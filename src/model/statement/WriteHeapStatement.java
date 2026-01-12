package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.IHeap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

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
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}