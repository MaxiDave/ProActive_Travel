package proactive_travel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Roger
 */
public abstract class Entrada {
    
    /**
     * @pre: --
     * @post: Demana la ruta del fitxer a carregar dades i retorna un scanner a aquest preparat per la lectura
     */
    public static Scanner entrarNomFitxer(){
        Scanner teclat = new Scanner(System.in);
        System.out.println("Nom del fitxer: ");
        Scanner fitxer= null;
        try { //Pot ser que el fitxer no existeixi
            fitxer = new Scanner(new File(teclat.nextLine())).useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            //Si no es troba el fitxer informa i avorta
            System.err.println(e);
            System.exit(-1);
        }
        return fitxer;
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "client"
     * @post: Llegeix un client de fitxer i l'afegeix a llistaClients
     */
    private static void donarAltaClient(Scanner fitxer, List<Client> clients){
        String nom= fitxer.nextLine();
        Set<String> prefs= new HashSet<String>();
        String pref= fitxer.nextLine();
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= fitxer.nextLine();
        }
        clients.add(new Client(nom, prefs));
    }
    
    private static void donarAltaLloc(Scanner fitxer, Mapa mundi){
        String nomID= fitxer.nextLine();
        String coords= fitxer.nextLine();
        String zH= fitxer.nextLine();
        mundi.afegeixLloc(new Lloc(nomID, new Coordenades(coords, zH)));
        fitxer.nextLine(); //*
    }
    
    private static void donarAltaAllotjament(Scanner fitxer, Mapa mundi){
        String nomID= fitxer.nextLine();
        String coords= fitxer.nextLine();
        String zH= fitxer.nextLine();
        String cat= fitxer.nextLine();
        Double preuHab= fitxer.nextDouble();
        fitxer.nextLine(); //Salta \n
        Set<String> prefs= new HashSet<String>();
        String pref= fitxer.nextLine();
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= fitxer.nextLine();
        }
        mundi.afegeixPuntInteres(new Allotjament(nomID, prefs, preuHab, cat, new Coordenades(coords, zH)));
    }
    
    private static void donarAltaPuntVisitable(Scanner fitxer, Mapa mundi){
        String nomID= fitxer.nextLine();
        String coords= fitxer.nextLine();
        String zH= fitxer.nextLine();
        
        String[] hhmm = fitxer.nextLine().split(":");
        Integer tempsV= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
        Double preu= fitxer.nextDouble();
        fitxer.nextLine(); //Salta \n
        Set<String> prefs= new HashSet<String>();
        String pref= fitxer.nextLine();
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= fitxer.nextLine();
        }
        String [] dades = fitxer.nextLine().split(":");
        String [] aux = dades[2].split("-");
        Hora inici= new Hora(Integer.parseInt(dades[1]), Integer.parseInt(aux[0]));
        Hora fi= new Hora(Integer.parseInt(aux[1]), Integer.parseInt(dades[3]));
        mundi.afegeixPuntInteres(new PuntVisitable(nomID, prefs, preu, tempsV, new FranjaHoraria(inici, fi), new Coordenades(coords, zH)));
        String inutil= fitxer.nextLine();
        while(!inutil.equals("*")) inutil= fitxer.nextLine();
    }
    
    private static void associarLloc(Scanner fitxer, Mapa mundi){
        String IDpuntInteres= fitxer.nextLine();
        String IDlloc= fitxer.nextLine();
        fitxer.nextLine(); //*
        mundi.associarLloc(IDlloc, IDpuntInteres);
    }
    
    /**
     * @pre: fitxer és obert i llest per llegir
     * @post: Crea les estructures de dades a partir de les dades del fitxer d'entrada
     */
    public static void inicialitzaAplicatiu(Scanner fitxer, List<Client> clients, Mapa mundi){
        fitxer.nextLine(); //S'ignora la línia del autor
        while(fitxer.hasNext()){
            String codiOperacio= fitxer.nextLine();
            if(codiOperacio.equals("client")) donarAltaClient(fitxer, clients);
            else if(codiOperacio.equals("lloc")) donarAltaLloc(fitxer, mundi);
            else if(codiOperacio.equals("allotjament")) donarAltaAllotjament(fitxer, mundi);
            else if(codiOperacio.equals("lloc visitable")) donarAltaPuntVisitable(fitxer, mundi);
            else if(codiOperacio.equals("associar lloc")) associarLloc(fitxer, mundi);
        }   
    }
    
    /**
     * @pre: --
     * @post: Demana les dades per a poder crear un GrupClients a partir dels Clients de l’agència
     */
    public static GrupClients crearGrup(Collection<Client> clients){
        return null;
    }
}
