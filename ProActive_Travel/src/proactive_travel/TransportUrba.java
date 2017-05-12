//ProActive_Travel

/**
 * @file: TransportUrba.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe TransportUrba: Conté informació d'un transport urbà, que es troben internament als llocs.
 * @copyright: Public License
 */

package proactive_travel;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un transport urbà, amb nom, durada i preu.
 */
public class TransportUrba{    
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;
    private final Integer durada;
    private final Double preu;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Crea un TransportUrba amb un descriptor, durada i preu 
     */
    public TransportUrba(String descriptor, Integer dur, Double cost){
        nom= descriptor;
        durada= dur;
        preu= cost;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna el preu del transport 
     */
    public Double getPreu(){
        return preu;
    }
    
    /** 
     * @pre: --
     * @post: Retorna la durada del transport 
     */
    public Integer getDurada(){
        return durada;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el descriptor del transport 
     */
    public String getNom(){
        return nom;
    }
}