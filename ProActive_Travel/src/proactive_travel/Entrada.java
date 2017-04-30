//ProActive_Travel

/**
 * @file: Entrada.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en 
 *         la generació de dades a partir del fitxer d'entrada inicial
 * @copyright: Public License
 */

package proactive_travel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs d'entrada
 */
public abstract class Entrada {
    
    /**
     * @pre: --
     * @post: Demana la ruta del fitxer a carregar dades i retorna un scanner a aquest 
     *        preparat per la lectura
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
    
    /** @pre: --
     *  @post: Retorna cert si "a" és una data, és a dir, si conté algún caràcter '-'
     */
    private static boolean esData(String a){
        return a.contains("-");
    }
    
    /**
     * @pre: --
     * @post: Ignora línees del fitxer fins que es troba amb un separador 
     */
    private static void ignorarFinsSeparador(Scanner fitxer){
        while(!fitxer.nextLine().equals("*")) fitxer.nextLine();
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "client"
     * @post: Llegeix un client de fitxer i l'afegeix al Map clients amb clau el seu nom
     */
    private static void donarAltaClient(Scanner fitxer, Map<String, Client> clients){
        String nom= fitxer.nextLine();
        Set<String> prefs= new HashSet<String>();
        String pref= fitxer.nextLine();
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= fitxer.nextLine();
        }
        clients.put(nom, new Client(nom, prefs));
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void donarAltaLloc(Scanner fitxer, Mapa mundi){
        String nomID= fitxer.nextLine();
        String coords= fitxer.nextLine();
        String zH= fitxer.nextLine();
        mundi.afegeixLloc(new Lloc(nomID, new Coordenades(coords, zH)));
        fitxer.nextLine(); //*
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "allotkament"
     * @post: Llegeix un allotjament de fitxer i l'afegeix al mapa
     */
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
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc visitable"
     * @post: Llegeix un puntVisitable de fitxer i l'afegeix al mapa
     */
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
        LocalTime inici= LocalTime.of(Integer.parseInt(dades[1].trim()), Integer.parseInt(aux[0]));
        LocalTime fi= LocalTime.of(Integer.parseInt(aux[1]), Integer.parseInt(dades[3]));
        mundi.afegeixPuntInteres(new PuntVisitable(nomID, prefs, preu, tempsV, new FranjaHoraria(inici, fi), new Coordenades(coords, zH)));
        ignorarFinsSeparador(fitxer);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar lloc"
     * @post: Llegeix un puntInteres i lloc de fitxer associa aquest puntInteres al lloc
     */
    private static void associarLloc(Scanner fitxer, Mapa mundi){
        String IDpuntInteres= fitxer.nextLine();
        String IDlloc= fitxer.nextLine();
        fitxer.nextLine(); //*
        mundi.associarLloc(IDlloc, IDpuntInteres);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar transport"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void associarUrba(Scanner fitxer, Mapa mundi){
        String llocID= fitxer.nextLine();
        String urbaID= fitxer.nextLine();
        String [] hhmm= fitxer.nextLine().split(":");
        Integer durada= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
        Double preu= fitxer.nextDouble();
        fitxer.nextLine(); // \n
        fitxer.nextLine(); //*
        TransportUrba transp= new TransportUrba(urbaID, durada, preu);
        mundi.associarUrba(llocID, transp);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport directe"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportDirecte(Scanner fitxer, Mapa mundi){
        String origenID= fitxer.nextLine();
        String destiID= fitxer.nextLine();
        String nomTrans= fitxer.nextLine();
        String [] hhmm= fitxer.nextLine().split(":");
        Integer durada= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
        Double preu= fitxer.nextDouble();
        fitxer.nextLine(); // \n
        fitxer.nextLine(); //*
        
        MTDirecte mT= new MTDirecte(nomTrans, mundi.obtenirPI(origenID), mundi.obtenirPI(destiID), preu, durada);
        mundi.afegirTransportDirecte(mT);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport indirecte"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportIndirecte(Scanner fitxer, Mapa mundi){
        String origenID= fitxer.nextLine(); Lloc origen= mundi.obtenirLloc(origenID);
        String destiID= fitxer.nextLine(); Lloc desti= mundi.obtenirLloc(destiID);
        if(origen != null && desti != null){
            String nomTrans= fitxer.nextLine();
            String [] hhmmOrigen= fitxer.nextLine().split(":");
            Integer tempsOrigen= (Integer.parseInt(hhmmOrigen[0])*60)+Integer.parseInt(hhmmOrigen[1]);
            String [] hhmmDesti= fitxer.nextLine().split(":");
            Integer tempsDesti= (Integer.parseInt(hhmmDesti[0])*60)+Integer.parseInt(hhmmDesti[1]);
            mundi.afegirConnexioMTI(nomTrans, origen, desti, tempsOrigen, tempsDesti);
            String data= fitxer.nextLine();
            while(!data.equals("*")){
                String [] anyMesDia= data.split("-");
                String hora= fitxer.nextLine();
                while(!hora.equals("*") && !esData(hora)){
                    String [] horaMinuts= hora.split(":");
                    LocalDateTime horaSortida= LocalDateTime.of(Integer.parseInt(anyMesDia[0]), Integer.parseInt(anyMesDia[1]), Integer.parseInt(anyMesDia[2]), Integer.parseInt(horaMinuts[0]), Integer.parseInt(horaMinuts[1]));
                    String [] duradaHoraMinuts= fitxer.nextLine().split(":");
                    Integer durada= (Integer.parseInt(duradaHoraMinuts[0])*60)+Integer.parseInt(duradaHoraMinuts[1]);
                    Double preu= fitxer.nextDouble();
                    MTIndirecte mitja= new MTIndirecte(nomTrans, origen, desti, preu, durada);
                    mundi.afegirMTIndirecte(mitja, horaSortida, origen);
                    fitxer.nextLine(); // \n
                    hora= fitxer.nextLine();
                }
                data= hora;
            }
        }
        else ignorarFinsSeparador(fitxer);
    }
    
    /**
     * @pre: fitxer és obert i llest per llegir
     * @post: Crea les estructures de dades a partir de les dades del fitxer d'entrada
     */
    public static void inicialitzaAplicatiu(Scanner fitxer, Map<String, Client> clients, Mapa mundi){
        fitxer.nextLine(); //S'ignora la línia del autor
        while(fitxer.hasNext()){
            String codiOperacio= fitxer.nextLine();
            if(codiOperacio.equals("client")) donarAltaClient(fitxer, clients);
            else if(codiOperacio.equals("lloc")) donarAltaLloc(fitxer, mundi);
            else if(codiOperacio.equals("allotjament")) donarAltaAllotjament(fitxer, mundi);
            else if(codiOperacio.equals("lloc visitable")) donarAltaPuntVisitable(fitxer, mundi);
            else if(codiOperacio.equals("associar lloc")) associarLloc(fitxer, mundi);
            else if(codiOperacio.equals("associar transport")) associarUrba(fitxer, mundi);
            else if(codiOperacio.equals("transport directe")) afegirTransportDirecte(fitxer, mundi);
            else if(codiOperacio.equals("transport indirecte")) afegirTransportIndirecte(fitxer, mundi);
        }   
    }
}
