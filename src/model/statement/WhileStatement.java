package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;

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