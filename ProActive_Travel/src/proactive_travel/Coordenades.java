package proactive_travel;

public class Coordenades {
    
    private final String latitud;
    private final String longitud;
    private final Double zH;
    
    /** @pre: -12 <= zH <= 14
        @post: Es crea unes coordenades amb latitud, longitud i 
        * zona horària UTC (Representada amb un nombre real) */
    public Coordenades(String lat, String lon, Double z){
        latitud= lat;
        longitud= lon;
        zH= z;
    }
    
    /** @pre: --
        @post: Retorna la zona horària de les coordenades */
    public Double obtenirZonaHoraria(){
        return zH;
    }
}
