//ProActive_Travel

/**
 * @file: MitjaTransport.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe MitjaTransport: Conté informació d'un Mitjà de Transport
 * @copyright: Public License
 */

package proactive_travel;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Mitjà de Transport, amb un nom, preu i durada
 */
public class MitjaTransport implements Comparable<MitjaTransport>{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;
    private final Double preu;
    private final Integer durada;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: Origen i destí han de ser llocs o punts d’interès
     * @post: Crea un mitjà de transport amb preu, durada i descriptor
     */
    public MitjaTransport(String descriptor, Double cost, Integer dur){
        nom= descriptor;
        preu= cost;
        durada= dur;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Retorna el nom del transport
     */
    public String getNom(){
        return nom;
    }
    
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

    @Override
    public int compareTo(MitjaTransport o) {
        Double valor= preu-o.preu;
        if(valor == 0) return durada-o.durada;
        else if(valor > 0) return 1;
        else return -1;
    }
}