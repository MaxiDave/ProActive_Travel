package proactive_travel;

import java.util.TimeZone;

public class Coordenades {
    
    private final String latitud;
    private final String longitud;
    private final TimeZone tZ;
    
    /** @pre: -12 <= zH <= 14
        @post: Es crea unes coordenades amb latitud, longitud i 
        * zona horària UTC (Representada amb un nombre real) */
    public Coordenades(String lat, String lon, String z){
        latitud= lat;
        longitud= lon;
        tZ = TimeZone.getTimeZone(z);
        tZ.setID(z);
    }
    
    /** @pre: --
        @post: Retorna la zona horària de les coordenades */
    public Integer obtenirZonaHoraria(){
        return tZ.getDSTSavings();
    }
}
