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
 * @brief: Representa un Viatge, amb uns clients, categoria desitjada, punts d'interès a visitar, origen, destí, instant d'inici i duració màxima.
 */
public class Viatge {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un conjunt de clients amb tants clients com té “clients”, categoria desitjada i punts d’interès a visitar
     */
    public Viatge(Collection<Client> clients, Integer catDesit, Collection<PuntInteres> pl,Lloc origen,Lloc desti, LocalDateTime inici,Double duracioMax){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
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
