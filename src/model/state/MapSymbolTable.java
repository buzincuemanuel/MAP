package model.state;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable<V> implements SymbolTable<V> {
    private final Map<String, V> table = new HashMap<>();

    @Override
    public void setValue(String variableName, V value) {
        if (!isDefined(variableName))
            throw new RuntimeException("Variable not declared: " + variableName);
        table.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return table.containsKey(variableName);
    }

    @Override
    public V getValue(String variableName) {
        if (!isDefined(variableName))
            throw new RuntimeException("Variable not declared: " + variableName);
        return table.get(variableName);
    }

    @Override
    public void declareVariable(V defaultValue, String variableName) {
        if (isDefined(variableName))
            throw new RuntimeException("Variable already declared: " + variableName);
        table.put(variableName, defaultValue);
    }

    @Override
    public String toString() {
        return "SymbolTable{" + table + '}';
    }
}
