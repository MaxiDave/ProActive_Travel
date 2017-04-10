/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

/**
 *
 * @author Roger
 */
public class MTIndirecte extends MitjaTransport{
    
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
    public MTIndirecte(String descriptor, Object o, Object d, Double preu, Double durada, Double dist) {
        super(descriptor, o, d, preu, durada, dist);
    }
    
    /**
     * @pre: --
     * @post: Retorna la estació a la que està vinculada el mitjà de transport indirecte
     * @return 
     */
    public Estacio getEstacio(){
        
    }
}
