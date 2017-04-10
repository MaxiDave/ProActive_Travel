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
    /**
     * @pre: Origen i destí han de ser llocs o punts d’interès
     * @post: Crea un mitjà de transport amb origen, destí, preu, durada i descriptor
     * @param descriptor
     * @param o
     * @param d
     * @param preu
     * @param durada
     * @param dist 
     */
    public MTDirecte(String descriptor, Object o, Object d, Double preu, Double durada, Double dist) {
        super(descriptor, o, d, preu, durada, dist);
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si el MTDirecte és urbà
     * @return 
     */
    public Boolean esUrba(){
        
    }
}
