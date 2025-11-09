package model.expression;

import model.exception.MyException;
import model.state.SymbolTable;
import model.value.Value;

public record VariableExpression(String variableName) implements Expression {

    @Override
    public Value evaluate(SymbolTable<Value> symbolTable) throws MyException {
        if (!symbolTable.isDefined(variableName))
            throw new MyException("Variable " + variableName + " is not defined");

        return symbolTable.getValue(variableName);
    }
}
