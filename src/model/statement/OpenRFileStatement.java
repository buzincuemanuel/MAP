package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.evaluate(state.getSymTable());
        if (val.getType() != Type.STRING)
            throw new MyException("OpenRFile: Expression is not a string.");

        StringValue fileName = (StringValue) val;
        if (state.getFileTable().isDefined(fileName))
            throw new MyException("OpenRFile: File is already open.");

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.value()));
            state.getFileTable().add(fileName, br);
        } catch (IOException e) {
            throw new MyException("OpenRFile: " + e.getMessage());
        }
        return state;
    }
    @Override
    public String toString() { return "open(" + expression + ")"; }
}