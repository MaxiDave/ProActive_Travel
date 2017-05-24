//ProActive_Travel

/**
 * @file TrajectePunts.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe TrajectePunts: Conté informació d'un TrajectePunts dut a terme per un MTPunts
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un TrajectePunts, amb el MTPunts i les hores d'arribada i sortida. Implementa ItemRuta
 */
public class TrajectePunts implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTPunts mT;                                   ///< @brief Emmagatzema la informació referent al MTPunts del TrajectePunts
    private final LocalDateTime iniciTrajecte;                  ///< @brief Emmagatzema el moment de l'inici del TrajectePunts
    private final LocalDateTime finalTrajecte;                  ///< @brief Emmagatzema el moment de destí del TrajectePunts
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un TrajectePunts amb el MTPunts i el moment d'inici
     * @brief Es crea un TrajectePunts amb el MTPunts i el moment d'inici
     */
    public TrajectePunts(MTPunts mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        this.iniciTrajecte= iniciTrajecte;
        this.finalTrajecte= iniciTrajecte.plusMinutes(mT.obtDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el moment d’inici del TrajectePunts
     * @brief Retorna el moment d’inici del TrajectePunts
     */
    public LocalDateTime obtInici(){
        return iniciTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el moment de fi del TrajectePunts
     * @brief Retorna el moment de fi del TrajectePunts
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el PuntRuta de sortida del ItemRuta, en aquest cas el PuntInteres 
     * @brief Retorna el PuntInteres del TrajectePunts
     */
    public PuntInteres obtPuntSortida(){
        return mT.getDesti();
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per el TrajectePunts, és a dir, 0
     * @brief Retorna la satisfacció produïda per el TrajectePunts, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre --
     * @post Retorna el cost del TrajectePunts
     * @brief Retorna el cost dl TrajectePunts
     */
    public Double obtCost(){
        return mT.obtPreu();
    }
    
    /** 
     * @pre --
     * @post Retorna la durada del TrajectePunts
     * @brief Retorna la durada del TrajectePunts
     */
    public Integer obtDurada(){
        return (int)Duration.between(iniciTrajecte, finalTrajecte).toMinutes();
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
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtLloc().obtNom()+" -> "+mT.getDesti().obtNom()+" ("+mT.obtNom()+")"+"\n";
    }
}
