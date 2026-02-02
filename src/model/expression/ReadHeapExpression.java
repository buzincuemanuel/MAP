package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.type.RefType;
import model.type.Type;
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
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else {
            throw new MyException("The rH argument is not a Ref Type");
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}