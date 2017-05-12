//ProActive_Travel

/**
 * @file: Visita.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Visita: Conté informació d'un punt que es pot visitar
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Visita a un punt visitable, amb l'instant d'entrada i de sortida
 */
public class Visita implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una Visita a partir d’un PuntVisitable, una hora d’entrada i de sortida, i un grau de satisfacció 
     */
    public Visita(PuntVisitable pV, LocalDateTime entrada, LocalDateTime sortida, Integer satisfaccio){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna l’hora d’entrada de la Visita 
     */
    public LocalDateTime getInici(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    /** 
     * @pre: --
     * @post: Retorna l’hora de sortida de la Visita 
     */
    public LocalDateTime getFinal(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el punt d'interes de sortida de la visita, en aquest cas el propi punt visitable 
     */
    public PuntInteres obtSortida(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la durada de la visita
     */
    public Integer obtDurada(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per la visita
     */
    public Integer obtSatisfaccio(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el cost per persona de la visita
     */
    public Double obtCost(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
