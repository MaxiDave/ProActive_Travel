/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.Objects;

/**
 *
 * @author David
 */
public class MTEstacio extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntInteres origen;
    private final Estacio desti;
    
    public MTEstacio(PuntInteres origen, Estacio desti, Double preu, Integer durada) {
        super("Trasllat a l'Estació de "+desti, preu, durada);
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
    public Estacio getDesti(){
        return desti;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof MTEstacio){
            MTEstacio mD= (MTEstacio)o;
            return getNom().equals(mD.getNom());
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}
