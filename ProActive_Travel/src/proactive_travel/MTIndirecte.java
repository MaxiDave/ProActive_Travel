package proactive_travel;
import java.time.*;

/**
 *
 * @author Roger
 */
public class MTIndirecte extends MitjaTransport{
    //ATRIBUTS
    private final Lloc origen;
    private final Lloc desti;
    
    /**
     * @pre: --
     * @post: Crea un mitjà de transport amb origen, destí, preu, durada i descriptor
     */
    public MTIndirecte(String descriptor, Lloc o, Lloc d, Double preu, Integer durada) {
        super(descriptor, preu, durada);
        origen= o;
        desti= d;
    }
    
    /**
     * @pre: --
     * @post: Retorna el lloc d’origen del transport
     */
    public Lloc getOrigen(){
        return origen;
    }
    
    /**
     * @pre: -
     * @post: Retorna el lloc de destí del transport
     */
    public Lloc getDesti(){
        return desti;
    }
}
