//ProActive_Travel

/**
 * @file: Estacio.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Estació: Conté informació d'una estació d'un determinat mitjà de transport
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Estacio, amb les connexions de sortida i arribada, i les sortides
 */
public class Estacio implements PuntRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nomTransport;
    private Map<LocalDate, Map<MTIndirecte, Set<LocalTime>>> sortides;
    private Set<MTIndirecte> transports;
    private Map<Lloc, Integer> connexioArribada;
    private Map<Lloc, Integer> connexioSortida;
    private final Lloc pare;
    
    //CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Crea una estació de nomTransport nom 
     */
    public Estacio(String nom, Lloc pare){
        nomTransport= nom;
        sortides= new HashMap< >();
        connexioArribada= new HashMap<>();
        connexioSortida= new HashMap<>();
        transports= new HashSet<>();
        this.pare= pare;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna el nom de l'estació
     */
    public String obtNom(){
        return nomTransport;
    }
    
    /** 
     * @pre: --
     * @post: Afegeix una connexió de Sortida cap al lloc desti amb una durada de "temps" 
     */
    public void afegirConnexioSortida(Lloc desti, Integer temps){
        connexioSortida.put(desti, temps);
    }
    
    /** 
     * @pre: --
     * @post: Afegeix una connexió d'arribada des del lloc origen amb una durada de "temps" 
     */
    public void afegirConnexioArribada(Lloc origen, Integer temps){
        connexioArribada.put(origen, temps);
    }
    
    /** 
     * @pre: Ha d'existir una Connexió de sortida cap al lloc on va el mitjà
     * @post: Afegeix una sortida "mitja" (MTIndirecte)
     */
    public void afegirSortida(MTIndirecte mitja, LocalDateTime horaSortida){
        transports.add(mitja);
        Map<MTIndirecte, Set<LocalTime>> mitjansSortides= sortides.get(horaSortida.toLocalDate());
        if(mitjansSortides == null){
            Map<MTIndirecte, Set<LocalTime>> nou= new HashMap<>();
            Set<LocalTime> sortida= new TreeSet<>();
            sortida.add(horaSortida.toLocalTime());
            nou.put(mitja, sortida);
            sortides.put(horaSortida.toLocalDate(), nou);
        }
        else{
            Set<LocalTime> horesSortides= mitjansSortides.get(mitja);
            if(horesSortides == null){
                Set<LocalTime> sortida= new TreeSet<>();
                sortida.add(horaSortida.toLocalTime());
                mitjansSortides.put(mitja, sortida);
            }
            else horesSortides.add(horaSortida.toLocalTime());
        }
    }
    
    // pre sortida existeix a connexioSortida
    public Integer obtTempsSortidaLloc(Lloc origen){
        return connexioSortida.get(origen);
    }
    
    // pre sortida existeix a connexioArribada
    public Integer obtTempsArribadaLloc(Lloc desti){
        return connexioArribada.get(desti);
    }
    
    public Iterator<MTIndirecte> obtMitjans(){
        return transports.iterator();
    }
    
    public Lloc obtLloc(){
        return pare;
    }
    
    public LocalDateTime obtSortida(MTIndirecte mI, LocalDateTime actual){
        LocalDateTime resultat= null;
        Map<MTIndirecte, Set<LocalTime>> sortidesDia= sortides.get(actual.toLocalDate());
        if(sortidesDia != null){
            Set<LocalTime> sortidesHores= sortidesDia.get(mI);
            if(sortidesHores != null){
                Boolean fi= false;
                Iterator<LocalTime> itHores= sortidesHores.iterator();
                while(!fi && itHores.hasNext()){
                    LocalTime sortida= itHores.next();
                    if(!sortida.isBefore(actual.toLocalTime())){
                        resultat= LocalDateTime.of(actual.toLocalDate(), sortida);
                        fi= true;
                    }
                }
            }
        }
        return resultat;
    }
    
    public void mostraConexions(){
        System.out.println("Connexions sortida Girona");
        for (Map.Entry<Lloc, Integer> pair : connexioSortida.entrySet()) {
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
        System.out.println("Connexions arribada Girona");
        for (Map.Entry<Lloc, Integer> pair : connexioArribada.entrySet()) {
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
    }
    
    public String toString(){
        return nomTransport;
    }
}