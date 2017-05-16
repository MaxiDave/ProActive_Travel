package proactive_travel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public final class GUI extends Application {
    @Override
    public void start(final Stage stage) throws MalformedURLException {
        stage.setTitle("PRO_ACTIVE TRAVEL AGENCY ®");
        final FileChooser fileChooser = new FileChooser();
 
        final Text titol= new Text("Calculadora de Rutes V3.0 Alpha");
        titol.setFont(Font.font(null, FontWeight.BOLD, 18));
        titol.setFill(Color.RED);
        final Text names= new Text("MaxiDave & TheMasterBoss ©");
        names.setFont(Font.font(null, 8));
        final Text textExaminar= new Text("Selecciona fitxer: ");
        final Button openButton = new Button("Examinar...");
        final Text textResultatLectura= new Text();
        final Button warningButton = new Button("Veure Warnings");
        final Button calculAproximat = new Button("Càlcul Aproximat");
        final Button calculExacte = new Button("Càlcul Exacte");
        warningButton.setVisible(false);
        calculAproximat.setVisible(false);
        calculExacte.setVisible(false);
        
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser(fileChooser); 
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null){
                        //Es creen les estructures de dades principals
                        ProActive_Travel.viatges= new ArrayList<Viatge>();
                        ProActive_Travel.clients= new HashMap<String, Client>();
                        ProActive_Travel.mundi= new Mapa();

                        //Es duu a terme el procés d'entrada de dades a partir del Scanner al mapa i al Mapa de clients
                        Entrada.inicialitzaAplicatiu(file, ProActive_Travel.clients, ProActive_Travel.mundi, ProActive_Travel.viatges);
                        
                        textExaminar.setText("Selecciona fitxer: "+file.getName());
                        if(!Entrada.fail){
                            calculAproximat.setVisible(true);
                            calculExacte.setVisible(true);
                            if(Entrada.warnings == 0){
                                warningButton.setVisible(false);
                                textResultatLectura.setFill(Color.GREEN);
                                textResultatLectura.setText("Fitxer processat correctament ("+Entrada.lineCounter+" línies)");
                            }
                            else{
                                warningButton.setVisible(true);
                                textResultatLectura.setFill(Color.RED);
                                textResultatLectura.setText("Fitxer processat amb "+Entrada.warnings+" Warnings ("+Entrada.lineCounter+" línies)");
                            }
                        }
                        else{
                            warningButton.setVisible(false);
                            calculAproximat.setVisible(false);
                            calculExacte.setVisible(false);
                            textResultatLectura.setFill(Color.RED);
                            textResultatLectura.setText("Fatal Error 404 :(");
                        }
                    }
                }
            });
        
        warningButton.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    try {
                        File file = new File("error.txt");
                        if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        warningButton.setVisible(false);
                        calculAproximat.setVisible(false);
                        calculExacte.setVisible(false);
                        textResultatLectura.setFill(Color.RED);
                        textResultatLectura.setText("Fatal Error 404 :(");
                    }
                }
            });
        
        calculExacte.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    List< List<Ruta>> rutesTotals= new ArrayList< >();
                    Iterator<Viatge> it= ProActive_Travel.viatges.iterator();
                    while(it.hasNext()){
                        rutesTotals.add(CalculExacte.calcularRutaBack(ProActive_Travel.mundi, it.next()));
                    }
                    System.out.println("He calculat");
                }
            });
        
        calculAproximat.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    Iterator<Viatge> it = ProActive_Travel.viatges.iterator();
                    while (it.hasNext()) {
                        Ruta r = CalculGreedy.calcularRutaGreedy(it.next(), ProActive_Travel.mundi);
                    }
                    System.out.println("I'm The Reaper and death is my shadow");
                }
            });
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(titol, 0, 0);
        GridPane.setConstraints(textExaminar, 0, 6);
        GridPane.setConstraints(openButton, 1, 6);
        GridPane.setConstraints(textResultatLectura, 0, 9);
        GridPane.setConstraints(warningButton, 0, 10);
        GridPane.setConstraints(calculAproximat, 0, 15);
        GridPane.setConstraints(calculExacte, 1, 15);
        GridPane.setConstraints(names, 0, 30);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(titol, textExaminar, openButton, textResultatLectura, warningButton, names, calculAproximat, calculExacte);
        
        
        Pane rootGroup = new VBox(12);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        //String image= "https://i.ytimg.com/vi/_RhNVrnHPhE/maxresdefault.jpg";
        String image= "http://abcblogs.abc.es/jon-oleaga/files/2013/10/viaje2.jpg";
        rootGroup.setStyle("-fx-background-image: url('" + image + "'); " +
           "-fx-background-position: center center; " +
           "-fx-background-repeat: stretch;");
        rootGroup.getChildren().addAll(inputGridPane);
        Scene escenari= new Scene(rootGroup, 500, 300);
        stage.setScene(escenari);
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
