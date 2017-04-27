package proactive_travel;

import java.util.*;

//@MaxiDave: Implementat

public class Allotjament extends PuntInteres {
    /**
     * DESCRIPCIÓ GENERAL
     * Representa un allotjament, amb la seva categoria.
     */
    
    //ATRIBUTS
    private final String categoria;
    private final Double cost;
    private final Coordenades c;
    
    /** @pre: 1<= categoria <= 5
        @post: Es crea un allotjament a partir de les dades del punt d’interès i la categoria del allotjament
    */
    public Allotjament(String nom, Collection<String> acts, Double costH,String catego, Coordenades coor) {
        super(nom, acts, costH);
        categoria= catego;
        cost=costH;
        c=coor;
    }
    
    /** @pre: --
     *  @post: Retorna la categoria de l’allotjament
     */
    public String obtenirCat(){
        return categoria;
    }
}
