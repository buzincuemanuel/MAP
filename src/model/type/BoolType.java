package model.type;

import model.value.BooleanValue;
import model.value.Value;

public class BoolType implements Type{

    @Override
    public Value defaultValue(){
        return new BooleanValue(false);
    }

    public boolean equals(Object another){
        return another instanceof BoolType;
    }
}
