//ProActive_Travel

/**
 * @file Allotjament.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Allotjament: Conté informació d'un Punt on s'hi pot allotjar i proporcionar satisfacció
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Collection;

/**
 * DESCRIPCIÓ GENERAL
 * @brief   Punt on s'hi pot allotjar, tot proporcionant satisfacció
 * @details Representa un allotjament, una extensió d'un PuntInteres (Herència) amb una categoria que representa el grau de luxe.
 */
public class Allotjament extends PuntInteres {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String categoria; ///< @brief Variable final que emmagatzema la categoria de l'allotjament
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un allotjament a partir de les dades del punt d’interès (nom, activitats, cost), la categoria del allotjament, i les coordenades d'on es troba
     * @brief Constructor
     */
    public Allotjament(String nom, Collection<String> acts, Double costH, String catego, Coordenades coor) {
        super(nom, acts, costH, coor);
        categoria= catego;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna la categoria de l’allotjament
     * @brief De quina categoria és l'allotjament?
     */
    public String obtenirCat(){
        return categoria;
    }
}