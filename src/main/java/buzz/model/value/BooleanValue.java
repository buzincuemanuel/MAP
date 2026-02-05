package buzz.model.value;

import buzz.model.type.BoolType;
import buzz.model.type.Type;

public record BooleanValue(boolean value) implements Value{

    @Override
    public Type getType(){
        return new BoolType();

    }


}