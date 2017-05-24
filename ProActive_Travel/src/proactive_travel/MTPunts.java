//ProActive_Travel

/**
 * @file MTPunts.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @warning --
 * @brief Classe MTPunts: Conté informació d'un MTPunts
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un MTPunts, un MitjaTransport amb un origen (Estacio) i un destí (PuntInteres). Hereda de MitjaTransport
 */
public class MTPunts extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Estacio origen;                                   ///< @brief Conté el Estacio d'origen
    private final PuntInteres desti;                                ///< @brief Conté el PuntInteres de destí
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un MTPunts amb la informació del MitjaTransport, origen i destí
     * @brief Constructor
     */
    public MTPunts(Estacio origen, PuntInteres desti, Double preu, Integer durada) {
        super("Trasllat de l'Estació de "+origen, preu, durada);
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Retorna l'Estacio d’origen del MTPunts
     * @brief Retorna l'Estacio d’origen del MTPunts
     */
    public Estacio getOrigen(){
        return origen;
    }
    
    /**
     * @pre --
     * @post Retorna el PuntInteres de destí del MTPunts
     * @brief Retorna el PuntInteres de destí del MTPunts
     */
    public PuntInteres getDesti(){
        return desti;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un MTPunts i és igual al MTPunts this
     * @brief Retorna cert si l'Objecte o és un MTPunts i és igual al MTPunts this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof MTPunts){
            MTPunts mP= (MTPunts)o;
            return obtNom().equals(mP.obtNom());
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
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}
