//ProActive_Travel

/**
 * @file: PuntInteres.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe PuntInteres: Conté informació d'un Punt d'Interès amb les seves característiques
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL 
 * @brief: Representa un punt on els clients tenen interès, bé sigui per visitar o bé per allotjar-se. 
 */
public class PuntInteres {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;
    private final Set<String> activitats;
    private final Double preu;
    private Lloc associat;
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un punt d’interès de nom “name” amb les activitats que ofereix “acts” i el preu  “cost”.
     */
    public PuntInteres(String name,Collection<String> acts,Double cost){
        nom= name;
        activitats= new HashSet<String>(acts);
        preu= cost;
        associat= null;
    }
    
    /** 
     * @pre: --
     * @post: Retorna cert si el punt d’interès satisfà la preferència “pref”, fals altrament 
     */
    public Boolean satisfaPreferencia(String pref){
        return activitats.contains(pref);
    }
    
    /** 
     * @pre: --
     * @post: Retorna el nom del punt d’interès
     */
    public String obtenirNom(){
        return nom;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el lloc on està vinculat el punt d’interès
     */
    public Lloc obtenirLloc(){
        return associat;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el cost. Si és gratis, retorna 0
     */
    public Double obtenirPreu(){
        return preu;
    }
    
    /** 
     * @pre: --
     * @post: El lloc "ll" passarà a ser el lloc on es vincula aquest Punt d'Interès
     */
    public void vincularLloc(Lloc ll){
        associat= ll;
    }
}
