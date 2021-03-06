//ProActive_Travel

/**
 * @file: PuntInteres.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe PuntInteres: Conté informació d'un Punt d'Interès amb les seves característiques
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL 
 * @brief: Representa un punt on els clients tenen interès, bé sigui per visitar o bé per allotjar-se. 
 */
public class PuntInteres implements PuntRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    protected final String nom;
    protected final Set<String> activitats;
    protected final Double preu;
    protected Lloc associat;
    protected final Coordenades coords;
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un punt d’interès de nom “name” amb les activitats que ofereix “acts” i el preu  “cost”.
     */
    public PuntInteres(String name,Collection<String> acts,Double cost, Coordenades coor){
        nom= name;
        activitats= new HashSet<String>(acts);
        preu= cost;
        coords= coor;
        associat= null;
    }
    
    /** 
     * @pre --
     * @post 
     */
    public Integer grauSatisfaccio(Map<String, Integer> pref){
        int grauSatis=0;
        Iterator<String> it= activitats.iterator();
        while(it.hasNext()){
            Integer valor= pref.get(it.next());
            if(valor != null) grauSatis+= valor;
        }
        return grauSatis;
    }
    
    /** 
     * @pre --
     * @post Retorna el nom del punt d’interès
     */
    public String obtNom(){
        return nom;
    }
    
    /** 
     * @pre --
     * @post Retorna el lloc on està vinculat el punt d’interès
     */
    public Lloc obtenirLloc(){
        return associat;
    }
    
    /** 
     * @pre --
     * @post Retorna el cost. Si és gratis, retorna 0
     */
    public Double obtenirPreu(){
        return preu;
    }
    
    /** 
     * @pre --
     * @post El lloc "ll" passarà a ser el lloc on es vincula aquest Punt d'Interès
     */
    public void vincularLloc(Lloc ll){
        associat= ll;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.nom);
        return hash;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof PuntInteres){
            PuntInteres aux= (PuntInteres)o;
            return nom.equals(aux.nom);
        }
        else return false;
    }
    
    @Override
    public String toString(){
        return nom;
    }
    
    public Coordenades obtCoordenades(){
        return coords;
    }
}
