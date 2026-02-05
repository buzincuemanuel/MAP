package buzz.model.expression;

import buzz.model.exception.MyException;
import buzz.model.state.IHeap;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;
import buzz.model.value.Value;

public record VariableExpression(String variableName) implements Expression {

    @Override
    public Value evaluate(SymbolTable<Value> symbolTable, IHeap heap) throws MyException {
        if (!symbolTable.isDefined(variableName))
            throw new MyException("Variable " + variableName + " is not defined");

        return symbolTable.getValue(variableName);
    }

    @Override
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        return typeEnv.getValue(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}