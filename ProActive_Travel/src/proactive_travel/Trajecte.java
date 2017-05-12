//ProActive_Travel

/**
 * @file: Trajecte.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Trajecte: Conté informació d'un Trajecte dut a terme per un mitjà de transport
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Trajecte, amb el mitjà de transport i les hores d'arribada i sortida
 */
public class Trajecte implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Trajecte amb el transport i les hores de sortida i arribada 
     */
    public Trajecte(MitjaTransport mT, LocalTime sortida, LocalTime arribada){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres de destí 
     */
    public PuntInteres obtSortida(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la durada del trajecte en minuts
     */
    public Integer obtDurada(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per el trajecte, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el preu del trajecte 
     */
    public Double obtCost(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna l'hora de sortida del Trajecte 
     */
    public LocalDateTime getInici(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna l'hora d'arribada del Trajecte 
     */
    public LocalDateTime getFinal(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
