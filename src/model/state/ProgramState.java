package model.state;

import model.statement.Statement;
import model.value.Value;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private static final AtomicInteger lastId = new AtomicInteger(0);
    private final int id;

    private final ExecutionStack<Statement> exeStack;
    private final SymbolTable<Value> symTable;
    private final Out<Value> out;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack<Statement> exeStack,
                    SymbolTable<Value> symTable,
                    Out<Value> out,
                    Statement originalProgram) {
        this.id = lastId.incrementAndGet();
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;

        this.exeStack.push(originalProgram);
    }

    public ExecutionStack<Statement> getExeStack() {
        return exeStack;
    }

    public SymbolTable<Value> getSymTable() {
        return symTable;
    }

    public Out<Value> getOut() {
        return out;
    }

    public int getId() {
        return id;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    @Override
    public String toString() {
        return "\n=== Program State " + id + " ===\n" +
                "ExeStack: " + exeStack + "\n" +
                "SymTable: " + symTable + "\n" +
                "Out: " + out + "\n";
    }
}
