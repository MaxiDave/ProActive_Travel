/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.Collection;

/**
 *
 * @author roger
 */
public class Allotjament extends PuntInteres {
    
    public Allotjament(String nom, Collection<String> acts, Double cost, Integer categoria) {
        super(nom, acts, cost);
    }
    
    /** @pre: --
     *  @post: Retorna la categoria de l’allotjament
     * @return 
     */
    public Integer obtenirCat(){
        
    }
}
