package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = condition.evaluate(state.getSymTable());

        if (value.getType() != Type.BOOLEAN) {
            throw new MyException("Conditional expression is not a boolean");
        }

        var booleanValue = (BooleanValue) value;

        ExecutionStack<Statement> stack = state.getExeStack();

        if (booleanValue.value()) {
            stack.push(thenStatement);
        } else {
            stack.push(elseStatement);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(IF(" + condition.toString() + ") THEN(" + thenStatement.toString()
                + ")ELSE(" + elseStatement.toString() + "))";
    }
}