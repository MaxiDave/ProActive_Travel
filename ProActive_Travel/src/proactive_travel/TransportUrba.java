/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

/**
 *
 * @author maxidave
 */
public class TransportUrba {
    
    private final String nom;
    private final Integer durada;
    private final Double preu;
    
    /** @pre: --
        @post: Crea un TransportUrba amb un descriptor, durada i preu */
    TransportUrba(String descriptor, Integer dur, Double cost){
        nom= descriptor;
        durada= dur;
        preu= cost;
    }
    
    /** @pre: --
        @post: Retorna el preu del transport */
    Double getPreu(){
        return preu;
    }
    
     /** @pre: --
         @post: Retorna la durada del transport */
    Integer getDurada(){
        return durada;
    }
    
     /** @pre: --
         @post: Retorna el descriptor del transport */
    String getDescriptor(){
        return nom;
    }
}
