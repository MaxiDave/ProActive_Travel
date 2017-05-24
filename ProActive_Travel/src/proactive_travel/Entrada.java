//ProActive_Travel

/**
 * @file Entrada.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en 
 *         la generació de dades a partir del fitxer d'entrada inicial
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Classe abstracte que s'encarrega dels càlculs d'entrada
 */
public abstract class Entrada {
    //VARIABLES LOCALS---------------------------------------------------------------------------------------------------------------------------
    public static int lineCounter;      ///< @brief Representa un contador a les línies que es van processant del fitxer
    public static int warnings;         ///< @brief Representa un contador als warnings que es van detectant al fitxer
    public static Boolean fail;         ///< @brief Conté informació de si l'entrada s'ha processat bé (TRUE) o amb error (FALSE)
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /** @pre --
     *  @post Retorna cert si "a" és una data, és a dir, si conté algún caràcter '-'
     *  @brief "a" és una Data?
     */
    private static boolean esData(String a){
        return a.contains("-");
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Ignora línees del fitxer fins que es troba amb un separador 
     * @brief Ignora fins al següent separador
     */
    private static void ignorarFinsSeparador(Scanner fitxer){
        String linia= llegirLinia(fitxer);
        while(!linia.equals("*")) linia= llegirLinia(fitxer);
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Incrementa el comptador de línia i retorna la següent línia llegida del Scanner "fitxer"
     * @brief Llegeix una línia del Scanner "fitxer"
     */
    private static String llegirLinia(Scanner fitxer){
        lineCounter++;
        return fitxer.nextLine();
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Retorna un Double llegit del Scanner, canvia de línia i incrementa el comptador de línia
     * @brief Lleigeix un double + CR del Scanner "fitxer"
     */
    private static Double llegirDouble(Scanner fitxer){
        lineCounter++;
        Double num= fitxer.nextDouble();
        fitxer.nextLine();
        if(num < 0) throw new NumberFormatException();
        return num;
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Retorna un Integer llegit del Scanner, canvia de línia i incrementa el comptador de línia
     * @brief Lleigeix un enter + CR del Scanner "fitxer"
     */
    private static Integer llegirInteger(Scanner fitxer){
        lineCounter++;
        Integer num= fitxer.nextInt();
        fitxer.nextLine();
        if(num <= 0) throw new NumberFormatException();
        return num;
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Tracta l'advertència de mal format durant l'entrada de les dades (Warning) tot ignorant fins a separador 
     *       i escriurà informació sobre aquest problema al PrintWriter "error"
     * @brief Tracta el Warning de Lectura tot ignorant fins a separador i escribint l'advertència a PrintWriter "error"
     */
    private static void tractarErrorLectura(PrintWriter error, Scanner fitxer, Exception e){
        error.println("Línia "+lineCounter+": "+e);
        ignorarFinsSeparador(fitxer);
        error.println("Mòdul ignorat (En cas de múltiples viatges, s'ignora l'antic)");
        error.println();
        warnings++;
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Llegeix de l'Scanner "fitxer" una expresió hh:mm i retorna el valor en minuts d'una expressió hh:mm si està correctament escrita
     * @brief Llegeix de l'Scanner "fitxer" una expressió hh:mm i la retorna el valor en format Integer minuts
     * @throws NumberFormatException si l'expressió no està correctament escrita
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
     * @pre --
     * @post Rep una expresió (string) en format hh:mm i retorna el LocalTime corresponent si està correctament escrita
     * @brief Rep una expresió (string) en format hh:mm i retorna el LocalTime corresponent
     * @throws NumberFormatException si l'expressió no està correctament escrita 
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
     * @pre --
     * @post Si el format de "data" és correcte (yyyy-mm-dd) el retorna en forma de LocalDate
     * @brief Retorna "data" en forma de LocalDate
     * @throws DateTimeException si el format de "data" no és correcte
     */
    private static LocalDate processarData(String data){
        String [] anyMesDia= data.split("-");
        return LocalDate.of(Integer.parseInt(anyMesDia[0]), Integer.parseInt(anyMesDia[1]), Integer.parseInt(anyMesDia[2]));
    }
    
    /**
     * @pre Scanner "fitxer" obert i preparat per lectura
     * @post Llegeix preferències de l'Scanner "fitxer" fins que troba un separador i les retorna en forma de Set
     * @brief Llegeix preferències de l'Scanner "fitxer" fins que troba un separador i les retorna en forma de Set
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
     * @pre Anterior valor llegit de fitxer és "client", Scanner "fitxer" obert i preparat per lectura
     * @post Llegeix un client d l'Scanner "fitxer" i l'afegeix al Map "clients" amb clau el seu nom
     * @brief Dona d'alta un nou Client i l'afegeix al Map "clients"
     */
    private static void donarAltaClient(Scanner fitxer, Map<String, Client> clients) {
        String nom= llegirLinia(fitxer);
        Set<String> prefs= llegirPreferencies(fitxer);
        clients.put(nom, new Client(nom, prefs));
    }
    
    /**
     * @pre Anterior valor llegit de fitxer és "lloc", Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un lloc d l'Scanner "fitxer" i l'afegeix al Mapa "mundi" si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Dona d'alta un nou Lloc i l'afegeix al Mapa "mundi"
     */
    private static void donarAltaLloc(PrintWriter error, Scanner fitxer, Mapa mundi){
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
     * @pre Anterior valor llegit de fitxer és "allotjament", , Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un allotjament d l'Scanner "fitxer" i l'afegeix al Mapa "mundi" si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Dona d'alta un nou Allotjament i l'afegeix al Mapa "mundi"
     */
    private static void donarAltaAllotjament(PrintWriter error, Scanner fitxer, Mapa mundi){
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
     * @pre Anterior valor llegit de fitxer és "lloc visitable", , Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un PuntVisitable de l'Scanner "fitxer" i l'afegeix al Mapa "mundi" si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Dona d'alta un nou PuntVisitable i l'afegeix al Mapa "mundi"
     */
    private static void donarAltaPuntVisitable(PrintWriter error, Scanner fitxer, Mapa mundi){
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
     * @pre Anterior valor llegit de fitxer és "associar lloc", Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un PuntInteres i un Lloc de l'Scanner "fitxer" i associa el Lloc al PuntInteres si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Associa un Lloc a un PuntInteres del Mapa "mundi"
     */
    private static void associarLloc(PrintWriter error, Scanner fitxer, Mapa mundi){
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
     * @pre Anterior valor llegit de fitxer és "associar transport", Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un Lloc i un MitjaTransport de l'Scanner "fitxer" i associa aquest mitjà al Lloc si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Associa un MitjaTransport a un Lloc del Mapa "mundi"
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
     * @pre Anterior valor llegit de fitxer és "transport directe", , Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un MTDirecte de l'Scanner "fitxer" entre dos PuntInteres i l'afegeix al Mapa "mundi" si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Afegeix un nou MTDirecte al Mapa "mundi"
     */
    private static void afegirTransportDirecte(PrintWriter error, Scanner fitxer, Mapa mundi){
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
     * @pre Anterior valor llegit de fitxer és "transport indirecte", , Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix un MTIndirecte de l'Scanner "fitxer" entre dos Estacions i l'afegeix al Mapa "mundi" si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Afegeix un nou MTIndirecte al Mapa "mundi"
     */
    private static void afegirTransportIndirecte(PrintWriter error, Scanner fitxer, Mapa mundi){
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
                        MTIndirecte mitja= new MTIndirecte(nomTrans, origen.obtEstacio(nomTrans), desti.obtEstacio(nomTrans), preu, durada);
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
     * @pre Anterior valor llegit de fitxer és "viatge", , Scanner "fitxer" obert i preparat per lectura, PrintWriter "error" obert i preparat per escriptura
     * @post Llegeix i Retorna un Viatge llegit de l'Scanner "fitxer", que sobreescriurà l'alterior (Si n'hi havia), si s'ha processat correctament, altrament Tracta l'error de Lectura
     * @brief Llegeix i Retorna un nou Viatge que sobreescriurà l'alterior (Si n'hi havia)
     */
    private static Viatge afegirViatge(PrintWriter error, Scanner fitxer, Mapa mundi, Map<String, Client> clients){
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
                viatge.afegirPI((PuntInteres)punt);
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
            return viatge;
        } catch (Exception e){
            tractarErrorLectura(error, fitxer, e);
            return null;
        }
    }
    
    /**
     * @pre File "file" és obert i llest per llegir
     * @post Crea les estructures de dades a partir de les dades del fitxer d'entrada i retorna l'últim Viatge entrat (Considerarem només 1 viatge per fitxer)
     * @brief Crea les estructures de dades a partir de les dades del fitxer d'entrada i retorna l'últim Viatge entrat
     */
    public static Viatge inicialitzaAplicatiu(File file, Map<String, Client> clients, Mapa mundi){
        PrintWriter error = null;
        try{
            error= new PrintWriter("error.txt", "UTF-8");
            try{
                Viatge viatge= null;
                lineCounter= 0;
                warnings= 0;
                fail= false;
                Scanner fitxer= new Scanner(file).useLocale(Locale.US);
                llegirLinia(fitxer); //S'ignora la línia del autor
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
                    else if(codiOperacio.equals("viatge")){
                        Viatge aux= afegirViatge(error, fitxer, mundi, clients);
                        if(aux != null) viatge= aux;
                    }
                    else{
                        Integer liniesAnteriors= lineCounter;
                        ignorarFinsSeparador(fitxer);
                        error.println("Línia "+liniesAnteriors+": Codi d'operació '"+codiOperacio+"' invàlid. S'ha ignorat el mòdul ("+(lineCounter-liniesAnteriors)+" línies)");
                        error.println();
                        warnings++;
                    }
                }
                error.close();
                fitxer.close();
                return viatge;
            } catch(FileNotFoundException | NoSuchElementException iE){
                fail= true;
                error.println(iE);
                error.println("Fatal Error 404: Si us plau torni-ho a intentar, ens sap greu :(");
                return null;
            }
        } catch(FileNotFoundException | UnsupportedEncodingException ex){
            fail= true;
            error.println(ex);
            error.println("Fatal Error 404: Si us plau torni-ho a intentar, ens sap greu :(");
            return null;
        } finally {
            error.close();
        }
    }
}
