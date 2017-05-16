//ProActive_Travel

/**
 * @file: Visita.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Visita: Conté informació d'un punt que es pot visitar
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Visita a un punt visitable, amb l'instant d'entrada i de sortida. És un item de Ruta
 */
public class Visita implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final PuntVisitable pV;
    private final LocalDateTime iniciVisita;
    private final LocalDateTime finalVisita;
    private final Integer satisfaccio;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una Visita a partir d’un PuntVisitable, una hora d’entrada, i un grau de satisfacció 
     */
    public Visita(PuntVisitable pV, LocalDateTime iniciVisita, Integer satisfaccio){
        this.pV= pV;
        this.iniciVisita= iniciVisita;
        finalVisita= iniciVisita.plusMinutes(pV.obtTempsVisita());
        this.satisfaccio= satisfaccio;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna l’hora d’entrada de la Visita 
     */
    public LocalDateTime obtInici(){
        return iniciVisita; 
    }

    /** 
     * @pre: --
     * @post: Retorna l’hora de sortida de la Visita 
     */
    public LocalDateTime obtFinal(){
        return finalVisita; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el punt d'interes de sortida de la visita, en aquest cas el propi punt visitable 
     */
    public PuntInteres obtPuntSortida(){
        return pV; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per la visita
     */
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el cost per persona de la visita
     */
    public Double obtCost(){
        return pV.obtenirPreu(); 
    }
    
    public Integer obtDurada(){
        return (int)Duration.between(iniciVisita, finalVisita).toMinutes();
    }
    
    @Override
    public String toString(){
        return "Visita: "+pV.obtenirNom();
    }
}
