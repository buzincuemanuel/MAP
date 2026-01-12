package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.value.RefValue;
import model.value.Value;

public class ReadHeapExpression implements Expression {
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolTable<Value> symTable, IHeap heap) throws MyException {
        Value val = expression.evaluate(symTable, heap);
        if (!(val instanceof RefValue)) {
            throw new MyException("ReadHeap argument is not a RefValue");
        }
        RefValue refVal = (RefValue) val;
        return heap.get(refVal.getAddr());
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}