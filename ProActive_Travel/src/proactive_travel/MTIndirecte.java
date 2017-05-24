//ProActive_Travel

/**
 * @file MTIndirecte.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @warning --
 * @brief Classe MTIndirecte: Conté informació d'un MTIndirecte
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un MTIndirecte, un MitjaTransport amb un origen (Estacio) i un destí (Estacio). Hereda de MitjaTransport
 */
public class MTIndirecte extends MitjaTransport{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Estacio origen;                                           ///< @brief Conté el Estacio d'origen
    private final Estacio desti;                                            ///< @brief Conté el Estacio de destí
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un MTIndirecte amb la informació del MitjaTransport, origen i destí
     * @brief Constructor
     */
    public MTIndirecte(String descriptor, Estacio o, Estacio d, Double preu, Integer durada) {
        super(descriptor, preu, durada);
        origen= o;
        desti= d;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Retorna el Estacio d’origen del MTIndirecte
     * @brief Retorna el Estacio d’origen del MTIndirecte
     */
    public Estacio getOrigen(){
        return origen;
    }
    
    /**
     * @pre --
     * @post Retorna el Estacio de destí del MTIndirecte
     * @brief Retorna el Estacio de destí del MTIndirecte
     */
    public Estacio getDesti(){
        return desti;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un MTIndirecte i és igual al MTIndirecte this
     * @brief Retorna cert si l'Objecte o és un MTIndirecte i és igual al MTIndirecte this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof MTIndirecte){
            MTIndirecte mI= (MTIndirecte)o;
            return obtNom().equals(mI.obtNom()) && (obtPreu()==mI.obtPreu()) && (obtDurada()==mI.obtDurada() && origen.equals(mI.origen) && desti.equals(mI.desti));
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
        hash = 53 * hash + Objects.hashCode(this.origen);
        return hash;
    }
}