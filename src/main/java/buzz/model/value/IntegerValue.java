package buzz.model.value;

import buzz.model.type.IntType;
import buzz.model.type.Type;

public record IntegerValue(int value) implements Value {
    @Override
    public Type getType() {
        return new IntType();
    }


}
