package buzz.controller;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;
import buzz.model.value.RefValue;
import buzz.model.value.Value;
import buzz.repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public IRepository getRepository() {
        return repo;
    }

    // Metoda pentru GUI: Execută un singur pas
    public void oneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());

        if (prgList.size() > 0) {
            // Garbage Collection
            List<Integer> symTableAddr = getAddrFromAllSymTables(prgList);
            Map<Integer, Value> newHeap = safeGarbageCollector(symTableAddr, prgList.get(0).getHeap().getContent());
            prgList.forEach(p -> p.getHeap().setContent(newHeap));

            // Execuție un pas
            oneStepForAllPrg(prgList);

            // Eliminare programe finalizate
            prgList = removeCompletedPrg(repo.getPrgList());
            repo.setPrgList(prgList);
        }

        executor.shutdownNow();
    }

    // --- Păstrează metodele existente din fișierul tău original (allStep, oneStepForAllPrg, etc.) ---

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            List<Integer> symTableAddr = getAddrFromAllSymTables(prgList);
            Map<Integer, Value> newHeap = safeGarbageCollector(symTableAddr, prgList.get(0).getHeap().getContent());
            prgList.forEach(p -> p.getHeap().setContent(newHeap));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    private void oneStepForAllPrg(List<ProgramState> prgList) throws MyException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
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
            throw new MyException(e.getMessage());
        }
        repo.setPrgList(prgList);
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromAllSymTables(List<ProgramState> prgList) {
        return prgList.stream()
                .flatMap(p -> p.getSymTable().getContent().values().stream())
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .distinct()
                .collect(Collectors.toList());
    }
}