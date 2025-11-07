package model.statement;

import model.statement.Statement;
import model.state.ProgramState;
import model.expression.Expression;

public record PrintStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var value = expression.evaluate(state.getSymTable());
        state.out().add(value);
        return state;
    }
}
