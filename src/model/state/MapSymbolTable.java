package model.state;

import model.exception.MyException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable<V> implements SymbolTable<V> {
    private final Map<String, V> map;

    public MapSymbolTable() {
        this.map = new HashMap<>();
    }

    @Override
    public void setValue(String variableName, V value) throws MyException {
        if (!map.containsKey(variableName))
            throw new MyException("Variable " + variableName + " is not declared.");
        map.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public V getValue(String variableName) throws MyException {
        if (!map.containsKey(variableName))
            throw new MyException("Variable " + variableName + " is not defined.");
        return map.get(variableName);
    }

    @Override
    public void declareVariable(V defaultValue, String variableName) {
        map.put(variableName, defaultValue);
    }

    @Override
    public Map<String, V> getContent() {
        return map;
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public SymbolTable<V> deepCopy() {
        MapSymbolTable<V> newSymTable = new MapSymbolTable<>();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            newSymTable.declareVariable(entry.getValue(), entry.getKey());
        }
        return newSymTable;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}