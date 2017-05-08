//ProActive_Travel

/**
 * @file: Viatge.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Viatge: Conté informació d'un viatge, com els clients, els punts d'interès desitjats...
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Viatge, amb uns clients, categoria desitjada, punts d'interès a visitar, origen, destí, instant d'inici i duració.
 */
public class Viatge {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private Set<Client> clients;
    private final String catDesit;
    private final LocalDateTime inici;
    private final Double duracio;
    private final PuntInteres origen;
    private final PuntInteres desti;
    private Set<PuntInteres> pI;
    private Map<String, Integer> satisfaccio;
    private String tipus;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Viatge amb categoria desitjada, punts d’interès a visitar, origen i desti, duració i inici.
     */
    public Viatge(String catDesit, PuntInteres origen, PuntInteres desti, LocalDateTime inici, Double duracio){
        clients= new HashSet<Client>();
        pI= new HashSet<PuntInteres>();
        satisfaccio= new HashMap<String, Integer>();
        this.catDesit= catDesit;
        this.inici= inici;
        this.duracio= duracio;
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Afegeix un client a la llista dels clients del viatge
     */
    public void afegirClient(Client c){
        clients.add(c);
        Iterator<String> it= c.obtPref();
        while(it.hasNext()){
            String pref= it.next();
            if(!satisfaccio.containsKey(pref)) satisfaccio.put(pref, 1);
            else satisfaccio.replace(pref, satisfaccio.get(pref)+1);
        }
    }
    
    /** 
     * @pre: --
     * @post: Afegeix un Punt d'Interes a la llista dels punts per on es vol passar del viatge
     */
    public void afegirPI(PuntInteres punt){
        pI.add(punt);
    }
    
     /** 
     * @pre: tipus == "ruta barata" || tipus == "ruta curta" || tipus == "ruta satisfactoria"
     * @post: Assigna l'String "tipus" com a tipus d'aquest viatge
     */
    public void assignarTipus(String tipus){
        this.tipus= tipus;
    }
    
    /** 
     * @pre: --
     * @post: Retorna un enter que representa el nombre de clients del conjunt que tenen la preferència “pref” entre les seves preferències personals
     */
    public Integer obtenirSatisfaccioPreferencia(String pref){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna cert si l’allotjament “hotel” és de la categoria que desitja el conjunt de clients
     */
    public Boolean categoriaDesitjada(Allotjament hotel){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el lloc d’origen del viatge del grup de clients
     */
    public Lloc obtenirOrigen(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el lloc de destí del viatge del grup de clients
     */
    public Lloc obtenirDesti(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la data (amb hora inclosa) de sortida del grup de clients
     */
    public LocalDateTime obtenirInici(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna un iterador als punts d’interès prefixats que s’han de visitar sí o sí
     */
    public Iterator<PuntInteres> obtenirInteressos(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
