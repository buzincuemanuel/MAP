package model.state;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable<T> implements SymbolTable<T> {
    private final Map<String, T> map;

    public MapSymbolTable() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(String id, T value) {
        map.put(id, value);
    }

    @Override
    public void setValue(String id, T value) {
        map.put(id, value);
    }

    @Override
    public boolean isDefined(String id) {
        return map.containsKey(id);
    }

    @Override
    public T getValue(String id) {
        return map.get(id);
    }

    @Override
    public void declareVariable(T value, String id) {
        map.put(id, value);
    }

    @Override
    public SymbolTable<T> deepCopy() {
        MapSymbolTable<T> newSymTable = new MapSymbolTable<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            newSymTable.add(entry.getKey(), entry.getValue());
        }
        return newSymTable;
    }

    @Override
    public Map<String, T> getContent() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}