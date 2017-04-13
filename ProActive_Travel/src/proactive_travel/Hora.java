package proactive_travel;

//@MaxiDave: Implementat

public class Hora{
    /** 
     * DESCRIPCIÓ GENERAL
     * Conté informació d’un instant (hh24:mm).
     */
    
    //ATRIBUTS
    private final Integer hora;
    private final Integer minuts;
    
    /** @pre: 0 <= hora <= 23. 0 <= minuts <= 59 
        @post: Crea una Hora a partir de les hores i minuts en format 24h 
     */
    public Hora(Integer h, Integer m){
        hora= h;
        minuts= m;
    }
    
    public boolean mesGran(Hora h){
        return((hora>h.hora) || (hora==h.hora && minuts>h.minuts));
    }
}
