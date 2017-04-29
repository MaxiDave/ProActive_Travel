package proactive_travel;
import java.util.*;
import java.time.*;

//@MaxiDave: Implementat

public class FranjaHoraria {
    /** 
     * DESCRIPCIÓ GENERAL
     * Conté una franja horària compresa entre dos hores determinades. 
     */
    
    //ATRIBUTS
    private final LocalTime obertura;
    private final LocalTime tancament;
    
    /** @pre: --
        @post: Crea una franja horària a partir d’un LocalTime inici i final 
     */
    FranjaHoraria(LocalTime inici, LocalTime fi){
        obertura= inici;
        tancament= fi;
    }
    
    /** @pre: --
        @post: Retorna cert si el LocalTime “instant” es troba dins de l’horari de la franja 
     */
    Boolean pertanyFranja(LocalTime instant){
        return instant.isAfter(obertura) && instant.isBefore(tancament);
    }
}
