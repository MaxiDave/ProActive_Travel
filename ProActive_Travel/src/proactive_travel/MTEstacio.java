//ProActive_Travel

/**
 * @file MTEstacio.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @warning --
 * @brief Classe MTEstacio: Conté informació d'un MTEstacio
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un MTEstacio, un MitjaTransport amb un origen (PuntInteres) i un destí (Estacio). Hereda de MitjaTransport
 */
public class MTEstacio extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntInteres origen;                                   ///< @brief Conté el PuntInteres d'origen
    private final Estacio desti;                                        ///< @brief Conté el Estacio de destí
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un MTEstacio amb la informació del MitjaTransport, origen i destí
     * @brief Constructor
     */
    public MTEstacio(PuntInteres origen, Estacio desti, Double preu, Integer durada) {
        super("Trasllat a l'Estació de "+desti, preu, durada);
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Retorna el PuntInteres d’origen del MTEstacio
     * @brief Retorna el PuntInteres d’origen del MTEstacio
     */
    public PuntInteres getOrigen(){
        return origen;
    }
    
    /**
     * @pre --
     * @post Retorna el Estacio de destí del MTEstacio
     * @brief Retorna el Estacio de destí del MTEstacio
     */
    public Estacio getDesti(){
        return desti;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un MTEstacio i és igual al MTEstacio this
     * @brief Retorna cert si l'Objecte o és un MTEstacio i és igual al MTEstacio this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof MTEstacio){
            MTEstacio mD= (MTEstacio)o;
            return obtNom().equals(mD.obtNom());
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
        hash = 67 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}
