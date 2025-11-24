package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.evaluate(state.getSymTable());
        if (val.getType() != Type.STRING)
            throw new MyException("CloseRFile: Expression is not a string.");

        StringValue fileName = (StringValue) val;
        BufferedReader br = state.getFileTable().lookup(fileName);

        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("CloseRFile: " + e.getMessage());
        }
        state.getFileTable().remove(fileName);
        return state;
    }
    @Override
    public String toString() { return "close(" + expression + ")"; }
}