//ProActive_Travel

/**
 * @file TrajecteEstacio.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe TrajecteEstacio: Conté informació d'un TrajecteEstacio dut a terme per un MTEstacio
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un TrajecteEstacio, amb MTEstacio i les hores d'arribada i sortida. Implementa ItemRuta
 */
public class TrajecteEstacio implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final MTEstacio mT;                             ///< @brief Emmagatzema la informació referent al MTEstacio del TrajecteEstacio
    private final LocalDateTime iniciTrajecte;              ///< @brief Emmagatzema el moment de l'inici del TrajecteEstacio
    private final LocalDateTime finalTrajecte;              ///< @brief Emmagatzema el moment de destí del TrajecteEstacio
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un TrajecteEstacio amb el MTEstacio i el moment d'inici
     * @brief Es crea un TrajecteEstacio amb el MTEstacio i el moment d'inici
     */
    public TrajecteEstacio(MTEstacio mT, LocalDateTime iniciTrajecte){
        this.mT= mT;
        this.iniciTrajecte= iniciTrajecte;
        this.finalTrajecte= iniciTrajecte.plusMinutes(mT.obtDurada());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el moment d’inici del TrajecteEstacio
     * @brief Retorna el moment d’inici del TrajecteEstacio
     */
    public LocalDateTime obtInici(){
        return iniciTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el moment de fi del TrajecteEstacio
     * @brief Retorna el moment de fi del TrajecteEstacio
     */
    public LocalDateTime obtFinal(){
        return finalTrajecte; 
    }
    
    /** 
     * @pre --
     * @post Retorna el puntRuta de sortida del ItemRuta, en aquest cas l'Estacio 
     * @brief Retorna l'Estacio del TrajecteEstacio
     */
    public Estacio obtPuntSortida(){
        return mT.getDesti();
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per el TrajecteEstacio, és a dir, 0
     * @brief Retorna la satisfacció produïda per el TrajecteEstacio, és a dir, 0
     */
    public Integer obtSatisfaccio(){
        return 0;
    }
    
    /** 
     * @pre --
     * @post Retorna el cost del TrajecteEstacio
     * @brief Retorna el cost dl TrajecteEstacio
     */
    public Double obtCost(){
        return mT.obtPreu();
    }
    
    /** 
     * @pre --
     * @post Retorna la durada del TrajecteEstacio
     * @brief Retorna la durada del TrajecteEstacio
     */
    public Integer obtDurada(){
        return (int)Duration.between(iniciTrajecte, finalTrajecte).toMinutes();
    }
    
    /** 
     * @pre --
     * @post Retorna el MTEstacio del TrajecteEstacio
     * @brief Retorna el MTEstacio del TrajecteEstacio
     */
    public MTEstacio obtMitja(){
        return mT;
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
        return iniciTrajecte.toLocalTime()+"-"+finalTrajecte.toLocalTime()+" "+mT.getOrigen().obtNom()+" -> "+mT.getDesti().obtLloc().obtNom()+" ("+mT.obtNom()+")"+"\n";
    }
}
