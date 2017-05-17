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
public class TrajecteDirecte implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTDirecte mT;
    private final LocalDateTime iniciTrajecte;
    private final LocalDateTime finalTrajecte;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un Trajecte amb el transport i l'hora d'inici de trajecte
     */
    public TrajecteDirecte(MTDirecte mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        this.iniciTrajecte= iniciTrajecte;
        this.finalTrajecte= iniciTrajecte.plusMinutes(mT.getDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    
    /** 
     * @pre: --
     * @post: Retorna el PuntInteres de destí 
     */
    public PuntInteres obtPuntSortida(){
        return mT.getDesti();
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
        return iniciTrajecte; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna l'hora d'arribada del Trajecte 
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte; 
    }
    
    public Integer obtDurada(){
        return (int)Duration.between(iniciTrajecte, finalTrajecte).toMinutes();
    }
    
    @Override
    public String toString(){
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtenirNom()+" -> "+mT.getDesti().obtenirNom()+" ("+mT.getNom()+")"+"\n";
    }
}
