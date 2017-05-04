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
    //VARIABLES LOCALS---------------------------------------------------------------------------------------------------------------------------
    private static int lineCounter= 0;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
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
        String linia= llegirLinia(fitxer);
        while(!linia.equals("*")) linia= llegirLinia(fitxer);
    }
    
    /**
     * @pre: --
     * @post: Retorna una línia llegida del Scanner i incrementa el comptador de línia
     */
    private static String llegirLinia(Scanner fitxer){
        lineCounter++;
        String c = null;
        if(fitxer.hasNextLine()){
            c= fitxer.nextLine();
            return c;
        }
        else throw new NumberFormatException();
    }
    
    /**
     * @pre: --
     * @post: Retorna un Double llegit del Scanner, canvia de línia i incrementa el comptador de línia
     */
    private static Double llegirDouble(Scanner fitxer){
        lineCounter++;
        Double num = null;
        if(fitxer.hasNextDouble()){
            num= fitxer.nextDouble();
            if(fitxer.hasNextLine()) fitxer.nextLine();
        }
        return num;
    }
    
    /**
     * @pre: --
     * @post: Tracta l'error de format durant l'entrada de les dades
     */
    private static void tractarErrorLectura(Scanner fitxer, Exception e) throws InterruptedException{
        System.err.println(e+". Línia "+lineCounter);
        ignorarFinsSeparador(fitxer);
        System.err.println("Mòdul ignorat");
        System.err.println();
        lineCounter--;
        Thread.sleep(1);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "client"
     * @post: Llegeix un client de fitxer i l'afegeix al Map clients amb clau el seu nom
     */
    private static void donarAltaClient(Scanner fitxer, Map<String, Client> clients){
        String nom= llegirLinia(fitxer);
        Set<String> prefs= new HashSet<String>();
        String pref= llegirLinia(fitxer);
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= llegirLinia(fitxer);
        }
        clients.put(nom, new Client(nom, prefs));
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void donarAltaLloc(Scanner fitxer, Mapa mundi){
        String nomID= llegirLinia(fitxer);
        String coords= llegirLinia(fitxer);
        String zH= llegirLinia(fitxer);
        mundi.afegeixLloc(new Lloc(nomID, new Coordenades(coords, zH)));
        llegirLinia(fitxer);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "allotkament"
     * @post: Llegeix un allotjament de fitxer i l'afegeix al mapa
     */
    private static void donarAltaAllotjament(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            String zH= llegirLinia(fitxer);
            String cat= llegirLinia(fitxer);
            Double preuHab= llegirDouble(fitxer);
            Set<String> prefs= new HashSet<String>();
            String pref= llegirLinia(fitxer);
            while(!pref.equals("*")){
                prefs.add(pref);
                pref= llegirLinia(fitxer);
            }
            mundi.afegeixPuntInteres(new Allotjament(nomID, prefs, preuHab, cat, new Coordenades(coords, zH)));
        } catch (InputMismatchException e){
            System.err.println("Error de lectura: Allotjament, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc visitable"
     * @post: Llegeix un puntVisitable de fitxer i l'afegeix al mapa
     */
    private static void donarAltaPuntVisitable(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            String zH= llegirLinia(fitxer);

            String[] hhmm = llegirLinia(fitxer).split(":");
            Integer tempsV= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
            Double preu= llegirDouble(fitxer);
            Set<String> prefs= new HashSet<String>();
            String pref= llegirLinia(fitxer);
            while(!pref.equals("*")){
                prefs.add(pref);
                pref= llegirLinia(fitxer);
            }
            String [] dades = llegirLinia(fitxer).split(":");
            String [] aux = dades[2].split("-");
            LocalTime inici= LocalTime.of(Integer.parseInt(dades[1].trim()), Integer.parseInt(aux[0]));
            LocalTime fi= LocalTime.of(Integer.parseInt(aux[1]), Integer.parseInt(dades[3]));
            mundi.afegeixPuntInteres(new PuntVisitable(nomID, prefs, preu, tempsV, new FranjaHoraria(inici, fi), new Coordenades(coords, zH)));
            ignorarFinsSeparador(fitxer);
        } catch (NumberFormatException | InputMismatchException e){
            System.err.println("Error de lectura: Associar Transport Urbà, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar lloc"
     * @post: Llegeix un puntInteres i lloc de fitxer associa aquest puntInteres al lloc
     */
    private static void associarLloc(Scanner fitxer, Mapa mundi){
        String IDpuntInteres= llegirLinia(fitxer);
        String IDlloc= llegirLinia(fitxer);
        llegirLinia(fitxer);
        mundi.associarLloc(IDlloc, IDpuntInteres);
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar transport"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void associarUrba(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String llocID= llegirLinia(fitxer);
            String urbaID= llegirLinia(fitxer);
            String [] hhmm= llegirLinia(fitxer).split(":");
            Integer durada= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
            Double preu= llegirDouble(fitxer);
            llegirLinia(fitxer);
            TransportUrba transp= new TransportUrba(urbaID, durada, preu);
            mundi.associarUrba(llocID, transp);
        } catch (NumberFormatException | InputMismatchException e){
            System.err.println("Error de lectura: Associar Transport Urbà, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport directe"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportDirecte(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String origenID= llegirLinia(fitxer);
            String destiID= llegirLinia(fitxer);
            String nomTrans= llegirLinia(fitxer);
            String [] hhmm= llegirLinia(fitxer).split(":");
            Integer durada= (Integer.parseInt(hhmm[0])*60)+Integer.parseInt(hhmm[1]);
            Double preu= llegirDouble(fitxer);
            llegirLinia(fitxer);

            MTDirecte mT= new MTDirecte(nomTrans, mundi.obtenirPI(origenID), mundi.obtenirPI(destiID), preu, durada);
            mundi.afegirTransportDirecte(mT);
        } catch (NumberFormatException | InputMismatchException e){
            System.err.println("Error de lectura: Associar Transport Urbà, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport indirecte"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportIndirecte(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String origenID= llegirLinia(fitxer); Lloc origen= mundi.obtenirLloc(origenID);
            String destiID= llegirLinia(fitxer); Lloc desti= mundi.obtenirLloc(destiID);
            if(origen != null && desti != null && origen != desti){
                String nomTrans= llegirLinia(fitxer);
                String [] hhmmOrigen= llegirLinia(fitxer).split(":");
                Integer tempsOrigen= (Integer.parseInt(hhmmOrigen[0])*60)+Integer.parseInt(hhmmOrigen[1]);
                String [] hhmmDesti= llegirLinia(fitxer).split(":");
                Integer tempsDesti= (Integer.parseInt(hhmmDesti[0])*60)+Integer.parseInt(hhmmDesti[1]);
                mundi.afegirConnexioMTI(nomTrans, origen, desti, tempsOrigen, tempsDesti);
                String data= llegirLinia(fitxer);
                while(!data.equals("*")){
                    String [] anyMesDia= data.split("-");
                    String hora= llegirLinia(fitxer);
                    while(!hora.equals("*") && !esData(hora)){
                        String [] horaMinuts= hora.split(":");
                        LocalDateTime horaSortida= LocalDateTime.of(Integer.parseInt(anyMesDia[0]), Integer.parseInt(anyMesDia[1]), Integer.parseInt(anyMesDia[2]), Integer.parseInt(horaMinuts[0]), Integer.parseInt(horaMinuts[1]));
                        String [] duradaHoraMinuts= llegirLinia(fitxer).split(":");
                        Integer durada= (Integer.parseInt(duradaHoraMinuts[0])*60)+Integer.parseInt(duradaHoraMinuts[1]);
                        Double preu= llegirDouble(fitxer);
                        MTIndirecte mitja= new MTIndirecte(nomTrans, origen, desti, preu, durada);
                        mundi.afegirMTIndirecte(mitja, horaSortida, origen);
                        hora= llegirLinia(fitxer);
                    }
                    data= hora;
                }
            }
            else ignorarFinsSeparador(fitxer);
        } catch (NumberFormatException | InputMismatchException e){
            System.err.println("Error de lectura: Associar Transport Urbà, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
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
            System.out.println();
        } catch (FileNotFoundException e) {
            //Si no es troba el fitxer informa i avorta
            System.err.println(e);
            System.exit(-1);
        }
        return fitxer;
    }
    
    /**
     * @pre: fitxer és obert i llest per llegir
     * @post: Crea les estructures de dades a partir de les dades del fitxer d'entrada
     */
    public static void inicialitzaAplicatiu(Scanner fitxer, Map<String, Client> clients, Mapa mundi){
        llegirLinia(fitxer); //S'ignora la línia del autor
        try{
            while(fitxer.hasNextLine()){
                System.out.println(lineCounter);
                String codiOperacio= llegirLinia(fitxer);
                if(codiOperacio.equals("client")) donarAltaClient(fitxer, clients);
                else if(codiOperacio.equals("lloc")) donarAltaLloc(fitxer, mundi);
                else if(codiOperacio.equals("allotjament")) donarAltaAllotjament(fitxer, mundi);
                else if(codiOperacio.equals("lloc visitable")) donarAltaPuntVisitable(fitxer, mundi);
                else if(codiOperacio.equals("associar lloc")) associarLloc(fitxer, mundi);
                else if(codiOperacio.equals("associar transport")) associarUrba(fitxer, mundi);
                else if(codiOperacio.equals("transport directe")) afegirTransportDirecte(fitxer, mundi);
                else if(codiOperacio.equals("transport indirecte")) afegirTransportIndirecte(fitxer, mundi);
                else{
                    Integer liniesAnteriors= lineCounter;
                    ignorarFinsSeparador(fitxer);
                    System.err.println("Línia "+liniesAnteriors+": Codi d'operació '"+codiOperacio+"' invàlid. S'ha ignorat el mòdul ("+(lineCounter-liniesAnteriors)+" línies)");
                    System.err.println();
                    Thread.sleep(1);
                }
            }
        } catch(InterruptedException iE){
            System.err.println(iE);
            System.err.println("Fatal Error 404: Si us plau, reinicii l'aplicatiu");
        }
        fitxer.close();
        System.out.println("Fitxer d'entrada processat correctament ("+lineCounter+" línies)");
    }
}
