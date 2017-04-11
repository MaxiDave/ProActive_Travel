package proactive_travel;

public class FranjaHoraria {
    
    private final Hora obertura;
    private final Hora tancament;
    
    /** *  @pre: --
        @post: Crea una franja horària a partir d’un HoraDia inici i final */
    FranjaHoraria(Hora inici, Hora fi){
        obertura= inici;
        tancament= fi;
    }
    
    /** *  @pre: --
        @post: Retorna cert si l’HoraDia “instant” es troba dins de l’horari de la franja */
    Boolean pertanyFranja(Hora instant){
        //Falta implementació
    }
}
