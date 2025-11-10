// Importă tot ce ai nevoie din pachetele tale
import model.exception.MyException;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class Main {
    public static void main(String[] args) {

        // 1. Construiește manual programul (Exemplul 1 din Lab3.pdf)
        // int v; v=2; Print(v)
        Statement example1 = new CompoundStatement(
                new VariableDeclarationStatement("v", Type.INTEGER),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        // 2. Inițializează Starea Programului (ProgramState)
        ExecutionStack<Statement> exeStack = new ListExecutionStack<>();
        SymbolTable<Value> symTable = new MapSymbolTable<>();
        Out<Value> out = new ListOut<>();

        ProgramState programState = new ProgramState(exeStack, symTable, out, example1);

        // 3. Simulează logica Controller-ului (bucla "allStep")
        System.out.println("=== Începutul Execuției ===");
        System.out.println(programState); // Afișează starea inițială

        try {
            while (!programState.getExeStack().isEmpty()) {
                // Aceasta este logica din "oneStep" pe care o vei muta în Controller
                Statement currentStmt = programState.getExeStack().pop();
                currentStmt.execute(programState); // Execută instrucțiunea

                System.out.println(programState); // Afișează starea după fiecare pas
            }
        } catch (MyException e) {
            System.err.println("A apărut o eroare de execuție: " + e.getMessage());
        }

        System.out.println("=== Sfârșitul Execuției ===");
        System.out.println("Output-ul programului: " + programState.getOut());
    }
}