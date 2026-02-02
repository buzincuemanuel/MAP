package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws MyException;
    SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException;
}