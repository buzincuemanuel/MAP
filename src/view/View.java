package view;

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

import java.util.Scanner;

public class View {

    private static final Statement example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", Type.INTEGER),
            new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );

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
                    return;
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
            ExecutionStack<Statement> exeStack = new ListExecutionStack<>();
            SymbolTable<Value> symTable = new MapSymbolTable<>();
            Out<Value> out = new ListOut<>();
            IFileTable fileTable = new MapFileTable();

            ProgramState programState = new ProgramState(exeStack, symTable, out, fileTable, example);

            IRepository repository = new Repository(logFilePath);
            repository.addPrgState(programState);

            Controller controller = new Controller(repository);

            repository.clearLogFile();

            System.out.println("Se execută programul... (verifică " + logFilePath + " și consola)");
            controller.allStep();

            System.out.println("Execuție terminată.");
            System.out.println("Output final: " + programState.getOut().toString());

        } catch (MyException e) {
            System.err.println("\n!!! A apărut o eroare în timpul execuției !!!");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("\n!!! A apărut o eroare necunoscută !!!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}