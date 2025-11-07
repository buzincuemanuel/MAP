package model.type;

import model.value.Value
import model.value.IntegerValue

public enum Type {
    INTEGER,
    BOOLEAN;

     public Value getDefaultValue() {
        return switch (this) {
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
        };
    }

}
