//ProActive_Travel

/**
 * @file: Allotjament.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Allotjament: Conté informació d'un punt on es pot passar una nit
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Estada en un Hotel, amb l'inici de la estada i el final.
 */
public class EstadaHotel {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una EstadaHotel a partir d’un Allotjament i una hora d’inici i de fi 
     */
    public EstadaHotel(Allotjament hotel, LocalDateTime inici, LocalDateTime fi){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    //MÈTODES CONSULTORS-------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna l’hora d’inici de l'Estada 
     */
    public LocalDateTime getInici(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    /** 
     * @pre: --
     * @post: Retorna l’hora de fi de l'Estada 
     */
    public LocalDateTime getFi(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}