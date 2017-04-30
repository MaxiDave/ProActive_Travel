//ProActive_Travel

/**
 * @file: MTDirecte.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe MTDirecte: Conté informació d'un Mitjà de transport directe
 * @copyright: Public License
 */

package proactive_travel;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un MTDirecte, un MitjaTransport amb un origen i un destí (Punts d'interès)
 */
public class MTDirecte extends MitjaTransport{
    //ATRIBUTS
    private final PuntInteres origen;
    private final PuntInteres desti;
    
    /**
     * @pre: --
     * @post: Crea un MTDirecte amb nom, origen, destí, preu, durada i descriptor
     */
    public MTDirecte(String descriptor, PuntInteres o, PuntInteres d, Double preu, Integer durada) {
        super(descriptor, preu, durada);
        origen= o;
        desti= d;
    }
    
    /**
     * @pre: --
     * @post: Retorna el Punt d’interès d’origen del transport
     */
    public PuntInteres getOrigen(){
        return origen;
    }
    
    /**
     * @pre: --
     * @post: Retorna el Punt d’interès de destí del transport
     */
    public PuntInteres getDesti(){
        return desti;
    }
}