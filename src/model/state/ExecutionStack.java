package model.state;

public interface ExecutionStack<T> {

    void push(T element);
    T pop();
    boolean isEmpty();
}
