//ProActive_Travel

/**
 * @file: Ruta.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Ruta: Conté informació d'una Ruta amb els seus ItemRuta
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Ruta
 */
public class Ruta {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea una Ruta inicial buida
     */
    public Ruta(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Afegeix un ItemRuta a la Ruta
     */
    public void afegeixItemRuta(ItemRuta item){
        throw new UnsupportedOperationException("Not supported yet"); 
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
