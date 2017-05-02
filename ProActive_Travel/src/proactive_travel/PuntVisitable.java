//ProActive_Travel

/**
 * @file: PuntVisitable.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe PuntVisitable: Conté informació d'un punt visitable amb la seva informació adicional.
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL 
 * @brief: Representa un punt visitable, amb les seves franges horàries, coordenades i temps de visita.
 */
public class PuntVisitable extends PuntInteres {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Integer tempsV;
    private final FranjaHoraria obertura;
    private final Coordenades coords;
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un punt visitable a partir de la informació del punt d’interès i el temps mitjà de visita
     */
    public PuntVisitable(String name,Collection<String> acts,Double cost,Integer tempsVisita, FranjaHoraria franja, Coordenades coor){
        super(name, acts, cost);
        tempsV= tempsVisita;
        obertura= franja;
        coords=coor;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el temps mitjà de visita
     */
    public Integer obtenirTempsVisita(){
        return tempsV;
    }
    
    /** 
     * @pre: --
     * @post: Retorna cert si el punt visitable està obert en un DiaHora determinat, fals altrament
     */
    public Boolean estaObert(LocalTime inst){
        return obertura.pertanyFranja(inst);
    }
}
