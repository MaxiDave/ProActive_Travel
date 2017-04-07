package proactive_travel;

public class HoraDia{
    
    private final String dia;
    private final Integer hora;
    private final Integer minuts;
    
    /** @pre: dia ha d’existir dins de «dl,dm,dc,dj,dv,ds,dg»,
        * 0 <= hora <= 23. 0 <= minuts <= 59 
        @post: Crea una Hora d’un dia a partir del dia de la setmana “dia” i les hores 
        * i minuts en format 24h */
    public HoraDia(String d, Integer h, Integer m){
        dia= d;
        hora= h;
        minuts= m;
    }
}
