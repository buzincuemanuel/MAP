package model.state;

import model.exception.MyException;

import java.util.Map;

public interface SymbolTable<V> {
    void setValue(String variableName, V value) throws MyException;
    boolean isDefined(String variableName);
    V getValue(String variableName) throws MyException;
    void declareVariable(V defaultValue, String variableName) throws MyException;
    Map<String, V> getContent();
}
