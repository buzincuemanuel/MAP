package view;

import controller.Controller;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.*;
import repository.IRepository;
import repository.Repository;

public class Interpreter {
    public static void main(String[] args) {
        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        try {
            ProgramState prg1 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), ex1);
            IRepository repo1 = new Repository("log1.txt");
            repo1.addPrgState(prg1);
            repo1.clearLogFile();
            Controller ctr1 = new Controller(repo1);

            Statement ex2 = new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("b", new IntType()),
                            new CompoundStatement(
                                    new AssignmentStatement("a",
                                            new ArithmeticExpression(
                                                    new ValueExpression(new IntegerValue(2)),
                                                    '+',
                                                    new ArithmeticExpression(
                                                            new ValueExpression(new IntegerValue(3)),
                                                            '*',
                                                            new ValueExpression(new IntegerValue(5))
                                                    )
                                            )
                                    ),
                                    new CompoundStatement(
                                            new AssignmentStatement("b",
                                                    new ArithmeticExpression(
                                                            new VariableExpression("a"),
                                                            '+',
                                                            new ValueExpression(new IntegerValue(1))
                                                    )
                                            ),
                                            new PrintStatement(new VariableExpression("b"))
                                    )
                            )
                    )
            );

            ProgramState prg2 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), ex2);
            IRepository repo2 = new Repository("log2.txt");
            repo2.addPrgState(prg2);
            repo2.clearLogFile();
            Controller ctr2 = new Controller(repo2);

            Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                    new CompoundStatement(new VariableDeclarationStatement("v", new BoolType()),
                            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                    new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));

            ProgramState prg3 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), ex3);
            IRepository repo3 = new Repository("log3.txt");
            repo3.addPrgState(prg3);
            repo3.clearLogFile();
            Controller ctr3 = new Controller(repo3);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));

            menu.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}