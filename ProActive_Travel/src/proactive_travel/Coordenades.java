//ProActive_Travel

/**
 * @file Coordenades.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Coordenades: Conté informació d'unes coordenades geogràfiques que pertanyen a una determinada zona horària
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.ZoneId;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa unes coordenades, amb latitud, longitud i zona horària.
 */
public class Coordenades {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String latitud;       ///< @brief Emmagatzema la latitud en forma d'String
    private final String longitud;      ///< @brief Emmagatzema la longitud en forma d'String
    private final ZoneId tZ;            ///< @brief Emmagatzema la zona horària en format ZoneId
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre "zH" ha de ser un format de l'API de Java TimeZone. Ex. "Europe/Madrid"
     * @post Es crea unes coordenades separant latitud i longitud de "coordenades", i 
     *        zona horària UTC "zH" (Representada amb un TimeZone) 
     * @brief Constructor
     */
    public Coordenades(String coordenades, String zH){
        String[] latLong = coordenades.split(",");
        latitud= latLong[0];
        longitud= latLong[1];
        tZ = ZoneId.of(zH);
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Retorna la ZoneId de les Coordenades 
     * @brief Retorna la ZoneId de les Coordenades
     */
    public ZoneId obtZona(){
        return tZ;
    }
    
    /** 
     * @pre --
     * @post Retorna la latitud de les Coordenades 
     * @brief Retorna la latitud de les Coordenades
     */
    public String obtLatitud(){
        return latitud;
    }
    
    /** 
     * @pre --
     * @post Retorna la longitud de les Coordenades 
     * @brief Retorna la longitud de les Coordenades
     */
    public String obtLongitud(){
        return longitud;
    }
}
