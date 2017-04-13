package proactive_travel;

//@MaxiDave: Implementat

public class FranjaHoraria {
    /** 
     * DESCRIPCIÓ GENERAL
     * Conté una franja horària compresa entre dos hores determinades. 
     */
    
    //ATRIBUTS
    private final Hora obertura;
    private final Hora tancament;
    
    /** @pre: --
        @post: Crea una franja horària a partir d’un HoraDia inici i final 
     */
    FranjaHoraria(Hora inici, Hora fi){
        obertura= inici;
        tancament= fi;
    }
    
    /** @pre: --
        @post: Retorna cert si l’HoraDia “instant” es troba dins de l’horari de la franja 
     */
    Boolean pertanyFranja(Hora instant){
        return (tancament.mesGran(obertura) && instant.mesGran(obertura) && tancament.mesGran(instant)) || (tancament.mesGran(instant) || instant.mesGran(obertura));
    }
}
