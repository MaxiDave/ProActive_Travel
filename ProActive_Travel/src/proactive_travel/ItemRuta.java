//ProActive_Travel

/**
 * @file: ItemRuta.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe ItemRuta: Conté informació d'un Ítem d'una Ruta
 * @copyright: Public License
 */

package proactive_travel;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Item d'una Ruta.
 */
public class ItemRuta {
    
    /** 
     * @pre: --
     * @post: Crea un ItemRuta inicial buit 
     */
    public ItemRuta(){
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /** 
     * @pre: ItemRuta buit
     * @post: ItemRuta serà un trajecte 
     */
    public void afegirTrajecte(Trajecte traj){
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /** 
     * @pre: ItemRuta buit
     * @post: ItemRuta serà una Estada 
     */
    void afegirEstada(EstadaHotel estada){
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /** 
     * @pre: ItemRuta buit
     * @post: ItemRuta serà una Visita 
     */
    void afegirVisita(Visita v){
        throw new UnsupportedOperationException("Not supported yet");
    }
}