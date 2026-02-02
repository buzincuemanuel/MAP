package buzz.model.state;

import java.util.Map;

public interface SymbolTable<T> {
    void add(String id, T value);
    void setValue(String id, T value);
    boolean isDefined(String id);
    T getValue(String id);
    void declareVariable(T value, String id);
    SymbolTable<T> deepCopy();
    Map<String, T> getContent();
}