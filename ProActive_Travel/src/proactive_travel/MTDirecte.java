//ProActive_Travel

/**
 * @file MTDirecte.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @warning --
 * @brief Classe MTDirecte: Conté informació d'un MTDirecte
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un MTDirecte, un MitjaTransport amb un origen (PuntInteres) i un destí (PuntInteres). Hereda de MitjaTransport
 */
public class MTDirecte extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntInteres origen;                           ///< @brief Conté el PuntInteres d'origen
    private final PuntInteres desti;                            ///< @brief Conté el PuntInteres de destí
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un MTDirecte amb la informació del MitjaTransport, origen i destí
     * @brief Constructor
     */
    public MTDirecte(String nom, PuntInteres origen, PuntInteres desti, Double preu, Integer durada) {
        super(nom, preu, durada);
        this.origen= origen;
        this.desti= desti;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Retorna el PuntInteres d’origen del MTDirecte
     * @brief Retorna el PuntInteres d’origen del MTDirecte
     */
    public PuntInteres getOrigen(){
        return origen;
    }
    
    /**
     * @pre --
     * @post Retorna el PuntInteres de destí del MTDirecte
     * @brief Retorna el PuntInteres de destí del MTDirecte
     */
    public PuntInteres getDesti(){
        return desti;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un MTDirecte i és igual al MTDirecte this
     * @brief Retorna cert si l'Objecte o és un MTYDirecte i és igual al MTDirecte this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof MTDirecte){
            MTDirecte mD= (MTDirecte)o;
            return origen.obtenirLloc().equals(desti.obtenirLloc()) && origen.obtenirLloc().equals(mD.origen.obtenirLloc()) && desti.obtenirLloc().equals(mD.desti.obtenirLloc());
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
        hash = 19 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}