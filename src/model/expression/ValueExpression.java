package model.expression;

import model.state.SymbolTable;
import model.exception.MyException;
import model.value.Value;

public class ValueExpression implements Expression {
    private final Value val;

    public ValueExpression(Value v) {
        this.val = v;
    }

    @Override
    public Value evaluate(SymbolTable<Value> symTable) throws MyException {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}

