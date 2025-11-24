package model.state;

import java.util.HashMap;
import java.util.Map;
import model.exception.MyException;

public class MapSymbolTable<V> implements SymbolTable<V> {
    private final Map<String, V> table = new HashMap<>();

    @Override
    public void setValue(String variableName, V value) throws MyException {
        if (!isDefined(variableName))
            throw new MyException("Variable not declared: " + variableName);
        table.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return table.containsKey(variableName);
    }

    @Override
    public V getValue(String variableName) throws MyException {
        if (!isDefined(variableName))
            throw new MyException("Variable not declared: " + variableName);
        return table.get(variableName);
    }

    @Override
    public void declareVariable(V defaultValue, String variableName) throws MyException {
        if (isDefined(variableName))
            throw new MyException("Variable already declared: " + variableName);
        table.put(variableName, defaultValue);
    }

    @Override
    public String toString() {
        return "SymbolTable{" + table + '}';
    }

    @Override
    public Map<String, V> getContent() {
        return table;
    }
}
