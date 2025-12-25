package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntType implements Type{

    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }

    public boolean equals(Object another){
        return another instanceof IntType;
    }
}
