//ProActive_Travel

/**
 * @file Lloc.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Lloc: Conté informació d'un Lloc que conté Punts d'Interès
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un Lloc, amb unes coordenades, estacions, punts d'interès i transports
 */
public class Lloc{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;                               ///< @brief Conté el nom del Lloc
    private final Coordenades coords;                       ///< @brief Conté les Coordenades del Lloc
    private Map<String, Estacio> estacions;                 ///< @brief Conté les estacions amb accés per NomEstacio
    private Set<PuntInteres> punts;                         ///< @brief Conté els PuntInteres associats al Lloc
    private Set<MitjaTransport> transportsUrbans;           ///< @brief Conté els MitjaTransport Urbans del Lloc
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post  Es crea un lloc de nom "nom" i coordeandes "coords" 
     * @post  Constructor 
     */ 
    public Lloc(String nom, Coordenades coords){
        this.nom= nom;
        this.coords= coords;
        punts= new HashSet<>();
        estacions= new HashMap<>();
        transportsUrbans= new TreeSet<>();
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el nom del lloc 
     * @brief Retorna el nom del lloc
     */
    public String obtNom(){
        return nom;
    }
    
    /** 
     * @pre --
     * @post Retorna les Coordenades del lloc 
     * @brief Retorna les Coordenades del lloc 
     */
    public Coordenades obtCoordenades(){
        return coords;
    }
    
    /** 
     * @pre --
     * @post Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una sortida a l'estació de nom "nomEst" cap al lloc "desti"
     *         amb un temps d'origen de "temps"
     * @brief Afegeix una Conexxió de Sortida de MTIndirecte 
     */
    public void afegirConnexioSortidaMTI(String nomEst, Lloc desti, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst, this));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioSortida(desti, temps);
    }
    
    /** 
     * @pre --
     * @post Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una arribada a l'estació de nom "nomEst" des del lloc "origen"
     *         amb un temps de destí de "temps"
     * @brief Afegeix una Conexxió d'Arribada de MTIndirecte 
     */
    public void afegirConnexioArribadaMTI(String nomEst, Lloc origen, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst, this));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioArribada(origen, temps);
    }
    
    /** 
     * @pre Ha d'existir una connexió de sortida a l'estació del mitjà "mitja"
     * @post S'afegeix el MTIndirecte com a sortida de l'estació corresponent
     * @brief Afegeix una sortida de MTIndirecte
     */
    public void afegirSortidaMTI(MTIndirecte mitja, LocalDateTime horaSortida){
        Estacio actual= estacions.get(mitja.obtNom());
        actual.afegirSortida(mitja, horaSortida);
    }
    
    /** 
     * @pre --
     * @post Afegeix el punt d'interès "pI" al la llista de PuntInteres del Lloc 
     * @brief Afegeix el punt d'interès "pI" al la llista de PuntInteres del Lloc 
     */
    public void afegirPuntInteres(PuntInteres pI){
        punts.add(pI);
    }
    
    /** 
     * @pre --
     * @post Si no existeix, afegeix el transport urbà al Lloc
     * @brief Afegeix el transport urbà al Lloc
     * @throws Exception si ja existeix
     */
    public void afegirTransportUrba(MitjaTransport mT) throws Exception{
        if(!transportsUrbans.contains(mT)) transportsUrbans.add(mT);
        else throw new Exception("TransportUrbaRepetitException");
    }
    
    /** 
     * @pre "nomEstacio" existeix a Estacions
     * @post Retorna l'Estacio del Lloc de nom "nomEstacio" 
     * @brief Retorna l'Estacio del Lloc de nom "nomEstacio" 
     */
    public Estacio obtEstacio(String nomEstacio){
        return estacions.get(nomEstacio);
    }
    
    /** 
     * @pre --
     * @post Retorna un Iterador als MitjaTransport Urbans del Lloc 
     * @brief Retorna un Iterador als MitjaTransport Urbans del Lloc 
     */
    public Iterator<MitjaTransport> obtTransportUrba(){
        return transportsUrbans.iterator();
    }
    
    /** 
     * @pre --
     * @post Retorna un Iterador als PuntInteres del Lloc 
     * @brief Retorna un Iterador als PuntsInteres del Lloc 
     */
    public Iterator<PuntInteres> obtPuntsInteres(){
        return punts.iterator(); 
    }
    
    /** 
     * @pre --
     * @post Retorna un Iterador a les Estacions del Lloc 
     * @brief Retorna un Iterador a les Estacions del Lloc 
     */
    public Iterator<Estacio> obtEstacions(){
        return estacions.values().iterator();
    }
    
    /** 
     * @pre --
     * @post Retorna el nom com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el nom com a sobreescriptura del mètode d'Object toString
     */
    @Override
    public String toString(){
        return nom;
    }

    /** 
     * @pre --
     * @post Sobreescriptura del hashCode generada automàticament
     * @brief Sobreescriptura del hashCode generada automàticament
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.nom);
        return hash;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un Lloc i és igual al Lloc this
     * @brief Retorna cert si l'Objecte o és un Lloc i és igual al Lloc this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Lloc){
            Lloc ll= (Lloc)o;
            return nom.equals(ll.nom);
        }
        else return false;
    }
}