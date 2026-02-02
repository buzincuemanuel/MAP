package buzz.model.state;

import buzz.model.exception.MyException;
import buzz.model.statement.Statement;
import buzz.model.value.Value;

public class ProgramState {
    private static int lastId = 0;
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
        this.id = getNextId();
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

    public ProgramState(ExecutionStack<Statement> exeStack,
                        SymbolTable<Value> symTable,
                        Out<Value> out,
                        IFileTable fileTable,
                        IHeap heap) {
        this.id = getNextId();
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = null;
    }

    private static synchronized int getNextId() {
        return ++lastId;
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