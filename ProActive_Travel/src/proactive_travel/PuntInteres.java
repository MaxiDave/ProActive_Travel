package proactive_travel;

import java.util.*;

//@MaxiDave: Implementat

public class PuntInteres {
    /**
     * DESCRIPCIÓ GENERAL 
     * Representa un punt on els clients tenen interès, 
     * bé sigui per visitar o bé per allotjar-se. 
    */
    
    //ATRIBUTS
    private final String nom;
    private final Set<String> activitats;
    private final Double preu;
    private final Lloc associat;
    
    /** @pre: --
     *  @post: Es crea un punt d’interès de nom “name” amb les activitats que ofereix “acts”,
     *         el preu  “cost”, i el lloc on està associat
    */
    public PuntInteres(String name,Collection<String> acts,Double cost){
        nom= name;
        activitats= new HashSet<String>(acts);
        preu= cost;
        associat= null;
    }
    
    /** @pre: --
     *  @post: Retorna cert si el punt d’interès satisfà la preferència “pref”, fals altrament 
     */
    public Boolean satisfaPreferencia(String pref){
        return activitats.contains(pref);
    }
    
    /** @pre: --
     *  @post: Retorna el nom del punt d’interès
     */
    public String obtenirNom(){
        return nom;
    }
    
    /** @pre: --
     *  @post: Retorna el lloc on està vinculat el punt d’interès
     */
    public Lloc obtenirLloc(){
        return associat;
    }
    
    /** @pre: --
     *  @post: Retorna el cost. Si és gratis, retorna 0
     */
    public Double obtenirPreu(){
        return preu;
    }
}
