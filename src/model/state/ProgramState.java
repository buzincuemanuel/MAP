package model.state;

import model.exception.MyException;
import model.statement.Statement;
import model.value.Value;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private static final AtomicInteger lastId = new AtomicInteger(0);
    private final int id;

    private final ExecutionStack<Statement> exeStack;
    private final SymbolTable<Value> symTable;
    private final Out<Value> out;
    private final IFileTable fileTable;
    private final IHeap heap;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack<Statement> exeStack,
                        SymbolTable<Value> symTable,
                        Out<Value> out,
                        IFileTable fileTable,
                        IHeap heap,
                        Statement originalProgram) {
        this.id = lastId.incrementAndGet();
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram;

        if (originalProgram != null) {
            this.exeStack.push(originalProgram);
        }
    }

    // fork
    public ProgramState(ExecutionStack<Statement> exeStack,
                        SymbolTable<Value> symTable,
                        Out<Value> out,
                        IFileTable fileTable,
                        IHeap heap) {
        this.id = lastId.incrementAndGet();
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = null;
    }

    public ExecutionStack<Statement> getExeStack() { return exeStack; }
    public SymbolTable<Value> getSymTable() { return symTable; }
    public Out<Value> getOut() { return out; }
    public IFileTable getFileTable() { return fileTable; }
    public IHeap getHeap() { return heap; }
    public int getId() { return id; }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new MyException("Program state stack is empty!");
        }
        Statement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "\n=== Program State (ID: " + id + ") ===\n" +
                "ExeStack: " + exeStack + "\n" +
                "SymTable: " + symTable + "\n" +
                "Out: " + out + "\n" +
                "FileTable: " + fileTable + "\n" +
                "Heap: " + heap + "\n";
    }
}