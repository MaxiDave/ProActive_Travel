package proactive_travel;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public final class FitxerGUI extends Application {
    @Override
    public void start(final Stage stage) {
        //Platform.setImplicitExit(false);
        stage.setTitle("Selector de fitxers");
        final FileChooser fileChooser = new FileChooser();
 
        final Text text= new Text("Examina fitxers: ");
        final Button openButton = new Button("Examinar...");
 
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser(fileChooser); 
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        ProActive_Travel.file= file;
                    }
                    stage.close();
                }
            });
  
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(text, 0, 0);
        GridPane.setConstraints(openButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(text, openButton);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    public static void inicia() {
        Application.launch();
    }
    
    private static void configureFileChooser(
        final FileChooser fileChooser) {                   
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TEXT", "*.txt")
            );
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
}
