//ProActive_Travel

/**
 * @file TrajecteIndirecte.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe TrajecteIndirecte: Conté informació d'un TrajecteIndirecte dut a terme per un MTIndirecte
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un TrajecteIndirecte, amb el MTIndirecte i les hores d'arribada i sortida. Implementa ItemRuta
 */
public class TrajecteIndirecte implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTIndirecte mT;                               ///< @brief Emmagatzema la informació referent al MTIndirecte del TrajecteIndirecte
    private final ZonedDateTime iniciTrajecte;                  ///< @brief Emmagatzema el moment de l'inici del TrajecteIndirecte
    private final ZonedDateTime finalTrajecte;                  ///< @brief Emmagatzema el moment de destí del TrajecteIndirecte
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un TrajecteIndirecte amb el MTIndirecte i el moment d'inici
     * @brief Es crea un TrajecteIndirecte amb el MTIndirecte i el moment d'inici
     */
    public TrajecteIndirecte(MTIndirecte mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        ZoneId zH= mT.getOrigen().obtLloc().obtCoordenades().obtZona();
        this.iniciTrajecte= iniciTrajecte.atZone(zH);
        this.finalTrajecte= this.iniciTrajecte.withZoneSameInstant(zH).plusMinutes(mT.obtDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el moment d’inici del TrajecteIndirecte 
     * @brief Retorna el moment d’inici del TrajecteIndirecte
     */
    public LocalDateTime obtInici(){
        return iniciTrajecte.toLocalDateTime(); 
    }
    
    /** 
     * @pre --
     * @post Retorna el moment de fi del TrajecteIndirecte
     * @brief Retorna el moment de fi del TrajecteIndirecte
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte.toLocalDateTime(); 
    }
    
    /** 
     * @pre --
     * @post Retorna el PuntRuta de sortida del ItemRuta, en aquest cas l'Estacio
     * @brief Retorna el l'Estacio del TrajecteIndirecte
     */
    public Estacio obtPuntSortida(){
        return mT.getDesti();
    }
    
    /** 
     * @pre --
     * @post Retorna el Lloc d'origen del TrajecteIndirecte
     * @brief Retorna el Lloc d'origen del TrajecteIndirecte
     */
    public Lloc obtLlocOrigen(){
        return mT.getOrigen().obtLloc();
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per el TrajecteIndirecte, és a dir, 0
     * @brief Retorna la satisfacció produïda per el TrajecteIndirecte, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre --
     * @post Retorna el cost del TrajecteIndirecte
     * @brief Retorna el cost dl TrajecteIndirecte
     */
    public Double obtCost(){
        return mT.obtPreu();
    }
    
    /** 
     * @pre --
     * @post Retorna la durada del TrajecteIndirecte
     * @brief Retorna la durada del TrajecteIndirecte
     */
    public Integer obtDurada(){
        return mT.obtDurada();
    }
    
    /** 
     * @pre --
     * @post Retorna el format d'escriptura com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el format d'escriptura
     */
    @Override
    public String toString(){
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtLloc().obtNom()+" -> "+mT.getDesti().obtLloc().obtNom()+" ("+mT.obtNom()+")"+"\n";
    }
}
