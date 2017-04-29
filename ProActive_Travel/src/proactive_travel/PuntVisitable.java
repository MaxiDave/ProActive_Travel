package proactive_travel;

import java.util.*;
import java.time.*;

//@MaxiDave: Implementat

public class PuntVisitable extends PuntInteres {
    /**
     * DESCRIPCIÓ GENERAL 
     * Representa un punt visitable, amb les seves franges horàries i temps de visita.
     */
    
    //ATRIBUTS
    private final Integer tempsV; //Minuts
    private final FranjaHoraria obertura;
    private final Coordenades c;
    
    /** @pre: --
     *  @post: Es crea un punt visitable a partir de la informació del punt d’interès i el temps mitjà de visita
     */
    public PuntVisitable(String name,Collection<String> acts,Double cost,Integer tempsVisita, FranjaHoraria franja, Coordenades coor){
        super(name, acts, cost);
        tempsV= tempsVisita;
        obertura= franja;
        c=coor;
    }
    
    /** @pre: --
     *  @post: Retorna el temps mitjà de visita
     */
    public Integer obtenirTempsVisita(){
        return tempsV;
    }
    
    /** @pre: --
     *  @post: Retorna cert si el punt visitable està obert en un DiaHora determinat, fals altrament
     */
    public Boolean estaObert(LocalTime inst){
        return obertura.pertanyFranja(inst);
    }
}
