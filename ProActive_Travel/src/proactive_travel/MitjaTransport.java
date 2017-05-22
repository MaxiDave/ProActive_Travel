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

import java.util.Comparator;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Mitjà de Transport, amb un nom, preu i durada
 */
public class MitjaTransport implements Comparable<MitjaTransport>{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    protected final String nom;
    protected final Double preu;
    protected final Integer durada;
    
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
    
    public static Comparator COMPARA_PER_PREU = new Comparator<MitjaTransport>() {
        public int compare(MitjaTransport a, MitjaTransport b) {
            if(a.preu<b.preu) return -1;
            else if(a.preu>b.preu) return 1;
            else if(a.durada<b.durada) return -1;
            else if(a.durada>b.durada) return 1;
            else return 0;
        }
    };
    
    public static Comparator COMPARA_PER_DURADA = new Comparator<MitjaTransport>() {
        public int compare(MitjaTransport a, MitjaTransport b) {
            if(a.durada<b.durada) return -1;
            else if(a.durada>b.durada) return 1;
            else if(a.preu<b.preu) return -1;
            else if(a.preu>b.preu) return 1;
            else return 0;
        }
    };

    @Override
    public int compareTo(MitjaTransport o) {
        Double valor= preu-o.preu;
        if(valor == 0) return durada-o.durada;
        else if(valor > 0) return 1;
        else return -1;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof MitjaTransport){
            MitjaTransport m= (MitjaTransport)o;
            return nom.equals(m.nom);
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.nom);
        return hash;
    }
}