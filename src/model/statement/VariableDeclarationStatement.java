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

        Value defaultValue = type.defaultValue();

        symTable.declareVariable(defaultValue, variableName);

        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        typeEnv.declareVariable(type, variableName);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + variableName;
    }
}