package buzz.view;

import buzz.controller.Controller;
import buzz.model.expression.*;
import buzz.model.state.*;
import buzz.model.statement.*;
import buzz.model.type.*;
import buzz.model.value.*;
import buzz.repository.IRepository;
import buzz.repository.Repository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        ListView<Statement> programsListView = new ListView<>();
        programsListView.setItems(FXCollections.observableArrayList(getAllExamples()));
        programsListView.setPrefHeight(400);

        Button startButton = new Button("Start Program");
        startButton.setOnAction(e -> {
            Statement selectedStmt = programsListView.getSelectionModel().getSelectedItem();
            if (selectedStmt == null) {
                showAlert("Error", "No program selected!");
                return;
            }

            int id = programsListView.getSelectionModel().getSelectedIndex() + 1;
            try {
                selectedStmt.typecheck(new MapSymbolTable<>());
                ProgramState prg = new ProgramState(new ListExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapFileTable(), new Heap(), selectedStmt);
                IRepository repo = new Repository(prg, "log" + id + ".txt");
                Controller controller = new Controller(repo);

                ProgramExecutor executorWindow = new ProgramExecutor(controller);
                executorWindow.show();


            } catch (Exception ex) {
                showAlert("Validation Error", ex.getMessage());
            }
        });

        root.getChildren().addAll(programsListView, startButton);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Select Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private List<Statement> getAllExamples() {
        List<Statement> allStatements = new ArrayList<>();

        // EX 1
        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        allStatements.add(ex1);

        // EX 2
        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new IntegerValue(4)), '+',
                                new ArithmeticExpression(new ValueExpression(new IntegerValue(3)), '*', new ValueExpression(new IntegerValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(new VariableExpression("a"), '+', new ValueExpression(new IntegerValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));
        allStatements.add(ex2);

        // EX 3
        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
        allStatements.add(ex3);

        // EX 4
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
        allStatements.add(ex4);
        return allStatements;
    }

    public static void main(String[] args) {
        launch(args);
    }
}