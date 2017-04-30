package proactive_travel;

import java.time.*;

/**
 *
 * @author maxidave
 */

public class Visita {
    /** @pre: --
        @post: Es crea una Visita a partir d’un PuntVisitable i una hora d’entrada i de sortida 
    */
    Visita(PuntVisitable pV, LocalDateTime entrada, LocalDateTime sortida){
        
    }

    /** @pre: --
        @post: Retorna l’hora d’entrada de la Visita 
    */
    LocalDateTime getEntrada(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }

    /** @pre: --
        @post: Retorna l’hora de sortida de la Visita 
    */
    LocalDateTime getSortida(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
