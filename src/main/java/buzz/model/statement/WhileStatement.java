package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.expression.Expression;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.BoolType;
import buzz.model.type.Type;
import buzz.model.value.BooleanValue;
import buzz.model.value.Value;

public record WhileStatement(Expression expression, Statement statement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Condition is not boolean");
        }

        BooleanValue boolVal = (BooleanValue) val;
        if (boolVal.value()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of WHILE has not the type bool");
        }
    }

    @Override
    public String toString() {
        return "while(" + expression + ") " + statement;
    }
}