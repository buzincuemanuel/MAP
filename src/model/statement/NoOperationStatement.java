package model.statement;

import model.exception.MyException;
import model.state.ProgramState;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    @Override
    public String toString() {
        return "NoOperationStatement{}";
    }
}