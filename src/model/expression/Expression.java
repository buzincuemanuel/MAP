package model.expression;

import model.exception.MyException;
import model.state.IHeap;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable<Value> symbolTable, IHeap heap) throws MyException;
    Type typecheck(SymbolTable<Type> typeEnv) throws MyException;
}