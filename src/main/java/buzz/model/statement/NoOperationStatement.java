package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;

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