package buzz.model.type;

import buzz.model.value.BooleanValue;
import buzz.model.value.Value;

public class BoolType implements Type{

    @Override
    public Value defaultValue(){
        return new BooleanValue(false);
    }

    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
