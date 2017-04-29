package proactive_travel;
import java.util.*;
import java.time.*;
public class Estacio {
    //ATRIBUTS
    private final String nomTransport;
    private Map<LocalDateTime, MTIndirecte> arribades;
    private Map<LocalDateTime, MTIndirecte> sortides;
    private Map<Lloc, Integer> tempsArribada;
    private Map<Lloc, Integer> tempsSortida;
    
     /** @pre: --
         @post: Crea una estació de nomTransport nom */
    Estacio(String nom){
        nomTransport= nom;
    }
}
