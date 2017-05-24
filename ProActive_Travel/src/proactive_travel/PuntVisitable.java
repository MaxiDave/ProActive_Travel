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
    private final LocalTime obertura;
    private final LocalTime tancament;
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un punt visitable a partir de la informació del punt d’interès i el temps mitjà de visita
     */
    public PuntVisitable(String name,Collection<String> acts,Double cost,Integer tempsVisita, LocalTime obertura, LocalTime tancament, Coordenades coor){
        super(name, acts, cost, coor);
        tempsV= tempsVisita;
        this.obertura= obertura;
        this.tancament= tancament;
    }
    
    /** 
     * @pre --
     * @post Retorna el temps mitjà de visita
     */
    public Integer obtTempsVisita(){
        return tempsV;
    }
    
    /** 
     * @pre --
     * @post Retorna el moment d'obertura del punt visitable
     */
    public LocalTime obtObertura(){
        return obertura;
    }
    
    /** 
     * @pre --
     * @post Retorna el moment de tancament del punt visitable
     */
    public LocalTime obtTancament(){
        return tancament;
    }
    
    public boolean estaObert(LocalTime act){
        boolean obert;
        if(obertura.compareTo(act)<0 && tancament.compareTo(act)>0){
            obert=true;
        }
        else{
            obert=false;
        }
        return obert;
    }
}
