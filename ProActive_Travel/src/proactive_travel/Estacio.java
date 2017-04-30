package proactive_travel;
import java.util.*;
import java.time.*;

public class Estacio {
    //ATRIBUTS
    private final String nomTransport;
    private Map<LocalDateTime, MTIndirecte> sortides;
    private Map<Lloc, Integer> connexioArribada;
    private Map<Lloc, Integer> connexioSortida;
    
    /** @pre: --
     *  @post: Crea una estació de nomTransport nom 
     */
    Estacio(String nom){
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
