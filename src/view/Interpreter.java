package view;

import controller.Controller;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.IntType;
import model.type.RefType;
import model.type.BoolType;
import model.value.IntegerValue;
import model.value.BooleanValue;
import repository.IRepository;
import repository.Repository;

public class Interpreter {
    public static void main(String[] args) {

        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), ">", new ValueExpression(new IntegerValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), '-', new ValueExpression(new IntegerValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        try {
            ProgramState prg1 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex1);
            IRepository repo1 = new Repository("log1.txt");
            repo1.addPrgState(prg1);
            Controller ctr1 = new Controller(repo1);

            ProgramState prg2 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex2);
            IRepository repo2 = new Repository("log2.txt");
            repo2.addPrgState(prg2);
            Controller ctr2 = new Controller(repo2);

            ProgramState prg3 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex3);
            IRepository repo3 = new Repository("log3.txt");
            repo3.addPrgState(prg3);
            Controller ctr3 = new Controller(repo3);

            ProgramState prg4 = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), ex4);
            IRepository repo4 = new Repository("log4.txt");
            repo4.addPrgState(prg4);
            Controller ctr4 = new Controller(repo4);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

            menu.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}