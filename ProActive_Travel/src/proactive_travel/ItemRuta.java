//ProActive_Travel

/**
 * @file ItemRuta.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe ItemRuta: Conté informació d'un Ítem d'una Ruta
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.LocalDateTime;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un Item d'una Ruta.
 */
public interface ItemRuta{
    /** 
     * @pre --
     * @post Retorna el PuntRuta de sortida del ItemRuta 
     * @brief Retorna el PuntRuta de sortida del ItemRuta 
     */
    public PuntRuta obtPuntSortida();
    
    /** 
     * @pre --
     * @post Retorna l’hora d'inici de l'ItemRuta
     * @brief Retorna l’hora d'inici de l'ItemRuta
     */
    public LocalDateTime obtInici();
    
    /** 
     * @pre --
     * @post Retorna l’hora de final de l'ItemRuta
     * @brief Retorna l’hora de final de l'ItemRuta
     */
    public LocalDateTime obtFinal();
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per l'ItemRuta
     * @brief Retorna la satisfacció produïda per l'ItemRuta
     */
    public Integer obtSatisfaccio();
    
    /** 
     * @pre --
     * @post Retorna el cost del ItemRuta
     * @brief Retorna el cost del ItemRuta
     */
    public Double obtCost();
    
    /** 
     * @pre --
     * @post Retorna la durada del ItemRuta
     * @brief Retorna la durada del ItemRuta
     */
    public Integer obtDurada();
}