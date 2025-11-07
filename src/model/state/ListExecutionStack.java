package model.state;
import java.util.LinkedList;
import model.state.ExecutionStack;

public class ListExecutionStack<T> implements ExecutionStack<T> {
    private final LinkedList<T> elements = new LinkedList<>();

    @Override
    public void push(T element) {
        elements.addFirst(element);
    }

    @Override
    public T pop() {
        return elements.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        return "ExecutionStack: " + elements;
    }
}
