//ProActive_Travel

/**
 * @file: Trajecte.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Trajecte: Conté informació d'un Trajecte dut a terme per un mitjà de transport
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;


/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Trajecte, amb el mitjà de transport i les hores d'arribada i sortida
 */

public class TrajecteIndirecte implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTIndirecte mT;
    private final ZonedDateTime iniciTrajecte;
    private final ZonedDateTime finalTrajecte;
    private final PuntInteres desti;
    private final Integer duradaTotal;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Trajecte amb el mitjà indirecte, l'hora d'inici de trajecte, i el punt d'interés de destí
     */
    public TrajecteIndirecte(MTIndirecte mT, LocalDateTime iniciTrajecte, PuntInteres desti){
        this.mT= mT;
        this.desti= desti;
        this.iniciTrajecte= iniciTrajecte.atZone(mT.getOrigen().obtenirCoordenades().obtZona());
        Estacio estacioSortida= mT.getOrigen().obtEstacio(mT.getNom());
        Integer duradaSortida= estacioSortida.obtTempsSortidaLloc(mT.getOrigen());
        Estacio estacioArribada= mT.getDesti().obtEstacio(mT.getNom());
        Integer duradaArribada= estacioArribada.obtTempsArribadaLloc(mT.getDesti());
        duradaTotal= duradaSortida+mT.getDurada()+duradaArribada;
        this.finalTrajecte= this.iniciTrajecte.withZoneSameInstant(mT.getDesti().obtenirCoordenades().obtZona()).plusMinutes(duradaTotal);
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres de destí 
     */
    public PuntInteres obtPuntSortida(){
        return desti;
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per el trajecte, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre: --
     * @post: Retorna el preu del trajecte 
     */
    public Double obtCost(){
        return mT.getPreu();
    }
    
    /** 
     * @pre: --
     * @post: Retorna l'hora de sortida del Trajecte 
     */
    public LocalDateTime obtInici(){
        return iniciTrajecte.toLocalDateTime(); 
    }
    
    /** 
     * @pre: --
     * @post: Retorna l'hora d'arribada del Trajecte 
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte.toLocalDateTime(); 
    }
    
    public Integer obtDurada(){
        return duradaTotal;
    }
    
    @Override
    public String toString(){
        return "TrajecteIndirecte: "+mT.getNom();
    }
}
