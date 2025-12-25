package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type{

    @Override
    public Value defaultValue(){
        return new StringValue("");
    }

    public boolean equals(Object another){
        return another instanceof StringType;
    }
}
