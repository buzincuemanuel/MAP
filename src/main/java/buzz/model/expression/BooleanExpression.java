package buzz.model.expression;

import buzz.model.exception.MyException;
import buzz.model.state.IHeap;
import buzz.model.state.SymbolTable;
import buzz.model.type.BoolType;
import buzz.model.type.Type;
import buzz.model.value.BooleanValue;
import buzz.model.value.Value;

public record BooleanExpression(Expression left, String operator, Expression right) implements Expression {

    @Override
    public Value evaluate(SymbolTable<Value> symbolTable, IHeap heap) throws MyException {

        Value resultLeft = left.evaluate(symbolTable, heap);
        Value resultRight = right.evaluate(symbolTable, heap);

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

    @Override
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("BooleanExpression: second operand is not a boolean");
            }
        } else {
            throw new MyException("BooleanExpression: first operand is not a boolean");
        }
    }
}