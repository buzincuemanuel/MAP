package buzz.model.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return "{" + elements.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public List<T> getValues() {
        return elements;
    }
}