package proactive_travel;

import java.awt.Desktop;                // Obrir els fitxers .txt
import java.io.*;                       // Entrada/Sortida
import java.net.MalformedURLException;  // COmprovar URL imatge de fons
import java.util.*;                     // Estructures de Dades de l'API
import javafx.application.Application;  // Java Application
import javafx.concurrent.*;             // Serveis 
import javafx.event.*;                  // Events
import javafx.geometry.Insets;          // Crear Pane
import javafx.scene.Scene;              // Controls d'scene 
import javafx.scene.control.*;          // Botons i ProgressBar 
import javafx.scene.layout.*;           // Layouts
import javafx.scene.paint.Color;        // Colors per els textos
import javafx.scene.text.*;             // Textos
import javafx.stage.FileChooser;        // Selector de fitxers
import javafx.stage.Stage;              // Stages

public final class ProActive_Travel extends Application {
    private static List<Viatge> viatges;
    private static Map<String, Client> clients;
    private static Mapa mundi;
    private final Service serveiBack= new ProcessBacktraking();
    private final Service serveiGreedy= new ProcessGreedy();
    private static long tempsCalculBack= 0;
    
    @Override
    public void start(final Stage stage) throws MalformedURLException{
        //Títol i FileChooser
        stage.setTitle("PRO_ACTIVE TRAVEL AGENCY ®");
        final FileChooser fileChooser = new FileChooser();
        
        //Elements de la GUI
        final Text titol= new Text("Calculadora de Rutes V3.0 Alpha");
        titol.setFont(Font.font(null, FontWeight.BOLD, 18));
        titol.setFill(Color.RED);
        
        final Text autors= new Text("MaxiDave & TheMasterBoss ©");
        autors.setFont(Font.font(null, 8));
        
        final Text examinar= new Text("Selecciona fitxer: ");
        
        final Button openButton = new Button("Examinar...");
        
        final Text resultatLectura= new Text();
        
        final Button warningButton = new Button("Veure Warnings");
        warningButton.setVisible(false);
        
        final Button calculAproximat = new Button("Càlcul Aproximat");
        calculAproximat.setVisible(false);
        final Text calculantGreedy= new Text("Calculant...");
        calculantGreedy.setFill(Color.RED);
        calculantGreedy.setVisible(false);
        final ProgressBar pGreedy= new ProgressBar();
        pGreedy.setVisible(false);
        
        final Button calculExacte = new Button("Càlcul Exacte");
        calculExacte.setVisible(false);
        final Text calculantBack= new Text("Calculant...");
        calculantBack.setFill(Color.RED);
        calculantBack.setVisible(false);
        final ProgressBar pBack= new ProgressBar();
        pBack.setVisible(false);
        
        final Button veureRutes= new Button("Veure Resultats (txt)");
        final Button veureGoogle= new Button("Veure Resultats (Maps)");
        veureRutes.setVisible(false);
        veureGoogle.setVisible(false);
        
        //RUTINES BOTONS
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e){
                    configurarFileChooser(fileChooser); 
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null){
                        //Es creen les estructures de dades principals
                        viatges= new ArrayList<>();
                        clients= new HashMap<>();
                        mundi= new Mapa();

                        //Es duu a terme el procés d'entrada de dades a partir del Scanner al mapa i al Mapa de clients
                        Entrada.inicialitzaAplicatiu(file, clients, mundi, viatges);
                        
                        examinar.setText("Selecciona fitxer: "+file.getName());
                        if(!Entrada.fail){
                            if(Entrada.warnings == 0){
                                warningButton.setVisible(false);
                                resultatLectura.setFill(Color.GREEN);
                                resultatLectura.setText("Fitxer processat correctament ("+Entrada.lineCounter+" línies)");
                            }
                            else{
                                warningButton.setVisible(true);
                                resultatLectura.setFill(Color.RED);
                                resultatLectura.setText("Fitxer processat amb "+Entrada.warnings+" Warnings ("+Entrada.lineCounter+" línies)");
                            }
                            calculAproximat.setVisible(true);
                            calculExacte.setVisible(true);
                        }
                        else{
                            warningButton.setVisible(false);
                            calculAproximat.setVisible(false);
                            calculExacte.setVisible(false);
                            resultatLectura.setFill(Color.RED);
                            resultatLectura.setText("Fatal Error 404 :(");
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
                        resultatLectura.setFill(Color.RED);
                        resultatLectura.setText("Fatal Error 404 :(");
                    }
                }
            });
        
        calculExacte.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    veureRutes.setVisible(false);
                    veureGoogle.setVisible(false);
                    calculExacte.setVisible(false);
                    pBack.setVisible(true);
                    calculantBack.setText("Calculant...");
                    calculantBack.setVisible(true);
                    mundi.generarEDBacktraking();
                    if(!serveiBack.isRunning()) serveiBack.start();
                }
            });
        
        calculAproximat.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    veureRutes.setVisible(false);
                    veureGoogle.setVisible(false);
                    calculAproximat.setVisible(false);
                    pGreedy.setVisible(true);
                    calculantGreedy.setVisible(true);
                    //EL CODI ESTA A ProcessGreedy(void run)
                    if(!serveiGreedy.isRunning()) serveiGreedy.start();
                }
            });
        
        veureRutes.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    try {
                        String nom= "Viatge"; Integer num= 1; String ext=".txt";
                        while(true){
                            File file = new File(nom+num+ext);
                            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
                            num++;
                        }
                    } catch (IOException | IllegalArgumentException ex) {
                        //No troba més fitxers, para de mostrar
                    }
                }
            });
        
        veureGoogle.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle(final ActionEvent e) {
                    //AQUÍ S'HA DE GENERAR I MOSTRAR DOCUMENTS GOOGLE MAPS
                    try {
                        String nom= "visita1.kml"; Integer num= 1; String ext=".txt"; //De moment nomes es genera NOM_FITXER.kml es a dir un mira com esta posat a ProActive_Travel / Greedy
                        while(true){
                            File file = new File(nom);
                            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
                        }
                    } catch (IOException | IllegalArgumentException ex) {
                        //No troba més fitxers, para de mostrar
                    }
                }
            });
        
        serveiBack.setOnSucceeded(e -> {
            pBack.setVisible(false);
            calculExacte.setVisible(true);
            if(tempsCalculBack < 10000) calculantBack.setText("Execució: "+tempsCalculBack+"ms");
            else calculantBack.setText("Execució: "+(tempsCalculBack/1000)+"s");
            veureRutes.setVisible(true);
            veureGoogle.setVisible(true);
            //reset service
            serveiBack.reset();
        });
        
        serveiGreedy.setOnSucceeded(e -> {
            pGreedy.setVisible(false);
            calculAproximat.setVisible(true);
            calculantGreedy.setVisible(false);
            veureRutes.setVisible(true);
            veureGoogle.setVisible(true);
            //reset service
            serveiGreedy.reset();
        });
        
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(titol, 0, 0);
        GridPane.setConstraints(examinar, 0, 6);
        GridPane.setConstraints(openButton, 1, 6);
        GridPane.setConstraints(resultatLectura, 0, 9);
        GridPane.setConstraints(warningButton, 0, 10);
        GridPane.setConstraints(calculAproximat, 0, 15);
        GridPane.setConstraints(calculantGreedy, 0, 17);
        GridPane.setConstraints(pGreedy, 0, 15);
        GridPane.setConstraints(calculExacte, 1, 15);
        GridPane.setConstraints(calculantBack, 1, 17);
        GridPane.setConstraints(pBack, 1, 15);
        GridPane.setConstraints(veureRutes, 0, 20);
        GridPane.setConstraints(veureGoogle, 1, 20);
        GridPane.setConstraints(autors, 0, 27);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(titol, examinar, openButton, resultatLectura, warningButton, autors, 
                calculAproximat, calculExacte, pGreedy, pBack, calculantBack, calculantGreedy, veureRutes, veureGoogle);
        
        
        Pane rootGroup = new VBox(12);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        //String image= "https://i.ytimg.com/vi/_RhNVrnHPhE/maxresdefault.jpg";
        String image= "http://abcblogs.abc.es/jon-oleaga/files/2013/10/viaje2.jpg";
        rootGroup.setStyle("-fx-background-image: url('" + image + "'); " +
           "-fx-background-position: center center; " +
           "-fx-background-repeat: stretch;");
        rootGroup.getChildren().addAll(inputGridPane);
        Scene escenari= new Scene(rootGroup, 500, 350);
        stage.setScene(escenari);
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch();
    }
    
    private static void configurarFileChooser(
        final FileChooser fileChooser) {                   
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TEXT", "*.txt")
            );
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    private class ProcessBacktraking extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Runnable runner = new Runnable(){
                        public void run() {
                            long tempsI, tempsF;
                            tempsI= System.currentTimeMillis();
                            List< List<Ruta>> rutesTotals= new ArrayList< >();
                            Iterator<Viatge> it= viatges.iterator();
                            while(it.hasNext()){
                                Viatge viatge= it.next();
                                CalculExacte calcul= new CalculExacte(mundi, viatge);
                                rutesTotals.add(calcul.calcularRutaBack(mundi, viatge));
                            }
                            String nomFitxer= "Viatge1.txt"; int num= 1;
                            Iterator<List<Ruta>> itList= rutesTotals.iterator();
                            while(itList.hasNext()){
                                Sortida.mostrarRutes(itList.next(), viatges.get(num-1), nomFitxer);
                                num++;
                                nomFitxer= (nomFitxer.substring(0, 5)+(char)num+".txt");
                            }
                            tempsF= System.currentTimeMillis();
                            tempsCalculBack= tempsF-tempsI;
                        }
                    };
                    Thread t = new Thread(runner, "Code Executer");
                    t.start();
                    t.join();
                    return null;
                }
            };
        }
    }
    
    private class ProcessGreedy extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Runnable runner = new Runnable(){
                        public void run() {
                            Iterator<Viatge> it = viatges.iterator();
                            while (it.hasNext()) {
                                List<Ruta> r = CalculGreedy.calcularRutaGreedy(it.next(), mundi);
                                //Sortida.mostrarRutes(r, viatge, "viatge1.txt");
                                //sortidaKML.generarFitxer(r);
                            }
                            System.out.println("I'm The Reaper and death is my shadow");
                        }
                    };
                    Thread t = new Thread(runner, "Code Executer");
                    t.start();
                    t.join();
                    return null;
                }
            };
        }
    }
}