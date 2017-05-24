//ProActive_Travel

/**
 * @file Visita.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Visita: Conté informació d'una Visita a un PuntVisitable
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa una Visita a un punt visitable, amb l'instant d'entrada i de sortida. Implementa ItemRuta
 */
public class Visita implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntVisitable pV;                     ///< @brief Emmagatzema la informació referent al PuntVisitable de la Visita
    private final LocalDateTime iniciVisita;            ///< @brief Emmagatzema el moment de l'inici de la Visita
    private final LocalDateTime finalVisita;            ///< @brief Emmagatzema el moment del final de la Visita
    private final Integer satisfaccio;                  ///< @brief Emmagatzema el grau de satisfacció que proporciona la Visita
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea una Visita a partir d’un PuntVisitable, una hora d’entrada, i un grau de satisfacció 
     * @brief Constructor
     */
    public Visita(PuntVisitable pV, LocalDateTime iniciVisita, Integer satisfaccio){
        this.pV= pV;
        this.iniciVisita= iniciVisita;
        finalVisita= iniciVisita.plusMinutes(pV.obtTempsVisita());
        this.satisfaccio= satisfaccio;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna el moment d’inici de la Visita 
     * @brief Retorna el moment d’inici de la Visita  
     */
    public LocalDateTime obtInici(){
        return iniciVisita; 
    }

    /** 
     * @pre --
     * @post Retorna el moment de fi de la Visita
     * @brief Retorna el moment de fi de la Visita 
     */
    public LocalDateTime obtFinal(){
        return finalVisita; 
    }
    
    /** 
     * @pre --
     * @post Retorna el punt d'interes de sortida del ItemRuta, en aquest cas el PuntVisitable 
     * @brief Retorna el PuntVisitable de la Visita
     */
    public PuntVisitable obtPuntSortida(){
        return pV; 
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per la Visita
     * @brief Retorna la satisfacció produïda per la Visita
     */
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    /** 
     * @pre --
     * @post Retorna el cost de la Visita
     * @brief Retorna el cost de la Visita
     */
    public Double obtCost(){
        return pV.obtenirPreu(); 
    }
    
    /** 
     * @pre --
     * @post Retorna la durada de la Visita
     * @brief Retorna la durada de la Visita
     */
    public Integer obtDurada(){
        return (int)Duration.between(iniciVisita, finalVisita).toMinutes();
    }
    
    /** 
     * @pre --
     * @post Retorna el format d'escriptura com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el format d'escriptura
     */
    @Override
    public String toString(){
        return iniciVisita.toLocalTime()+"-"+finalVisita.toLocalTime()+" "+pV.obtNom()+" (Visita)"+"\n";
    }
}
