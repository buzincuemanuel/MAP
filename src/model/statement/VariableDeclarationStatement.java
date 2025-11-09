package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public record VariableDeclarationStatement(String variableName, Type type) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<Value> symTable = state.getSymTable();

        if (symTable.isDefined(variableName)) {
            throw new MyException("Variable " + variableName + " is already declared.");
        }

        Value defaultValue = type.getDefaultValue();

        symTable.declareVariable(defaultValue, variableName);

        return state;
    }

    @Override
    public String toString() {
        return type.toString() + " " + variableName;
    }
}