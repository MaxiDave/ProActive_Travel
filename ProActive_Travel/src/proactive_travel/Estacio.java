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
public class Estacio {
    //ATRIBUTS
    private final String nomTransport;
    private Map<LocalDateTime, MTIndirecte> sortides;
    private Map<Lloc, Integer> connexioArribada;
    private Map<Lloc, Integer> connexioSortida;
    
    /** @pre: --
     *  @post: Crea una estació de nomTransport nom 
     */
    public Estacio(String nom){
        nomTransport= nom;
        sortides= new HashMap<LocalDateTime, MTIndirecte>();
        connexioArribada= new HashMap<Lloc, Integer>();
        connexioSortida= new HashMap<Lloc, Integer>();
    }
    
    /** @pre: --
     *  @post: Retorna el nom de l'estació
     */
    public String getNom(){
        return nomTransport;
    }
    
    /** @pre: --
     *  @post: Afegeix una connexió de Sortida cap al lloc desti amb una durada de "temps" 
     */
    public void afegirConnexioSortida(Lloc desti, Integer temps){
        connexioSortida.put(desti, temps);
    }
    
    /** @pre: --
     *  @post: Afegeix una connexió d'arribada des del lloc origen amb una durada de "temps" 
     */
    public void afegirConnexioArribada(Lloc origen, Integer temps){
        connexioArribada.put(origen, temps);
    }
    
    /** @pre: Ha d'existir una Connexió de sortida cap al lloc on va el mitjà
     *  @post: Afegeix una sortida "mitja" (MTIndirecte)
     */
    public void afegirSortida(MTIndirecte mitja, LocalDateTime horaSortida){
        sortides.put(horaSortida, mitja);
    }
}
