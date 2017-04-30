package proactive_travel;

/**
 *
 * @author Roger
 */

public class MitjaTransport {
    //ATRIBUTS
    private final String nom;
    private final Double preu;
    private final Integer durada;
    
    /**
     * @pre: Origen i destí han de ser llocs o punts d’interès
     * @post: Crea un mitjà de transport amb preu, durada i descriptor
    */
    public MitjaTransport(String descriptor, Double cost, Integer dur){
        nom= descriptor;
        preu= cost;
        durada= dur;
    }
    
    /**
     * @pre: --
     * @post: Retorna el nom del transport
     */
    public String getNom(){
        return nom;
    }
    
    /**
     * @pre: --
     * @post: Retorna el preu del transport
     */
    public Double getPreu(){
        return preu;
    }
    
    /**
     * @pre: --
     * @post: Retorna la durada del transport
     */
    public Integer getDurada(){
        return durada;
    }
}
