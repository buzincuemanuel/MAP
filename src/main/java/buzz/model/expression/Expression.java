package buzz.model.expression;

import buzz.model.exception.MyException;
import buzz.model.state.IHeap;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;
import buzz.model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable<Value> symbolTable, IHeap heap) throws MyException;
    Type typecheck(SymbolTable<Type> typeEnv) throws MyException;
}