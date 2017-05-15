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
    private final Integer duracio;
    private final Double preuMax;
    private PuntInteres origen;
    private PuntInteres desti;
    private Set<PuntInteres> pI;
    private Map<String, Integer> satisfaccio;
    private Boolean rutaBarata;
    private Boolean rutaCurta;
    private Boolean rutaSatisfactoria;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Viatge amb clients, categoria desitjada, inici i duració.
     */
    public Viatge(String catDesit, LocalDateTime inici, Integer duracio, Double preuMax){
        this.clients= new HashSet<Client>();
        this.pI= new HashSet<PuntInteres>();
        this.satisfaccio= new HashMap<String, Integer>();
        this.catDesit= catDesit;
        this.inici= inici;
        this.duracio= duracio;
        this.preuMax= preuMax;
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
     * @post: Assigna un Punt d'Interes com a origen del viatge
     */
    public void assignarOrigen(PuntInteres punt){
        origen= punt;
    }
    
    /** 
     * @pre: --
     * @post: Assigna un Punt d'Interes com a destí del viatge
     */
    public void assignarDesti(PuntInteres punt){
        desti= punt;
    }
    
    /** 
     * @pre: --
     * @post: Afegeix un Punt d'Interes a la llista dels punts per on es vol passar del viatge
     */
    public void afegirPI(PuntInteres punt){
        pI.add(punt);
    }
    
    /** 
     * @pre: --
     * @post: El viatge vol la Ruta més barata 
     */
    public void assignarBarata(){
        rutaBarata= true;
    }
    
    /** 
     * @pre: --
     * @post: El viatge vol la Ruta més curta 
     */
    public void assignarCurta(){
        rutaCurta= true;
    }
    
    /** 
     * @pre: --
     * @post: El viatge vol la Ruta més satisfactoria
     */
    public void assignarSatisfactoria(){
        rutaSatisfactoria= true;
    }
    
    public Map<String, Integer> obtMapSatisfaccio(){
        return satisfaccio;
    }
    
    /** 
     * @pre: --
     * @post: Retorna cert si l’allotjament “hotel” és de la categoria que desitja el conjunt de clients
     */
    public Boolean categoriaDesitjada(Allotjament hotel){
        if(hotel.obtenirCat().equals(catDesit)) return true;
        else return false;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres d’origen del viatge del grup de clients
     */
    public PuntInteres obtOrigen(){
        return origen; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres de destí del viatge del grup de clients
     */
    public PuntInteres obtDesti(){
        return desti; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la data (amb hora inclosa) de sortida del grup de clients
     */
    public LocalDateTime obtDataInici(){
        return inici;
    }
    
    /** 
     * @pre: --
     * @post: Retorna un iterador als punts d’interès prefixats que s’han de visitar sí o sí
     */
    public Set<PuntInteres> obtenirInteressos(){
        return pI;
    }
    
    public Boolean esBarata(){
        return rutaBarata;
    }
    
    public Boolean esCurta(){
        return rutaCurta;
    }
    
    public Boolean esSatisfactoria(){
        return rutaSatisfactoria;
    }
    
    public Integer nClients(){
        return clients.size();
    }
    
    public Map<String, Integer> preferenciesClients(){
        return satisfaccio;
    }
    
    public Iterator<PuntInteres> obtIteradorPI(){
        return pI.iterator();
    }
    
    public Integer obtDurada(){
        return duracio;
    }
    
    public Double obtPreuMax(){
        return preuMax;
    }
}