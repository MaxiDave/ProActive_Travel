//ProActive_Travel

/**
 * @file: FranjaHoraria.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe FranjaHoraria: Conté informació d'una franja horària entre dos hores d'un dia
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Franja Horària, entre dues hores d'un dia
 */
public class FranjaHoraria {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final LocalTime obertura;
    private final LocalTime tancament;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Crea una franja horària a partir d’un LocalTime inici i final 
     */
    public FranjaHoraria(LocalTime inici, LocalTime fi){
        obertura= inici;
        tancament= fi;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna cert si el LocalTime “instant” es troba dins de l’horari de la franja 
     */
    public Boolean pertanyFranja(LocalTime instant){
        return instant.isAfter(obertura) && instant.isBefore(tancament);
    }
}
