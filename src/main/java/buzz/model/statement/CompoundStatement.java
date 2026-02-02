package buzz.model.statement;

import buzz.model.exception.MyException;
import buzz.model.state.ExecutionStack;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;

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