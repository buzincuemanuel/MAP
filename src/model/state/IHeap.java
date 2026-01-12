package model.state;

import model.exception.MyException;
import model.value.Value;
import java.util.Map;

public interface IHeap {
    int allocate(Value value);
    Value get(int address) throws MyException;
    boolean containsKey(int address);
    void update(int address, Value value) throws MyException;
    void setContent(Map<Integer, Value> content);
    Map<Integer, Value> getContent();
}