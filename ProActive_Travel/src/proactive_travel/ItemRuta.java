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

import java.time.LocalDateTime;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Item d'una Ruta.
 */
public interface ItemRuta {
    /** 
     * @pre: --
     * @post: Retorna el punt d'interes de sortida del Item 
     */
    public PuntInteres obtSortida();
    
    /** 
     * @pre: --
     * @post: Retorna l’hora d'inici de l'item
     */
    public LocalDateTime getInici();
    
    /** 
     * @pre: --
     * @post: Retorna l’hora de fi de l'item 
     */
    public LocalDateTime getFinal();
    
    /** 
     * @pre: --
     * @post: Retorna la durada de l'item
     */
    public Integer obtDurada();
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per l'item
     */
    public Integer obtSatisfaccio();
    
    /** 
     * @pre: --
     * @post: Retorna el cost per persona del item
     */
    public Double obtCost();
}