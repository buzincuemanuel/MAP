package model.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListOut<T> implements Out<T> {
    private final List<T> elements;

    public ListOut() {
        this.elements = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void add(T element) {
        elements.add(element);
    }

    @Override
    public String toString() {
        return "ListOut{" + "elements=" + elements + '}';
    }

    @Override
    public List<T> getValues() {
        return elements;
    }
}