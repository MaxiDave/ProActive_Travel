package proactive_travel;

import java.util.*;
import java.time.*;

/**
 *
 * @author roger i david
 */

public class Viatge {
    
    /** @pre: --
     *  @post: Es crea un conjunt de clients amb tants clients com té “clients”, categoria desitjada i punts d’interès a visitar
     */
    public Viatge(Collection<Client> clients, Integer catDesit, Collection<PuntInteres> pl,Lloc origen,Lloc desti, LocalDateTime inici,Double duracioMax){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna un enter que representa el nombre de clients del conjunt que tenen la preferència “pref” entre les seves preferències personals
     */
    public Integer obtenirSatisfaccioPreferencia(String pref){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna cert si l’allotjament “hotel” és de la categoria que desitja el conjunt de clients
     */
    public Boolean categoriaDesitjada(Allotjament hotel){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna el lloc d’origen del viatge del grup de clients
     */
    public Lloc obtenirOrigen(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna el lloc de destí del viatge del grup de clients
     */
    public Lloc obtenirDesti(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna la data (amb hora inclosa) de sortida del grup de clients
     */
    public LocalDateTime obtenirInici(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
     *  @post: Retorna un iterador als punts d’interès prefixats que s’han de visitar sí o sí
     */
    Iterator<PuntInteres> obtenirInteressos(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
