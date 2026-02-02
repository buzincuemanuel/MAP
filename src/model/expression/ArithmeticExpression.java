package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

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

    @Override
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("ArithmeticExpression: second operand is not an integer");
            }
        } else {
            throw new MyException("ArithmeticExpression: first operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}