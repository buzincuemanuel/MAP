package model.expression;

import model.exception.MyException;
import model.state.SymbolTable;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public record RelationalExpression(Expression left, String operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable<Value> symTable) throws MyException {
        Value v1 = left.evaluate(symTable);
        Value v2 = right.evaluate(symTable);

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
    public String toString() { return left + " " + operator + " " + right; }
}