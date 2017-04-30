package proactive_travel;

import java.util.*;

/**
 *
 * @author Roger
 */

public class Ruta {
    
    /**
     * @pre: --
     * @post: Crea una Ruta inicial buida
     */
    public Ruta(){
        
    }
    
    /**
     * @pre: --
     * @post: Afegeix un ItemRuta a la Ruta
     */
    public void afegeixItemRuta(ItemRuta item){
        
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si el Trajecte absolut de la ruta té “origen” com origen i “desti” com destí, i també opcionalment passa per tots els “punts”. És a dir, si la Ruta està completada.
     */
    public Boolean esCompleta(PuntInteres o, PuntInteres d, Collection<PuntInteres> punts){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si la Ruta es buida, fals altrament
     */
    public Boolean isEmpty(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si la Ruta passa per el punt d'interès "pI"
     */
    public Boolean passaPer(PuntInteres pI){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
