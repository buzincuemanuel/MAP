package model.statement ;

import model.exception.MyException;
import model.statement.Statement;
import model.state.ProgramState;
import model.expression.Expression;

public record PrintStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        var value = expression.evaluate(state.getSymTable(), state.getHeap());
        state.getOut().add(value);
        return state;
    }
}