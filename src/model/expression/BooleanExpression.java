package model.expression;

import model.exception.MyException;
import model.state.SymbolTable;
import model.value.Value;
import model.value.BooleanValue;

public record BooleanExpression(Expression left, String operator, Expression right) implements Expression {

    @Override
    public Value evaluate(SymbolTable<Value> symbolTable) throws MyException {

        Value resultLeft = left.evaluate(symbolTable);
        Value resultRight = right.evaluate(symbolTable);

        if (!(resultLeft instanceof BooleanValue(boolean leftValue))) {
            throw new MyException("BooleanExpression: left operand is not a boolean");
        }

        if (!(resultRight instanceof BooleanValue(boolean rightValue))) {
            throw new MyException("BooleanExpression: right operand is not a boolean");
        }

        boolean result = switch (operator) {
            case "&&" -> leftValue && rightValue;
            case "||" -> leftValue || rightValue;
            default ->
                    throw new MyException("BooleanExpression: invalid operator specified");
        };

        return new BooleanValue(result);
    }
}
