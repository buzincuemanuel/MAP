package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public record AssignmentStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<Value> symTable = state.getSymTable();

        if (!symTable.isDefined(variableName)) {
            throw new MyException("Assignment error: Variable " + variableName + " is not declared.");
        }

        Value newValue = expression.evaluate(symTable);
        Type variableType = symTable.getValue(variableName).getType();

        if (!newValue.getType().equals(variableType)) {
            throw new MyException("Assignment error: Type of expression (" + newValue.getType() +
                    ") does not match type of variable " + variableName + " (" + variableType + ").");
        }

        symTable.setValue(variableName, newValue);
        return state;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}