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
public class MTPunts extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Estacio origen;
    private final PuntInteres desti;
    
    public MTPunts(Estacio origen, PuntInteres desti, Double preu, Integer durada) {
        super("Trasllat de l'Estació de "+origen, preu, durada);
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Retorna el Punt d’interès d’origen del transport
     */
    public Estacio getOrigen(){
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
    public String toString(){
        return origen+" "+desti;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof MTPunts){
            MTPunts mP= (MTPunts)o;
            return getNom().equals(mP.getNom());
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}
