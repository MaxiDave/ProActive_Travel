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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs d'entrada
 */
public abstract class Entrada {
    //VARIABLES LOCALS---------------------------------------------------------------------------------------------------------------------------
    public static int lineCounter;
    public static int warnings;
    public static Boolean fail;
    
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
    private static void tractarErrorLectura(PrintWriter error, Scanner fitxer, Exception e) throws InterruptedException{
        error.println("Línia "+lineCounter+": "+e);
        ignorarFinsSeparador(fitxer);
        error.println("Mòdul ignorat");
        error.println();
        warnings++;
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
    private static void donarAltaClient(Scanner fitxer, Map<String, Client> clients) {
        String nom= llegirLinia(fitxer);
        Set<String> prefs= llegirPreferencies(fitxer);
        clients.put(nom, new Client(nom, prefs));
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void donarAltaLloc(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
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
            error.println("Error de lectura: Coordenades invàlides, no es llegeix el lloc");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "allotjament"
     * @post: Llegeix un allotjament de fitxer i l'afegeix al mapa
     */
    private static void donarAltaAllotjament(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            String zH= llegirLinia(fitxer);
            String cat= llegirLinia(fitxer);
            Double preuHab= llegirDouble(fitxer);
            Set<String> prefs= llegirPreferencies(fitxer);
            mundi.afegeixPuntInteres(new Allotjament(nomID, prefs, preuHab, cat, new Coordenades(coords, zH)));
        } catch (Exception e){
            error.println("Error de lectura: Coordenades invàlides, no es llegeix l'allotjament");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "lloc visitable"
     * @post: Llegeix un puntVisitable de fitxer i l'afegeix al mapa
     */
    private static void donarAltaPuntVisitable(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            String nomID= llegirLinia(fitxer);
            String coords= llegirLinia(fitxer);
            String zH= llegirLinia(fitxer);
            Integer tempsV= processarTemps(fitxer);
            Double preu= llegirDouble(fitxer);
            Set<String> prefs= llegirPreferencies(fitxer);
            String [] dades = llegirLinia(fitxer).split(":");
            String [] aux = dades[2].split("-");
            LocalTime inici= processarHora(dades[1]+":"+aux[0]);
            LocalTime fi= processarHora(aux[1]+":"+dades[3]);
            mundi.afegeixPuntInteres(new PuntVisitable(nomID, prefs, preu, tempsV, inici, fi, new Coordenades(coords, zH)));
            ignorarFinsSeparador(fitxer);
        } catch (Exception e){
            error.println("Error de lectura: Donar alta Lloc Visitable, no es llegeix");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar lloc"
     * @post: Llegeix un puntInteres i lloc de fitxer associa aquest puntInteres al lloc
     */
    private static void associarLloc(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
        try {
            PuntInteres pI= mundi.obtenirPI(llegirLinia(fitxer));
            Lloc lloc= mundi.obtenirLloc(llegirLinia(fitxer));
            llegirLinia(fitxer);
            mundi.associarLloc(lloc, pI);
        } catch (Exception e) {
            error.println("Error de lectura: AssociarLloc, no es llegeix");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "associar transport"
     * @post: Llegeix un lloc de fitxer i l'afegeix al mapa
     */
    private static void associarUrba(PrintWriter error, Scanner fitxer, Mapa mundi){
        try{
            Lloc ll= mundi.obtenirLloc(llegirLinia(fitxer));
            String urbaID= llegirLinia(fitxer);
            Integer durada= processarTemps(fitxer);
            Double preu= llegirDouble(fitxer);
            llegirLinia(fitxer);
            MitjaTransport transp= new MitjaTransport(urbaID, preu, durada);
            mundi.associarUrba(ll, transp);
        } catch (Exception e){
            error.println(e);
            error.println("No existeix el Lloc i/o el PuntInteres: S'ignora");
            warnings++;
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport directe"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportDirecte(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            PuntInteres origen= mundi.obtenirPI(llegirLinia(fitxer));
            PuntInteres desti= mundi.obtenirPI(llegirLinia(fitxer));
            String nomTrans= llegirLinia(fitxer);
            Integer durada= processarTemps(fitxer);
            Double preu= llegirDouble(fitxer);
            llegirLinia(fitxer);

            MTDirecte mT= new MTDirecte(nomTrans, origen, desti, preu, durada);
            mundi.afegirTransportDirecte(mT);
        } catch (Exception e){
            error.println("Error de lectura: Transport Directe, no es llegeix");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "transport indirecte"
     * @post: Llegeix dos llocs de fitxer i crea un mitjà de transport entre aquests dos llocs
     */
    private static void afegirTransportIndirecte(PrintWriter error, Scanner fitxer, Mapa mundi) throws InterruptedException{
        try{
            Lloc origen= mundi.obtenirLloc(llegirLinia(fitxer));
            Lloc desti= mundi.obtenirLloc(llegirLinia(fitxer));
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
            error.println("Error de lectura: Transport Indirecte, no es llegeix");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: Anterior valor llegit de fitxer és "viatge"
     * @post: Llegeix un viatge de fitxer i l'afegeix a la llista de viatges
     */
    private static void afegirViatge(PrintWriter error, Scanner fitxer, Mapa mundi, List<Viatge> viatges, Map<String, Client> clients) throws InterruptedException{
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
                    error.println(e);
                    error.println("Client inexistent: S'ignora");
                    warnings++;
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
                    error.println("Error de lectura: Tipus de ruta desconegut, s'ignora");
                    warnings++;
                }
                ruta= llegirLinia(fitxer);
            }
            viatges.add(viatge);
        } catch (Exception e){
            error.println("Error de lectura: Viatge, no es llegeix");
            tractarErrorLectura(error, fitxer, e);
        }
    }
    
    /**
     * @pre: fitxer és obert i llest per llegir
     * @post: Crea les estructures de dades a partir de les dades del fitxer d'entrada
     */
    public static void inicialitzaAplicatiu(File file, Map<String, Client> clients, Mapa mundi, List<Viatge> viatges){
        try{
            lineCounter= 0;
            warnings= 0;
            fail= false;
            PrintWriter error = new PrintWriter("error.txt", "UTF-8");
            Scanner fitxer= new Scanner(file).useLocale(Locale.US);
            llegirLinia(fitxer); //S'ignora la línia del autor
            try{
                while(fitxer.hasNextLine()){
                    String codiOperacio= llegirLinia(fitxer);
                    if(codiOperacio.equals("client")) donarAltaClient(fitxer, clients);
                    else if(codiOperacio.equals("lloc")) donarAltaLloc(error, fitxer, mundi);
                    else if(codiOperacio.equals("allotjament")) donarAltaAllotjament(error, fitxer, mundi);
                    else if(codiOperacio.equals("lloc visitable")) donarAltaPuntVisitable(error, fitxer, mundi);
                    else if(codiOperacio.equals("associar lloc")) associarLloc(error, fitxer, mundi);
                    else if(codiOperacio.equals("associar transport")) associarUrba(error, fitxer, mundi);
                    else if(codiOperacio.equals("transport directe")) afegirTransportDirecte(error, fitxer, mundi);
                    else if(codiOperacio.equals("transport indirecte")) afegirTransportIndirecte(error, fitxer, mundi);
                    else if(codiOperacio.equals("viatge")) afegirViatge(error, fitxer, mundi, viatges, clients);
                    else{
                        Integer liniesAnteriors= lineCounter;
                        ignorarFinsSeparador(fitxer);
                        error.println("Línia "+liniesAnteriors+": Codi d'operació '"+codiOperacio+"' invàlid. S'ha ignorat el mòdul ("+(lineCounter-liniesAnteriors)+" línies)");
                        error.println();
                        warnings++;
                    }
                }
            } catch(InterruptedException iE){
                fail= true;
                error.println(iE);
                error.println("Fatal Error 404: Si us plau torni-ho a intentar, ens sap greu :(");
            }
            error.close();
            fitxer.close();
        } catch(FileNotFoundException | UnsupportedEncodingException ex){
            fail= true;
        }
    }
}
