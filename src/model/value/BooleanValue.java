package model.value;
import model.type.BoolType;
import model.type.Type;

public record BooleanValue(boolean value) implements Value{

    @Override
    public Type getType(){
        return new BoolType();

    }
}