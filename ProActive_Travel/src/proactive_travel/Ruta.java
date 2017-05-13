//ProActive_Travel

/**
 * @file: Ruta.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Ruta: Conté informació d'una Ruta amb els seus ItemRuta
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Ruta
 */
public class Ruta {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    Deque<ItemRuta> items;
    private Integer durada;
    private Integer satisfaccio;
    private Double cost;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea una Ruta inicial buida
     */
    public Ruta(){
        items= new ArrayDeque<ItemRuta>();
        durada= 0;
        satisfaccio= 0;
        cost= (double)0;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Afegeix un ItemRuta a la Ruta
     */
    public void afegeixItemRuta(ItemRuta item){
        items.addLast(item);
        durada+= item.obtDurada();
        satisfaccio+= item.obtSatisfaccio();
        cost+= item.obtCost();
    }
    
    public void treureUltimItem(){
        ItemRuta item= items.pollLast();
        durada-= item.obtDurada();
        satisfaccio-= item.obtSatisfaccio();
        cost-= item.obtCost();
    }
    
    public PuntInteres obtDesti(){
        return items.getLast().obtSortida();
    }
    
    public Integer obtDurada(){
        return durada; 
    }
    
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    public Double obtCost(){
        return cost;
    }
}
