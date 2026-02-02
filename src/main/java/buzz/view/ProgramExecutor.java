package buzz.view;

import buzz.controller.Controller;
import buzz.model.state.ProgramState;
import buzz.model.value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramExecutor {
    private final Controller controller;
    private final Stage stage;

    // UI Components
    private TextField numberOfProgramStatesTextField;
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    private ListView<String> outputListView;
    private ListView<String> fileTableListView;
    private ListView<Integer> programStateIdentifiersListView;
    private TableView<Map.Entry<String, Value>> symbolTableView;
    private ListView<String> executionStackListView;
    private Button runOneStepButton;

    public ProgramExecutor(Controller controller) {
        this.controller = controller;
        this.stage = new Stage();
        initUI();
    }

    private void initUI() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // (a) Number of PrgStates
        numberOfProgramStatesTextField = new TextField();
        numberOfProgramStatesTextField.setEditable(false);
        HBox numStatesBox = new HBox(5, new Label("No. Program States:"), numberOfProgramStatesTextField);

        // (b) Heap Table
        heapTableView = new TableView<>();
        TableColumn<Map.Entry<Integer, Value>, Integer> heapAddrCol = new TableColumn<>("Address");
        heapAddrCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        TableColumn<Map.Entry<Integer, Value>, String> heapValCol = new TableColumn<>("Value");
        heapValCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        heapTableView.getColumns().add(heapAddrCol);
        heapTableView.getColumns().add(heapValCol);
        VBox heapBox = new VBox(5, new Label("Heap Table"), heapTableView);

        // (c) Output
        outputListView = new ListView<>();
        VBox outBox = new VBox(5, new Label("Output"), outputListView);

        // (d) File Table
        fileTableListView = new ListView<>();
        VBox fileBox = new VBox(5, new Label("File Table"), fileTableListView);

        // (e) PrgState Identifiers
        programStateIdentifiersListView = new ListView<>();
        programStateIdentifiersListView.setOnMouseClicked(e -> updateCurrentStateViews());
        VBox prgIdBox = new VBox(5, new Label("PrgState IDs"), programStateIdentifiersListView);

        // (f) Symbol Table
        symbolTableView = new TableView<>();
        TableColumn<Map.Entry<String, Value>, String> symNameCol = new TableColumn<>("Var Name");
        symNameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        TableColumn<Map.Entry<String, Value>, String> symValCol = new TableColumn<>("Value");
        symValCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableView.getColumns().add(symNameCol);
        symbolTableView.getColumns().add(symValCol);
        VBox symBox = new VBox(5, new Label("Symbol Table"), symbolTableView);

        // (g) Execution Stack
        executionStackListView = new ListView<>();
        VBox exeBox = new VBox(5, new Label("Exe Stack"), executionStackListView);

        // Layout Organization (GridPane for better distribution)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(heapBox, 0, 0);
        grid.add(outBox, 1, 0);
        grid.add(fileBox, 2, 0);
        grid.add(prgIdBox, 0, 1);
        grid.add(symBox, 1, 1);
        grid.add(exeBox, 2, 1);

        // (h) Run One Step Button
        runOneStepButton = new Button("Run One Step");
        runOneStepButton.setOnAction(e -> runOneStep());

        root.getChildren().addAll(numStatesBox, grid, runOneStepButton);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Program Execution");
        stage.setScene(scene);

        // Initial population
        refresh();
    }

    public void show() {
        stage.show();
    }

    private void runOneStep() {
        try {
            List<ProgramState> programStates = controller.getRepository().getPrgList();
            if (programStates.size() > 0) {
                controller.oneStep();
                refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Program is finished!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void refresh() {
        populateHeap();
        populateOut();
        populateFileTable();
        populatePrgStateIdentifiers();
        updateCurrentStateViews(); // Updates SymTable and ExeStack based on selection
    }

    private void populateHeap() {
        if (!controller.getRepository().getPrgList().isEmpty()) {
            Map<Integer, Value> heap = controller.getRepository().getPrgList().get(0).getHeap().getContent();
            heapTableView.setItems(FXCollections.observableArrayList(heap.entrySet()));
            heapTableView.refresh();
        }
    }

    private void populateOut() {
        if (!controller.getRepository().getPrgList().isEmpty()) {
            List<Value> out = controller.getRepository().getPrgList().get(0).getOut().getValues();
            outputListView.setItems(FXCollections.observableArrayList(
                    out.stream().map(Object::toString).collect(Collectors.toList())));
        }
    }

    private void populateFileTable() {
        if (!controller.getRepository().getPrgList().isEmpty()) {
            Map<Object, Object> files = (Map) controller.getRepository().getPrgList().get(0).getFileTable().getContent();
            fileTableListView.setItems(FXCollections.observableArrayList(
                    files.keySet().stream().map(Object::toString).collect(Collectors.toList())));
        }
    }

    private void populatePrgStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepository().getPrgList();
        List<Integer> idList = programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableArrayList(idList));
        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void updateCurrentStateViews() {
        // Obține ID-ul selectat sau implicit primul
        Integer selectedId = programStateIdentifiersListView.getSelectionModel().getSelectedItem();
        ProgramState currentPrg = null;

        List<ProgramState> programStates = controller.getRepository().getPrgList();
        if (programStates.isEmpty()) return;

        if (selectedId == null) {
            currentPrg = programStates.get(0);
        } else {
            currentPrg = programStates.stream()
                    .filter(p -> p.getId() == selectedId)
                    .findFirst()
                    .orElse(programStates.get(0));
        }

        // Populate Symbol Table
        symbolTableView.setItems(FXCollections.observableArrayList(currentPrg.getSymTable().getContent().entrySet()));
        symbolTableView.refresh();

        // Populate Execution Stack
        List<String> stackList = currentPrg.getExeStack().getStatements().stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        executionStackListView.setItems(FXCollections.observableArrayList(stackList));
    }
}