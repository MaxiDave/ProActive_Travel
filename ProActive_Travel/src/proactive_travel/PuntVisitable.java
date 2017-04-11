/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;
/**
 *
 * @author roger
 */
public class PuntVisitable extends PuntInteres {
    
    /** @pre: --
     *  @post: Es crea un punt visitable a partir de la informació del punt d’interès i el temps mitjà de visita
     * @param nom
     * @param acts
     * @param cost
     * @param tempsVisita 
     */
    public PuntVisitable(String nom,Collection<String> acts,Double cost,Integer tempsVisita){
        super(nom, acts, cost);
    }
    
    /** @pre: --
     *  @post: S’ha afegit la franja horària “fh” al punt visitable
     * @param fh 
     */
    public void afegirFranja(FranjaHoraria fh){
        
    }
    
    /** @pre: --
     *  @post: Retorna el temps mitjà de visita
     * @return 
     */
    public Double obtenirTempsVisita(){
        
    }
    
    /** @pre: --
     *  @post: Retorna cert si el punt visitable està obert en un DiaHora determinat
     * @param inst
     * @return 
     */
    public Boolean estaObert(Hora inst){
        
    }
}
