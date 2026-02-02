package buzz.model.statement ;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;
import buzz.model.state.SymbolTable;
import buzz.model.type.Type;
import buzz.model.expression.Expression;

public record PrintStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.getSymTable(), state.getHeap());
        state.getOut().add(value);
        return null;
    }

    @Override
    public SymbolTable<Type> typecheck(SymbolTable<Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }
}