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
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Trajecte amb el mitjà indirecte, l'hora d'inici de trajecte, i el punt d'interés de destí
     */
    public TrajecteIndirecte(MTIndirecte mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        ZoneId zH= mT.getOrigen().obtLloc().obtCoordenades().obtZona();
        this.iniciTrajecte= iniciTrajecte.atZone(zH);
        this.finalTrajecte= this.iniciTrajecte.withZoneSameInstant(zH).plusMinutes(mT.getDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres de destí 
     */
    public Estacio obtPuntSortida(){
        return mT.getDesti();
    }
    
    public Lloc obtLlocOrigen(){
        return mT.getOrigen().obtLloc();
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
        return mT.getDurada();
    }
    
    @Override
    public String toString(){
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtLloc().obtNom()+" -> "+mT.getDesti().obtLloc().obtNom()+" ("+mT.getNom()+")"+"\n";
    }
}
