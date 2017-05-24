//ProActive_Travel

/**
 * @file MitjaTransport.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @warning --
 * @brief Classe MitjaTransport: Conté informació d'un Mitjà de Transport
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Comparator;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un Mitjà de Transport, amb un nom, preu i durada
 */
public class MitjaTransport implements Comparable<MitjaTransport>{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    protected final String nom;                         ///< @brief Conté el nom del MitjaTransport
    protected final Double preu;                        ///< @brief Conté el preu del MitjaTransport
    protected final Integer durada;                     ///< @brief Conté la durada del MitjaTransport
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un mitjà de transport amb nom, preu i durada
     * @brief Constructor
     */
    public MitjaTransport(String nom, Double preu, Integer durada){
        this.nom= nom;
        this.preu= preu;
        this.durada= durada;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Retorna el nom del transport
     * @brief Retorna el nom del transport
     */
    public String obtNom(){
        return nom;
    }
    
    /**
     * @pre --
     * @post Retorna el preu del transport
     * @brief Retorna el preu del transport
     */
    public Double obtPreu(){
        return preu;
    }
    
    /**
     * @pre --
     * @post Retorna la durada del transport
     * @biref Retorna la durada del transport
     */
    public Integer obtDurada(){
        return durada;
    }
    
    /**
     * DESCRIPCIÓ GENERAL
     * @biref Comparador de MitjaTransport per preu
     */
    public static Comparator COMPARA_PER_PREU = new Comparator<MitjaTransport>() {
        /**
        * @pre --
        * @post Retorna negatiu si a<b, positiu si b<a, i zero si a==b
        * @brief Retorna negatiu si a<b, positiu si b<a, i zero si a==b
        */
        public int compare(MitjaTransport a, MitjaTransport b) {
            if(a.preu<b.preu) return -1;
            else if(a.preu>b.preu) return 1;
            else if(a.durada<b.durada) return -1;
            else if(a.durada>b.durada) return 1;
            else return 0;
        }
    };
    
    /**
     * DESCRIPCIÓ GENERAL
     * @biref Comparador de MitjaTransport per durada
     */
    public static Comparator COMPARA_PER_DURADA = new Comparator<MitjaTransport>() {
        /**
        * @pre --
        * @post Retorna negatiu si a<b, positiu si b<a, i zero si a==b
        * @brief Retorna negatiu si a<b, positiu si b<a, i zero si a==b
        */
        public int compare(MitjaTransport a, MitjaTransport b) {
            if(a.durada<b.durada) return -1;
            else if(a.durada>b.durada) return 1;
            else if(a.preu<b.preu) return -1;
            else if(a.preu>b.preu) return 1;
            else return 0;
        }
    };
    
    /**
     * @pre --
     * @post Sobreescriptura del mètode compareTo
     * @biref Sobreescriptura del mètode compareTo
     */
    @Override
    public int compareTo(MitjaTransport o) {
        Double valor= preu-o.preu;
        if(valor == 0) return durada-o.durada;
        else if(valor > 0) return 1;
        else return -1;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un MitjaTransport i és igual al MitjaTransport this
     * @brief Retorna cert si l'Objecte o és un MitjaTransport i és igual al MitjaTransport this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof MitjaTransport){
            MitjaTransport m= (MitjaTransport)o;
            return nom.equals(m.nom);
        }
        else return false;
    }

    /** 
     * @pre --
     * @post Sobreescriptura del hashCode generada automàticament
     * @brief Sobreescriptura del hashCode generada automàticament
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.nom);
        return hash;
    }
}