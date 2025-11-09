package model.statement;

import model.exception.MyException;
import model.state.ExecutionStack;
import model.state.ProgramState;

public record CompoundStatement(Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack<Statement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
