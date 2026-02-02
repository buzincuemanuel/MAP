package model.statement;

import model.exception.MyException;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;

public record CompoundStatement(Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack<Statement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}