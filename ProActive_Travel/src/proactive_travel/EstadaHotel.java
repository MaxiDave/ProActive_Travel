//ProActive_Travel

/**
 * @file EstadaHotel.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe EstadaHotel: Conté informació d'una EstadaHotel en un Allotjament
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa una Estada en un Allotjament que implementa ItemRuta, amb l'inici de la estada i el final.
 */
public class EstadaHotel implements ItemRuta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final Allotjament hotel;                ///< @brief Emmagatzema la informació referent al Allotjament de l'EstadaHotel
    private final LocalDateTime iniciEstada;        ///< @brief Emmagatzema el moment de l'inici de l'EstadaHotel
    private final LocalDateTime finalEstada;        ///< @brief Emmagatzema el moment del final de l'EstadaHotel
    private final Integer satisfaccio;              ///< @brief Emmagatzema el grau de satisfacció que proporciona l'EstadaHotel
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea una EstadaHotel a partir d’un Allotjament, una hora d’inici i un grau de satisfacció
     * @brief Constructor
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
     * @pre --
     * @post Retorna el moment d’inici de l'Estada 
     * @brief Retorna el moment d’inici de l'Estada  
     */
    public LocalDateTime obtInici(){
        return iniciEstada;
    }

    /** 
     * @pre --
     * @post Retorna el moment de fi de l'Estada 
     * @brief Retorna el moment de fi de l'Estada 
     */
    public LocalDateTime obtFinal(){
        return finalEstada; 
    }
    
    /** 
     * @pre --
     * @post Retorna el punt d'interes de sortida del ItemRuta, en aquest cas l'Allotjament 
     * @brief Retorna l'Allotjament de l'EstadaHotel
     */
    public Allotjament obtPuntSortida(){
        return hotel; 
    }
    
    /** 
     * @pre --
     * @post Retorna la satisfacció produïda per l'EstadaHotel
     * @brief Retorna la satisfacció produïda per l'EstadaHotel
     */
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    /** 
     * @pre --
     * @post Retorna el cost per persona i per habitació doble de l'EstadaHotel
     * @brief Retorna el cost per persona i per habitació doble de l'EstadaHotel
     */
    public Double obtCost(){
        return hotel.obtenirPreu(); 
    }
    
    /** 
     * @pre --
     * @post Retorna la durada de l'EstadaHotel
     * @brief Retorna la durada de l'EstadaHotel
     */
    public Integer obtDurada(){
        return (int)Duration.between(iniciEstada, finalEstada).toMinutes();
    }
    
    /** 
     * @pre --
     * @post Retorna el format d'escriptura com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el format d'escriptura
     */
    @Override
    public String toString(){
        return iniciEstada.toLocalTime()+"-..:.. "+hotel.obtNom()+" (Estada)"+"\n";
    }
}