package proactive_travel;

public class FranjaHoraria {
    
    private final HoraDia obertura;
    private final HoraDia tancament;
    
    /** @pre: --
        @post: Crea una franja horària a partir d’un HoraDia inici i final */
    FranjaHoraria(HoraDia inici, HoraDia fi){
        obertura= inici;
        tancament= fi;
    }
    
    /** @pre: --
        @post: Retorna cert si l’HoraDia “instant” es troba dins de l’horari de la franja */
    Boolean pertanyFranja(HoraDia instant){
        //Falta implementació
    }
}
