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
import javafx.stage.Stage;
import javafx.scene.text.Text;

public final class infoGUI extends Application { 
    @Override
    public void start(final Stage stage) {
        stage.setTitle("S'ha processat el fitxer ("+Entrada.lineCounter+" línies)");
        final Text text;
        if(Entrada.warnings > 0) text= new Text("Warnings detectats: "+Entrada.warnings);
        else text= new Text("Fitxer processat correctament");
        final Button continuar= new Button("Continuar");
        final Button warnings= new Button("Veure Warnings");
 
        continuar.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e){
                    stage.close();
                }
            });
        
        warnings.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    //Obrir fitxer error
                }
            });
  
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(text, 0, 0);
        GridPane.setConstraints(continuar, 0, 1);
        if(Entrada.warnings > 0) GridPane.setConstraints(warnings, 1, 1);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        if(Entrada.warnings > 0) inputGridPane.getChildren().addAll(text, continuar, warnings);
        else inputGridPane.getChildren().addAll(text, continuar);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
}
