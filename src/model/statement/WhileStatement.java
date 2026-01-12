package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.BoolType;
import model.value.BooleanValue;
import model.value.Value;

public record WhileStatement(Expression expression, Statement statement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Condition is not boolean");
        }

        BooleanValue boolVal = (BooleanValue) val;
        if (boolVal.value()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return state;
    }

    @Override
    public String toString() {
        return "while(" + expression + ") " + statement;
    }
}