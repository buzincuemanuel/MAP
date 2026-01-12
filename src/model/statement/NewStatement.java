package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.IHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record NewStatement(String varName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!symTable.isDefined(varName)) {
            throw new MyException("NewStatement: Variable " + varName + " is not defined.");
        }

        Value varValue = symTable.getValue(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new MyException("NewStatement: Variable " + varName + " is not of RefType.");
        }

        Value evaluated = expression.evaluate(symTable, heap);
        Type locationType = ((RefType) varValue.getType()).getInner();

        if (!evaluated.getType().equals(locationType)) {
            throw new MyException("NewStatement: Type mismatch. Variable " + varName + " points to " + locationType + " but expression evaluates to " + evaluated.getType());
        }

        int newAddress = heap.allocate(evaluated);
        symTable.setValue(varName, new RefValue(newAddress, locationType));

        return state;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}