package buzz.model.expression;

import buzz.model.exception.MyException;
import buzz.model.state.IHeap;
import buzz.model.state.SymbolTable;
import buzz.model.type.RefType;
import buzz.model.type.Type;
import buzz.model.value.RefValue;
import buzz.model.value.Value;

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