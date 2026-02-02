package buzz.model.state;

import java.util.List;

public interface ExecutionStack<T> {

    void push(T element);
    T pop();
    boolean isEmpty();
    List<T> getStatements();
}
