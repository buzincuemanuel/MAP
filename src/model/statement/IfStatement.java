package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = condition.evaluate(state.getSymTable(), state.getHeap());

        if (!value.getType().equals(new BoolType())) {
            throw new MyException("Conditional expression is not a boolean");
        }

        var booleanValue = (BooleanValue) value;

        ExecutionStack<Statement> stack = state.getExeStack();

        if (booleanValue.value()) {
            stack.push(thenStatement);
        } else {
            stack.push(elseStatement);
        }

        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        Type typexp = condition.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF has not the type bool");
        }
    }

    @Override
    public String toString() {
        return "(IF(" + condition.toString() + ") THEN(" + thenStatement.toString()
                + ")ELSE(" + elseStatement.toString() + "))";
    }
}