package buzz.model.state;

import java.util.List;

public interface Out<T> {
    void add(T element);
    String toString();
    List<T> getValues();
}
