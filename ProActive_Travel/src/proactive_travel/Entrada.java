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
        return fitxer.nextLine();
    }
    
    /**
     * @pre: --
     * @post: Retorna un Double llegit del Scanner, canvia de línia i incrementa el comptador de línia
     */
    private static Double llegirDouble(Scanner fitxer){
        lineCounter++;
        Double num= fitxer.nextDouble();
        fitxer.nextLine();
        if(num < 0) throw new NumberFormatException();
        return num;
    }
    
    /**
     * @pre: --
     * @post: Retorna un Integer llegit del Scanner, canvia de línia i incrementa el comptador de línia
     */
    private static Integer llegirInteger(Scanner fitxer){
        lineCounter++;
        Integer num= fitxer.nextInt();
        fitxer.nextLine();
        if(num <= 0) throw new NumberFormatException();
        return num;
    }
    
    /**
     * @pre: --
     * @post: Tracta l'error de format durant l'entrada de les dades
     */
    private static void tractarErrorLectura(Scanner fitxer, Exception e) throws InterruptedException{
        System.err.println("Línia "+lineCounter+": "+e);
        ignorarFinsSeparador(fitxer);
        System.err.println("Mòdul ignorat");
        System.err.println();
        Thread.sleep(1);
    }
    
    /**
     * @pre: --
     * @post: Llegeix de fitxer una expresió hh:mm i retorna el valor en minuts d'una expressió hh:mm si està correctament escrita, altrament dispara excepció
     */
    private static Integer processarTemps(Scanner fitxer){
        String[] hhmm = llegirLinia(fitxer).split(":");
        if(hhmm.length != 2) throw new NumberFormatException();
        else{
            Integer hh= Integer.parseInt(hhmm[0].trim());
            Integer mm= Integer.parseInt(hhmm[1].trim());
            if(hh < 0 || mm < 0 || hh > 23 || mm > 59) throw new NumberFormatException();
            return (hh*60)+mm;
        }
    }
    
    /**
     * @pre: --
     * @post: Rep una expresió (string) en format hh:mm i retorna el LocalTime corresponent si està correctament escrita, altrament dispara excepció
     */
    private static LocalTime processarHora(String hora){
        String[] hhmm = hora.split(":");
        if(hhmm.length != 2) throw new NumberFormatException();
        else{
            Integer hh= Integer.parseInt(hhmm[0].trim());
            Integer mm= Integer.parseInt(hhmm[1].trim());
            return LocalTime.of(hh, mm);
        }
    }
    
    /**
     * @pre: --
     * @post: Si el format és correcte (yyyy-mm-dd) el retorna en forma de LocalDate, altrament dispara una excepció
     */
    private static LocalDate processarData(String data){
        String [] anyMesDia= data.split("-");
        return LocalDate.of(Integer.parseInt(anyMesDia[0]), Integer.parseInt(anyMesDia[1]), Integer.parseInt(anyMesDia[2]));
    }
    
    /**
     * @pre: --
     * @post: Llegeix preferències de fitxer fins que troba un separador i les retorna en forma de Set
     */
    private static Set<String> llegirPreferencies(Scanner fitxer){
        Set<String> prefs= new HashSet<String>();
        String pref= llegirLinia(fitxer);
        while(!pref.equals("*")){
            prefs.add(pref);
            pref= llegirLinia(fitxer);
        }
        return prefs;
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "client"
     * @post: Llegeix un client de fitxer i l'afegeix al Map clients amb clau el seu nom
     */
    private static void donarAltaClient(Scanner fitxer, Map<String, Client> clients){
        String nom= llegirLinia(fitxer);
        Set<String> prefs= llegirPreferencies(fitxer);
        clients.put(nom, new Client(nom, prefs));
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void donarAltaLloc(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            if(coords.contains(",")){
                String zH= llegirLinia(fitxer);
                mundi.afegeixLloc(new Lloc(nomID, new Coordenades(coords, zH)));
                llegirLinia(fitxer);
            }
            else throw new NumberFormatException();
        } catch (NumberFormatException e){
            System.err.println("Error de lectura: Coordenades invàlides, no es llegeix el lloc");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "allotjament"
     * @post: Llegeix un allotjament de fitxer i l'afegeix al mapa
     */
    private static void donarAltaAllotjament(Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            String zH= llegirLinia(fitxer);
            String cat= llegirLinia(fitxer);
            Double preuHab= llegirDouble(fitxer);
            Set<String> prefs= llegirPreferencies(fitxer);
            mundi.afegeixPuntInteres(new Allotjament(nomID, prefs, preuHab, cat, new Coordenades(coords, zH)));
        } catch (InputMismatchException | NumberFormatException e){
            System.err.println("Error de lectura: Coordenades invàlides, no es llegeix l'allotjament");
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
            Integer tempsV= processarTemps(fitxer);
            Double preu= llegirDouble(fitxer);
            Set<String> prefs= llegirPreferencies(fitxer);
            String [] dades = llegirLinia(fitxer).split(":");
            String [] aux = dades[2].split("-");
            LocalTime inici= processarHora(dades[1]+aux[0]);
            LocalTime fi= processarHora(aux[1]+dades[3]);
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
            Integer durada= processarTemps(fitxer);
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
            Integer durada= processarTemps(fitxer);
            Double preu= llegirDouble(fitxer);
            llegirLinia(fitxer);

            MTDirecte mT= new MTDirecte(nomTrans, mundi.obtenirPI(origenID), mundi.obtenirPI(destiID), preu, durada);
            mundi.afegirTransportDirecte(mT);
        } catch (Exception e){
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
            if(origen != desti){
                String nomTrans= llegirLinia(fitxer);
                Integer tempsOrigen= processarTemps(fitxer);
                Integer tempsDesti= processarTemps(fitxer);
                mundi.afegirConnexioMTI(nomTrans, origen, desti, tempsOrigen, tempsDesti);
                String data= llegirLinia(fitxer);
                while(!data.equals("*")){
                    LocalDate anyMesDia= processarData(data);
                    String hora= llegirLinia(fitxer);
                    while(!hora.equals("*") && !esData(hora)){
                        LocalTime horaMinuts= processarHora(hora);
                        LocalDateTime horaSortida= LocalDateTime.of(anyMesDia, horaMinuts);
                        Integer durada= processarTemps(fitxer);
                        Double preu= llegirDouble(fitxer);
                        MTIndirecte mitja= new MTIndirecte(nomTrans, origen, desti, preu, durada);
                        mundi.afegirMTIndirecte(mitja, horaSortida, origen);
                        hora= llegirLinia(fitxer);
                    }
                    data= hora;
                }
            }
            else throw new Exception("OrigenDestiIgualsException");
        } catch (Exception e){
            System.err.println("Error de lectura: Associar Transport Urbà, no es llegeix");
            tractarErrorLectura(fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "viatge"
     * @post: Llegeix un viatge de fitxer i l'afegeix a la llista de viatges
     */
    private static void afegirViatge(Scanner fitxer, Mapa mundi, List<Viatge> viatges, Map<String, Client> clients) throws InterruptedException{
        try{
            LocalDate anyMesDia= processarData(llegirLinia(fitxer));
            LocalTime horaMinuts= processarHora(llegirLinia(fitxer));
            LocalDateTime sortidaViatge= LocalDateTime.of(anyMesDia, horaMinuts);
            Integer dies= llegirInteger(fitxer);
            Double preuMax= llegirDouble(fitxer);
            String catDesitjada= llegirLinia(fitxer);
            
            Viatge viatge= new Viatge(catDesitjada, sortidaViatge, dies, preuMax);
            String nomClient= llegirLinia(fitxer);
            while(!nomClient.equals("*")){
                try{
                    Client aux= clients.get(nomClient);
                    if(aux == null) throw new Exception("ClientInexistentException");
                    else viatge.afegirClient(aux);
                } catch(Exception e){
                    System.err.println(e);
                    System.err.println("Client inexistent: S'ignora");
                }
                nomClient= llegirLinia(fitxer);
            }
            PuntInteres origen= mundi.obtenirPI(llegirLinia(fitxer));
            viatge.assignarOrigen(origen);
            PuntInteres punt= mundi.obtenirPI(llegirLinia(fitxer));
            String puntAux= llegirLinia(fitxer);
            while(!puntAux.equals("*")){
                viatge.afegirPI(punt);
                punt= mundi.obtenirPI(puntAux);
                puntAux= llegirLinia(fitxer);
            }
            viatge.assignarDesti(punt);
            String ruta= llegirLinia(fitxer);
            while(!ruta.equals("*")){
                try{
                    if(ruta.equals("ruta barata")) viatge.assignarBarata();
                    else if(ruta.equals("ruta curta")) viatge.assignarCurta();
                    else if(ruta.equals("ruta satisfactoria")) viatge.assignarSatisfactoria();
                    else throw new InputMismatchException();
                } catch (InputMismatchException e){
                    System.err.println("Error de lectura: Tipus de ruta desconegut, s'ignora");
                }
                ruta= llegirLinia(fitxer);
            }
        } catch (Exception e){
            System.err.println("Error de lectura: Viatge, no es llegeix");
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
    public static void inicialitzaAplicatiu(Scanner fitxer, Map<String, Client> clients, Mapa mundi, List<Viatge> viatges){
        llegirLinia(fitxer); //S'ignora la línia del autor
        try{
            while(fitxer.hasNextLine()){
                String codiOperacio= llegirLinia(fitxer);
                if(codiOperacio.equals("client")) donarAltaClient(fitxer, clients);
                else if(codiOperacio.equals("lloc")) donarAltaLloc(fitxer, mundi);
                else if(codiOperacio.equals("allotjament")) donarAltaAllotjament(fitxer, mundi);
                else if(codiOperacio.equals("lloc visitable")) donarAltaPuntVisitable(fitxer, mundi);
                else if(codiOperacio.equals("associar lloc")) associarLloc(fitxer, mundi);
                else if(codiOperacio.equals("associar transport")) associarUrba(fitxer, mundi);
                else if(codiOperacio.equals("transport directe")) afegirTransportDirecte(fitxer, mundi);
                else if(codiOperacio.equals("transport indirecte")) afegirTransportIndirecte(fitxer, mundi);
                else if(codiOperacio.equals("viatge")) afegirViatge(fitxer, mundi, viatges, clients);
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
