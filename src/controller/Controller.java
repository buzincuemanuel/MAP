package controller;

import model.exception.MyException;
import model.state.ProgramState;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repository.getPrgList());

        while (prgList.size() > 0) {
            repository.getPrgList().get(0).getHeap().setContent(
                    safeGarbageCollector(
                            getAddrFromAllSymTables(repository.getPrgList()),
                            getAddrFromHeap(repository.getPrgList().get(0).getHeap().getContent().values()),
                            repository.getPrgList().get(0).getHeap().getContent()
                    ));

            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    private void oneStepForAllPrg(List<ProgramState> prgList) {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setPrgList(prgList);
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromAllSymTables(List<ProgramState> prgList) {
        return prgList.stream()
                .flatMap(p -> getAddrFromSymTable(p.getSymTable().values()).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddr(); })
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddr(); })
                .collect(Collectors.toList());
    }
}