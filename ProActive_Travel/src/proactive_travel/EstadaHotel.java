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
public class EstadaHotel implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una EstadaHotel a partir d’un Allotjament, una hora d’inici i de fi i un grau de satisfacció
     */
    public EstadaHotel(Allotjament hotel, LocalDateTime inici, LocalDateTime fi, Integer satisfaccio){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
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
    public LocalDateTime getFinal(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el punt d'interes de sortida del Item, en aquest cas l'hotel 
     */
    public PuntInteres obtSortida(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la durada de l'estada
     */
    public Integer obtDurada(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per l'estada
     */
    public Integer obtSatisfaccio(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el cost per persona de l'estada
     */
    public Double obtCost(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}