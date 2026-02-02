package buzz.model.type;

import buzz.model.value.StringValue;
import buzz.model.value.Value;

public class StringType implements Type{

    @Override
    public Value defaultValue(){
        return new StringValue("");
    }

    public boolean equals(Object another){
        return another instanceof StringType;
    }
}
