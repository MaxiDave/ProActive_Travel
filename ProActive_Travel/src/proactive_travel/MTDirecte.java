package proactive_travel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roger
 */
public class MTDirecte extends MitjaTransport{
    //ATRIBUTS
    private final PuntInteres origen;
    private final PuntInteres desti;
    
    /**
     * @pre: --
     * @post: Crea un mitjà de transport amb nom, origen, destí, preu, durada i descriptor
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
     * @pre: -
     * @post: Retorna el Punt d’interès de destí del transport
     */
    public PuntInteres getDesti(){
        return desti;
    }
}
