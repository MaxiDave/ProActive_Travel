//ProActive_Travel

/**
 * @file TrajecteDirecte.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe TrajecteDirecte: Conté informació d'un TrajecteDirecte dut a terme per un MTDirecte
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un TrajecteDirecte, amb el MTDirecte i les hores d'arribada i sortida. Implementa ItemRuta
 */
public class TrajecteDirecte implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTDirecte mT;                         ///< @brief Emmagatzema la informació referent al MTdirecte del TrajecteDirecte
    private final LocalDateTime iniciTrajecte;          ///< @brief Emmagatzema el moment de l'inici del TrajecteDirecte
    private final LocalDateTime finalTrajecte;          ///< @brief Emmagatzema el moment de destí del TrajecteDirecte
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un TrajecteDirecte amb el MTDirecte i el moment d'inici
     * @brief Es crea un TrajecteDirecte amb el MTDirecte i el moment d'inici
     */
    public TrajecteDirecte(MTDirecte mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        this.iniciTrajecte= iniciTrajecte;
        this.finalTrajecte= iniciTrajecte.plusMinutes(mT.obtDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el moment d’inici del TrajecteDirecte 
     * @brief Retorna el moment d’inici del TrajecteDirecte 
     */
    public LocalDateTime obtInici(){
        return iniciTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el moment de fi del TrajecteDirecte
     * @brief Retorna el moment de fi del TrajecteDirecte
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el PuntRuta de sortida del ItemRuta, en aquest cas el PuntInteres 
     * @brief Retorna el PuntInteres del TrajecteDirecte
     */
    public PuntInteres obtPuntSortida(){
        return mT.getDesti();
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per el TrajecteDirecte, és a dir, 0
     * @brief Retorna la satisfacció produïda per el TrajecteDirecte, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre --
     * @post Retorna el cost del TrajecteDirecte
     * @brief Retorna el cost dl TrajecteDirecte
     */
    public Double obtCost(){
        return mT.obtPreu();
    }
    
    /** 
     * @pre --
     * @post Retorna la durada del TrajecteDirecte
     * @brief Retorna la durada del TrajecteDirecte
     */
    public Integer obtDurada(){
        return (int)Duration.between(iniciTrajecte, finalTrajecte).toMinutes();
    }
    
    /** 
     * @pre --
     * @post Retorna el MTDirecte del TrajecteDirecte
     * @brief Retorna el MTDirecte del TrajecteDirecte
     */
    public MTDirecte obtMitja(){
        return mT;
    }
    
    /** 
     * @pre --
     * @post Retorna cert si l'Objecte o és un TrajecteDirecte i és igual al TrajecteDirecte this
     * @brief Retorna cert si l'Objecte o és un TrajecteDirecte i és igual al TrajecteDirecte this
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof TrajecteDirecte){
            TrajecteDirecte tD= (TrajecteDirecte)o;
            return mT.obtNom().equals(tD.mT.obtNom()) && mT.getOrigen().equals(tD.mT.getOrigen()) && mT.getDesti().equals(tD.mT.getDesti());
        }
        else return false;
    }
    
    /** 
     * @pre --
     * @post Sobreescriptura del hashCode generada automàticament
     * @brief Sobreescriptura del hashCode generada automàticament
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.mT);
        return hash;
    }
    
    /** 
     * @pre --
     * @post Retorna el format d'escriptura com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el format d'escriptura
     */
    @Override
    public String toString(){
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtNom()+" -> "+mT.getDesti().obtNom()+" ("+mT.obtNom()+")"+"\n";
    }
}
