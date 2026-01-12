package model.expression;

import model.state.IHeap;
import model.state.SymbolTable;
import model.exception.MyException;
import model.value.Value;

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
    public String toString() {
        return val.toString();
    }
}

