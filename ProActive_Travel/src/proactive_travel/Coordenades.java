//ProActive_Travel

/**
 * @file: Coordenades.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Coordenades: Conté informació d'unes coordenades geogràfiques que pertanyen a una determinada zona horària
 * @copyright: Public License
 */

package proactive_travel;
import java.time.ZoneId;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa unes coordenades, amb latitud, longitud i zona horària.
 */
public class Coordenades {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String latitud;
    private final String longitud;
    private final ZoneId tZ;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: z ha de ser un format de l'API de Java TimeZone
     * @post: Es crea unes coordenades separant latitud i longitud de coor, i 
     *        zona horària UTC (Representada amb un TimeZone) 
     */
    public Coordenades(String coor, String z){
        String[] latLong = coor.split(",");
        latitud= latLong[0];
        longitud= latLong[1];
        tZ = ZoneId.of(z);
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    
}
