package buzz.model.type;

import buzz.model.value.IntegerValue;
import buzz.model.value.Value;

public class IntType implements Type{

    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }

    public boolean equals(Object another){
        return another instanceof IntType;
    }
}
