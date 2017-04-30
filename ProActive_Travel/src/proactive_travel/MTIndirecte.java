//ProActive_Travel

/**
 * @file: MTIndirecte.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe MTIndirecte: Conté informació d'un Mitjà de transport indirecte
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un MTIndirecte, un MitjaTransport amb un origen i un destí (Llocs)
 */
public class MTIndirecte extends MitjaTransport{
    //ATRIBUTS
    private final Lloc origen;
    private final Lloc desti;
    
    /**
     * @pre: --
     * @post: Crea un mitjà de transport amb origen, destí, preu, durada i descriptor
     */
    public MTIndirecte(String descriptor, Lloc o, Lloc d, Double preu, Integer durada) {
        super(descriptor, preu, durada);
        origen= o;
        desti= d;
    }
    
    /**
     * @pre: --
     * @post: Retorna el lloc d’origen del transport
     */
    public Lloc getOrigen(){
        return origen;
    }
    
    /**
     * @pre: --
     * @post: Retorna el lloc de destí del transport
     */
    public Lloc getDesti(){
        return desti;
    }
}