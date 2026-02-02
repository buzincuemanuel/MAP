package model.statement;

import model.exception.MyException;
import model.state.*;
import model.type.Type;
import model.value.Value;

public class ForkStatement implements Statement {
    private final Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack<Statement> newStack = new ListExecutionStack<>();
        newStack.push(statement);

        SymbolTable<Value> newSymTable = state.getSymTable().deepCopy();

        IHeap heap = state.getHeap();
        IFileTable fileTable = state.getFileTable();
        Out<Value> out = state.getOut();

        return new ProgramState(newStack, newSymTable, out, fileTable, heap);
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}