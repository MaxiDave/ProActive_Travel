package proactive_travel;

import java.util.*;

//@MaxiDave: Implementat

public class Allotjament extends PuntInteres {
    /**
     * DESCRIPCIÓ GENERAL
     * Representa un allotjament, amb la seva categoria.
     */
    
    //ATRIBUTS
    private final Integer categoria;
    
    /** @pre: 1<= categoria <= 5
        @post: Es crea un allotjament a partir de les dades del punt d’interès i la categoria del allotjament
    */
    public Allotjament(String nom, Collection<String> acts, Double cost, Lloc associat,Integer cat) {
        super(nom, acts, cost, associat);
        categoria= cat;
    }
    
    /** @pre: --
     *  @post: Retorna la categoria de l’allotjament
     */
    public Integer obtenirCat(){
        return categoria;
    }
}
