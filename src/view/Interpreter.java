package view;

import controller.Controller;
import model.exception.MyException;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

public class Interpreter {

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        try {
            ex1.typecheck(new MapSymbolTable<>());
            ProgramState prg1 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (MyException e) {
            System.out.println("Example 1 validation error: " + e.getMessage());
        }

        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new StringValue("dsd")), '+',
                                new ArithmeticExpression(new ValueExpression(new IntegerValue(3)), '*', new ValueExpression(new IntegerValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(new VariableExpression("a"), '+', new ValueExpression(new IntegerValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        try {
            ex2.typecheck(new MapSymbolTable<>());
            ProgramState prg2 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        } catch (MyException e) {
            System.out.println("Example 2 validation error: " + e.getMessage());
        }

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        try {
            ex3.typecheck(new MapSymbolTable<>());
            ProgramState prg3 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (MyException e) {
            System.out.println("Example 3 validation error: " + e.getMessage());
        }

        Statement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(new CompoundStatement(
                                                        new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                                                        new CompoundStatement(
                                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            ex4.typecheck(new MapSymbolTable<>());
            ProgramState prg4 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        } catch (MyException e) {
            System.out.println("Example 4 validation error: " + e.getMessage());
        }

        menu.show();
    }
}