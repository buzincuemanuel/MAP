package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws MyException;
    SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException;
}