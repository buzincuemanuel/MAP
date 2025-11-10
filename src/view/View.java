package view;

// Importă tot ce este necesar
import controller.Controller;
import model.exception.MyException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Scanner;

public class View {

    // Hardcodăm Exemplele din Lab3.pdf

    // Ex 1: int v; v=2; Print(v)
    private static final Statement example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", Type.INTEGER),
            new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );

    // Ex 2: int a; int b; a=2+3*5; b=a+1; Print(b)
    private static final Statement example2 = new CompoundStatement(
            new VariableDeclarationStatement("a", Type.INTEGER),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", Type.INTEGER),
                    new CompoundStatement(
                            new AssignmentStatement("a", new ArithmeticExpression(
                                    new ValueExpression(new IntegerValue(2)),
                                    '+',
                                    new ArithmeticExpression(
                                            new ValueExpression(new IntegerValue(3)),
                                            '*',
                                            new ValueExpression(new IntegerValue(5))
                                    )
                            )),
                            new CompoundStatement(
                                    new AssignmentStatement("b", new ArithmeticExpression(
                                            new VariableExpression("a"),
                                            '+',
                                            new ValueExpression(new IntegerValue(1))
                                    )),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );

    // Ex 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
    private static final Statement example3 = new CompoundStatement(
            new VariableDeclarationStatement("a", Type.BOOLEAN),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", Type.INTEGER),
                    new CompoundStatement(
                            new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Alege o opțiune: ");
            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    System.out.println("La revedere!");
                    return; // Iese din main și oprește programul
                case "1":
                    executeExample(example1, "log1.txt");
                    break;
                case "2":
                    executeExample(example2, "log2.txt");
                    break;
                case "3":
                    executeExample(example3, "log3.txt");
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Meniu ===");
        System.out.println("0. Ieșire");
        System.out.println("1. Rulează Exemplul 1: int v; v=2; Print(v)");
        System.out.println("2. Rulează Exemplul 2: int a; int b; a=2+3*5; b=a+1; Print(b)");
        System.out.println("3. Rulează Exemplul 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
    }

    private static void executeExample(Statement example, String logFilePath) {
        try {
            // 1. Inițializează componentele ADT goale
            ExecutionStack<Statement> exeStack = new ListExecutionStack<>();
            SymbolTable<Value> symTable = new MapSymbolTable<>();
            Out<Value> out = new ListOut<>();

            // 2. Creează starea inițială
            ProgramState programState = new ProgramState(exeStack, symTable, out, example);

            // 3. Creează Repository-ul și adaugă starea
            IRepository repository = new Repository(logFilePath);
            repository.addPrgState(programState);

            // 4. Creează Controller-ul
            Controller controller = new Controller(repository);

            // 5. Rulează execuția completă
            System.out.println("Se execută programul... (verifică " + logFilePath + " și consola)");
            controller.allStep(); // Apelează logica de execuție

            // 6. Afișează output-ul final (opțional, dar util)
            System.out.println("Execuție terminată.");
            System.out.println("Output final: " + programState.getOut().toString());

        } catch (MyException e) {
            // Prinde orice eroare de execuție (ex. împărțire la zero, variabilă nedeclarată)
            System.err.println("\n!!! A apărut o eroare în timpul execuției !!!");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // Prinde erori neașteptate
            System.err.println("\n!!! A apărut o eroare necunoscută !!!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}