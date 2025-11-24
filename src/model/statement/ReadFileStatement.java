package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(Expression expression, String varName) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.getSymTable().isDefined(varName))
            throw new MyException("ReadFile: Variable " + varName + " is not defined.");
        if (state.getSymTable().getValue(varName).getType() != Type.INTEGER)
            throw new MyException("ReadFile: Variable " + varName + " is not int.");

        Value val = expression.evaluate(state.getSymTable());
        if (val.getType() != Type.STRING)
            throw new MyException("ReadFile: Expression is not a string.");

        StringValue fileName = (StringValue) val;
        BufferedReader br = state.getFileTable().lookup(fileName);

        try {
            String line = br.readLine();
            int intVal = (line == null) ? 0 : Integer.parseInt(line);
            state.getSymTable().setValue(varName, new IntegerValue(intVal));
        } catch (IOException e) {
            throw new MyException("ReadFile: " + e.getMessage());
        }
        return state;
    }
    @Override
    public String toString() { return "readFile(" + expression + ", " + varName + ")"; }
}