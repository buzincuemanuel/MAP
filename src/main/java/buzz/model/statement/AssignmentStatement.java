package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.expression.Expression;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;
import buzz.model.value.Value;

public record AssignmentStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<Value> symTable = state.getSymTable();

        if (!symTable.isDefined(variableName)) {
            throw new MyException("Assignment error: Variable " + variableName + " is not declared.");
        }

        Value newValue = expression.evaluate(symTable, state.getHeap());
        Type variableType = symTable.getValue(variableName).getType();

        if (!newValue.getType().equals(variableType)) {
            throw new MyException("Assignment error: Type of expression (" + newValue.getType() +
                    ") does not match type of variable " + variableName + " (" + variableType + ").");
        }

        symTable.setValue(variableName, newValue);
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typevar = typeEnv.getValue(variableName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}