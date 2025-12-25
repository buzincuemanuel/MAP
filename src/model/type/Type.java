package model.type;

import model.value.BooleanValue;
import model.value.Value;
import model.value.IntegerValue;
import model.value.StringValue;

public interface Type {

    Value defaultValue();
}