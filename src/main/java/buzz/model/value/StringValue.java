package buzz.model.value;

import buzz.model.type.StringType;
import buzz.model.type.Type;

public record StringValue(String value) implements Value {
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}