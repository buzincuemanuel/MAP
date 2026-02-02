package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NoOperationStatement{}";
    }
}