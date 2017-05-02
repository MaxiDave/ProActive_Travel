//ProActive_Travel

/**
 * @file: Allotjament.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Allotjament: Conté informació d'un punt on es pot passar una nit
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un allotjament, amb una categoria de luxe.
 */
public class Allotjament extends PuntInteres {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String categoria;
    private final Double cost;
    private final Coordenades localitzacio;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un allotjament a partir de les dades del punt d’interès, la categoria del allotjament, i coordenades on es troba
     */
    public Allotjament(String nom, Collection<String> acts, Double costH, String catego, Coordenades coor) {
        super(nom, acts, costH);
        categoria= catego;
        cost= costH;
        localitzacio= coor;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna la categoria de l’allotjament
     */
    public String obtenirCat(){
        return categoria;
    }
}