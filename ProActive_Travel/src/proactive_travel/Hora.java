package proactive_travel;

public class Hora{
    
    private final Integer hora;
    private final Integer minuts;
    
    /** @pre: dia ha d’existir dins de «dl,dm,dc,dj,dv,ds,dg»,
        * 0 <= hora <= 23. 0 <= minuts <= 59 
        @post: Crea una Hora d’un dia a partir del dia de la setmana “dia” i les hores 
        * i minuts en format 24h */
    public Hora(Integer h, Integer m){
        hora= h;
        minuts= m;
    }
}
