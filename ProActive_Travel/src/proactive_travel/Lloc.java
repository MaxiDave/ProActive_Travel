//ProActive_Travel

/**
 * @file: Lloc.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Lloc: Conté informació d'un Lloc que conté Punts d'Interès
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Lloc, amb unes coordenades, estacions, punts d'interès i transports
 */
public class Lloc {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;
    private final Coordenades coords;
    private Map<String, Estacio> estacions;
    private Set<PuntInteres> punts;
    private Set<TransportUrba> transports;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post:  Es crea un lloc de nom "n" i coordeandes "coor" 
     */ 
    public Lloc(String n, Coordenades coor){
        nom= n;
        coords= coor;
        punts= new HashSet<PuntInteres>();
        estacions= new HashMap<String, Estacio>();
        transports= new HashSet<TransportUrba>();
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna el nom del lloc 
     */
    public String obtenirNom(){
        return nom;
    }
    
    /** 
     * @pre: --
     * @post: Retorna les coordenades del lloc 
     */
    public Coordenades obtenirCoordenades(){
        return coords;
    }
    
    /** @pre: --
     *  @post: Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una sortida a l'estació de nom nomEst cap al lloc desti
     *         amb un temps d'origen de "temps"
     */
    public void afegirConnexioSortidaMTI(String nomEst, Lloc desti, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioSortida(desti, temps);
    }
    
    /** @pre: --
     *  @post: Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una arribada a l'estació de nom nomEst des del lloc origen
     *         amb un temps de destí de "temps"
     */
    public void afegirConnexioArribadaMTI(String nomEst, Lloc origen, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioArribada(origen, temps);
    }
    
    /** @pre: Ha d'existir una connexió de Sortida a l'estació del mitjà "mitja"
     *  @post: S'afegeix el mitjà de transport indirecte com a sortida de l'estació corresponent
     */
    public void afegirSortidaMTI(MTIndirecte mitja, LocalDateTime horaSortida){
        Estacio actual= estacions.get(mitja.getNom());
        actual.afegirSortida(mitja, horaSortida);
    }
    
    /** 
     * @pre: --
     * @post: Afegeix el punt d'interès "pI" als punts d'interès del lloc 
     */
    public void afegirPuntInteres(PuntInteres pI){
        punts.add(pI);
    }
    
    /** 
     * @pre: --
     * @post: Afegeix el transport urbà al Lloc 
     */
    public void afegirTransportUrba(TransportUrba tU){
        transports.add(tU);
    } 
}