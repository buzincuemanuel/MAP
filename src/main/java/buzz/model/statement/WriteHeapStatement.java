package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.expression.Expression;
import buzz.model.state.IHeap;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.RefType;
import buzz.model.type.Type;
import buzz.model.value.RefValue;
import buzz.model.value.Value;

public record WriteHeapStatement(String varName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined");
        }

        Value val = symTable.getValue(varName);
        if (!(val instanceof RefValue)) {
            throw new MyException("Variable " + varName + " is not of RefType");
        }

        RefValue refVal = (RefValue) val;
        if (!heap.containsKey(refVal.getAddr())) {
            throw new MyException("Address " + refVal.getAddr() + " is not in heap");
        }

        Value evaluated = expression.evaluate(symTable, heap);
        if (!evaluated.getType().equals(((RefType) refVal.getType()).getInner())) {
            throw new MyException("Type mismatch for WriteHeap");
        }

        heap.update(refVal.getAddr(), evaluated);
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typevar = typeEnv.getValue(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}