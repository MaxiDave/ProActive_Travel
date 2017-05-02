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
public class Visita {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una Visita a partir d’un PuntVisitable i una hora d’entrada i de sortida 
     */
    public Visita(PuntVisitable pV, LocalDateTime entrada, LocalDateTime sortida){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna l’hora d’entrada de la Visita 
     */
    public LocalDateTime getEntrada(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    /** 
     * @pre: --
     * @post: Retorna l’hora de sortida de la Visita 
     */
    public LocalDateTime getSortida(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
