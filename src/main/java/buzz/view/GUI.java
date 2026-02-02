package buzz.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        var layoutManager = new BorderPane();

        var scene = new Scene(layoutManager);
        stage.setScene(scene);

        stage.show();

    }
}
