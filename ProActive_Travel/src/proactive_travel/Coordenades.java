package proactive_travel;

import java.util.TimeZone;

public class Coordenades {
    
    private final String latitud;
    private final String longitud;
    private final TimeZone tZ;
    
    /** @pre: -12 <= zH <= 14
        @post: Es crea unes coordenades separant latitud i longitud de coor, i 
        *      zona horària UTC (Representada amb un TimeZone) */
    public Coordenades(String coor, String z){
        String[] latLong = coor.split(",");
        latitud= latLong[0];
        longitud= latLong[1];
        tZ = TimeZone.getTimeZone(z);
        tZ.setID(z);
    }
    
    /** @pre: --
        @post: Retorna la zona horària de les coordenades */
    public Integer obtenirZonaHoraria(){
        return tZ.getDSTSavings();
    }
}
