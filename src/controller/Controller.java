package controller;

import model.exception.MyException;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.statement.Statement;
import repository.IRepository;

public class Controller {

    private final IRepository repository;
    private boolean displaySteps = true;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplaySteps(boolean value) {
        this.displaySteps = value;
    }

    public ProgramState oneStep(ProgramState state) throws MyException {
        ExecutionStack<Statement> stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new MyException("Program state stack is empty!");
        }
        Statement currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        ProgramState prg = repository.getCrtPrg();
        repository.logPrgStateExec();
        if (displaySteps) System.out.println(prg);

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);

            repository.logPrgStateExec();
            if (displaySteps) System.out.println(prg);
        }
    }
}