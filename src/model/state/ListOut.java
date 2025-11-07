package model.state;

import java.util.ArrayList;
import java.util.List;

public class ListOut<T> implements Out<T> {
    private final List<T> elements = new ArrayList<>();

    @Override
    public void add(T element) {
        elements.add(element);
    }

    @Override
    public String toString() {
        return "ListOut{" + "elements=" + elements + '}';
    }
}
