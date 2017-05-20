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

import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un MTDirecte, un MitjaTransport amb un origen i un destí (Punts d'interès)
 */
public class MTDirecte extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntInteres origen;
    private final PuntInteres desti;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea un MTDirecte amb un mitjà de transport i uns PuntInteres d'origen i destí
     */
    public MTDirecte(String nom, PuntInteres origen, PuntInteres desti, Double preu, Integer durada) {
        super(nom, preu, durada);
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
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
    
    @Override
    public boolean equals(Object o){
        if(o instanceof MTDirecte){
            MTDirecte mD= (MTDirecte)o;
            return getNom().equals(mD.getNom());
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}