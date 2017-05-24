//ProActive_Travel

/**
 * @file Estacio.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Estació: Conté informació d'una estació d'un determinat mitjà de transport
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa una Estacio, amb les connexions de sortida i arribada, i les sortides
 */
public class Estacio implements PuntRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nomTransport;                                  ///< @brief Emmagatzema el nom de l'Estació (Que serà el mateix que el tipus de MTIndirecte)
    private Map<LocalDate, Map<MTIndirecte, Set<LocalTime>>> sortides;  ///< @brief Estructura complexa que emmagatzema a partir del Dia, un Mapa de MTIndirectes que surten a unes determinades hores
    private Set<MTIndirecte> transports;                                ///< @brief Emmagatzema tots els MTIndirectes de l'estació
    private Map<Lloc, Integer> connexioArribada;                        ///< @brief Conté informació del temps d'arribada si es ve del lloc Lloc
    private Map<Lloc, Integer> connexioSortida;                         ///< @brief Conté informació del temps de sortida si es va al lloc Lloc
    private final Lloc pare;                                            ///< @brief Conté el lloc on es sitúa l'estació
    
    //CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Crea una estació de nom "nom" al Lloc "pare"
     * @brief Constructor
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
     * @pre --
     * @post Retorna el nom de l'estació
     * @brief Retorna el nom de l'estació
     */
    public String obtNom(){
        return nomTransport;
    }
    
    /** 
     * @pre --
     * @post Afegeix una connexió de Sortida cap al Lloc "desti" amb una durada de "temps"
     * @brief Afegeix una connexió de Sortida
     */
    public void afegirConnexioSortida(Lloc desti, Integer temps){
        connexioSortida.put(desti, temps);
    }
    
    /** 
     * @pre --
     * @post Afegeix una connexió d'arribada des del Lloc "origen" amb una durada de "temps" 
     * @brief Afegeix una connexió d'Arribada
     */
    public void afegirConnexioArribada(Lloc origen, Integer temps){
        connexioArribada.put(origen, temps);
    }
    
    /** 
     * @pre Ha d'existir una Connexió de sortida cap al lloc on va el mitjà
     * @post Afegeix una sortida "mitja" (MTIndirecte) a una hora determinada "horaSortida"
     * @brief Afegeix una sortida "mitja" (MTIndirecte)
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
    
    /** 
     * @pre "origen" existeix a connexioSortida
     * @post Retorna el Temps de Sortida a partir del Lloc "origen"
     * @brief Retorna el Temps de Sortida a partir del Lloc "origen"
     */
    public Integer obtTempsSortidaLloc(Lloc origen){
        return connexioSortida.get(origen);
    }
    
    /** 
     * @pre "desti" existeix a connexioArribada
     * @post Retorna el Temps d'arribada a partir del Lloc "desti"
     * @brief Retorna el Temps d'arribada a partir del Lloc "desti"
     */
    public Integer obtTempsArribadaLloc(Lloc desti){
        return connexioArribada.get(desti);
    }
    
    /** 
     * @pre --
     * @post Retorna un iterador als MTIndirectes de l'Estacio
     * @brief Retorna un iterador als MTIndirectes de l'Estacio
     */
    public Iterator<MTIndirecte> obtMitjans(){
        return transports.iterator();
    }
    
    /** 
     * @pre --
     * @post Retorna el Lloc on es troba l'Estacio
     * @brief Retorna el Lloc on es troba l'Estacio
     */
    public Lloc obtLloc(){
        return pare;
    }
    
    /** 
     * @pre --
     * @post Retorna el moment del mateix dia en que surt el MTIndirecte "mI" a partir del moment "actual", si no existeix retorna null
     * @brief Retorna el moment del mateix dia en que surt el MTIndirecte "mI" a partir del moment "actual"
     */
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
    
    /** 
     * @pre --
     * @post Retorna el nom com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el nom com a sobreescriptura del mètode d'Object toString
     */
    public String toString(){
        return nomTransport;
    }

}