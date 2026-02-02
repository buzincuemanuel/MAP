package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.expression.Expression;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.state.IHeap;
import buzz.model.type.RefType;
import buzz.model.type.Type;
import buzz.model.value.RefValue;
import buzz.model.value.Value;

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

        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typevar = typeEnv.getValue(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}