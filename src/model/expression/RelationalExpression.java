package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public record RelationalExpression(Expression left, String operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable<Value> symTable, IHeap heap) throws MyException {
        Value v1 = left.evaluate(symTable, heap);
        Value v2 = right.evaluate(symTable, heap);

        if (!(v1 instanceof IntegerValue(int n1)))
            throw new MyException("RelationalExpression: Left operand not an integer.");
        if (!(v2 instanceof IntegerValue(int n2)))
            throw new MyException("RelationalExpression: Right operand not an integer.");

        boolean res = switch (operator) {
            case "<" -> n1 < n2;
            case "<=" -> n1 <= n2;
            case "==" -> n1 == n2;
            case "!=" -> n1 != n2;
            case ">" -> n1 > n2;
            case ">=" -> n1 >= n2;
            default -> throw new MyException("Invalid relational operator: " + operator);
        };
        return new BooleanValue(res);
    }

    @Override
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("RelationalExpression: second operand is not an integer");
            }
        } else {
            throw new MyException("RelationalExpression: first operand is not an integer");
        }
    }

    @Override
    public String toString() { return left + " " + operator + " " + right; }
}