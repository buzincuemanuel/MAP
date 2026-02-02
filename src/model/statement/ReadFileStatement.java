package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.IntType;
import model.type.StringType;
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
        if (!state.getSymTable().getValue(varName).getType().equals(new IntType()))
            throw new MyException("ReadFile: Variable " + varName + " is not int.");

        Value val = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType()))
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
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        Type typevar = typeEnv.getValue(varName);

        if (!typexp.equals(new StringType())) {
            throw new MyException("ReadFile: The expression must be a string");
        }
        if (!typevar.equals(new IntType())) {
            throw new MyException("ReadFile: The variable must be an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() { return "readFile(" + expression + ", " + varName + ")"; }
}