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
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un MTIndirecte, un MitjaTransport amb un origen i un destí (Llocs)
 */
public class MTIndirecte extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Estacio origen;
    private final Estacio desti;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea un mitjà de transport amb origen, destí, preu, durada i descriptor
     */
    public MTIndirecte(String descriptor, Estacio o, Estacio d, Double preu, Integer durada) {
        super(descriptor, preu, durada);
        origen= o;
        desti= d;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Retorna el lloc d’origen del transport
     */
    public Estacio getOrigen(){
        return origen;
    }
    
    /**
     * @pre: --
     * @post: Retorna el lloc de destí del transport
     */
    public Estacio getDesti(){
        return desti;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof MTIndirecte){
            MTIndirecte mI= (MTIndirecte)o;
            return getNom().equals(mI.getNom()) && (getPreu()==mI.getPreu()) && (getDurada()==mI.getDurada() && origen.equals(mI.origen) && desti.equals(mI.desti));
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}