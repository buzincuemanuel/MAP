package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.expression.Expression;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.StringType;
import buzz.model.type.Type;
import buzz.model.value.StringValue;
import buzz.model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType()))
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
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("OpenRFile: The expression must be a string");
        }
    }

    @Override
    public String toString() { return "open(" + expression + ")"; }
}