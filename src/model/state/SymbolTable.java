package model.state;

public interface SymbolTable<V> {
    void setValue(String variableName, V value);
    boolean isDefined(String variableName);
    V getValue(String variableName);
    void declareVariable(V defaultValue, String variableName);
}
