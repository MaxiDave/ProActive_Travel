//ProActive_Travel

/**
 * @file: Allotjament.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Allotjament: Conté informació d'un punt on es pot passar una nit
 * @copyright: Public License
 */

package proactive_travel;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Estada en un Hotel, amb l'inici de la estada i el final.
 */
public class EstadaHotel implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Allotjament hotel;
    private final LocalDateTime iniciEstada;
    private final LocalDateTime finalEstada;
    private final Integer satisfaccio;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea una EstadaHotel a partir d’un Allotjament, una hora d’inici i de fi i un grau de satisfacció
     */
    public EstadaHotel(Allotjament hotel, LocalDateTime iniciEstada, Integer satisfaccio){
        this.hotel= hotel;
        this.iniciEstada= iniciEstada;
        LocalDateTime aux= iniciEstada;
        if(iniciEstada.toLocalTime().isAfter(LocalTime.of(3,59))) aux= iniciEstada.plusDays(1);
        aux= aux.withHour(4);
        aux= aux.withMinute(0);
        this.finalEstada= aux;
        this.satisfaccio= satisfaccio;
    }

    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna l’hora d’inici de l'Estada 
     */
    public LocalDateTime obtInici(){
        return iniciEstada;
    }

    /** 
     * @pre: --
     * @post: Retorna l’hora de fi de l'Estada 
     */
    public LocalDateTime obtFinal(){
        return finalEstada; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el punt d'interes de sortida del Item, en aquest cas l'hotel 
     */
    public Allotjament obtPuntSortida(){
        return hotel; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna la satisfacció produïda per l'estada
     */
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    /** 
     * @pre: --
     * @post: Retorna el cost per persona de l'estada
     */
    public Double obtCost(){
        return hotel.obtenirPreu(); 
    }
    
    public Integer obtDurada(){
        return (int)Duration.between(iniciEstada, finalEstada).toMinutes();
    }
    
    @Override
    public String toString(){
        return iniciEstada.toLocalTime()+"-..:.. "+hotel.obtNom()+" (Estada)"+"\n";
    }
}