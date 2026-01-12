package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.value.Value;
import model.value.IntegerValue;

public record ArithmeticExpression(Expression left, char operator, Expression right) implements Expression {

    @Override
    public Value evaluate(SymbolTable<Value> symbolTable, IHeap heap) throws MyException {

        Value resultLeft = left.evaluate(symbolTable, heap);
        Value resultRight = right.evaluate(symbolTable, heap);

        if (!(resultLeft instanceof IntegerValue(int leftValue))){
            throw new MyException("ArithmeticExpression: left value is not an integer");
        }

        if (!(resultRight instanceof IntegerValue(int rightValue))){
            throw new MyException("ArithmeticExpression: right value is not an integer");
        }
        int result = switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> {
                if (rightValue == 0) {
                    throw new MyException("Division by zero!");
                }
                yield leftValue / rightValue;
            }
            default -> throw new MyException("ArithmeticExpression: invalid operator");
        };

        return new IntegerValue(result);
    }
}
