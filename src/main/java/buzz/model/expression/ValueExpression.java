package buzz.model.expression;

import buzz.model.exception.MyException;
import buzz.model.state.IHeap;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;
import buzz.model.value.Value;

public class ValueExpression implements Expression {
    private final Value val;

    public ValueExpression(Value v) {
        this.val = v;
    }

    @Override
    public Value evaluate(SymbolTable<Value> symTable, IHeap heap) throws MyException {
        return val;
    }

    @Override
    public Type typecheck(SymbolTable<Type> typeEnv) throws MyException {
        return val.getType();
    }

    @Override
    public String toString() {
        return val.toString();
    }

}