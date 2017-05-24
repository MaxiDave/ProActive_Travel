//ProActive_Travel

/**
 * @file: CalculGreedy.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en trobar
 *         una bona solució (Greedy) de Ruta, en termes monetaris, temps i Satisfacció 
 * @copyright: Public License
 */
package proactive_travel;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Classe que s'encarrega de generar un fitxer kml
 */

public class sortidaKML {
    private static PrintWriter sortidaKML;  ///< @brief PrintWriter que aculmulara tota la informacio per enviarla a un fitxer
    private static int contadorFitxer=0;    ///< @brief Contador perque al haver mes de una ruta no es sobreescriguin
    
    /**
     * @pre --
     * @post Posa tot el que ha d'anar a la capcalera del fitxer
     * @brief Capcalera del fitxer
     */
    private static void setup() throws FileNotFoundException, UnsupportedEncodingException{
        //Preparacio del fitxer
        sortidaKML = new PrintWriter("viatge"+contadorFitxer+".kml","UTF-8"); //Aqui tens el nom si el vols passar per parametre amb string et deixo sino posem un generic
        
        //Capçalera del fitxer
        sortidaKML.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sortidaKML.println("<kml xmlns=\"http://earth.google.com/kml/2.2\">");
        sortidaKML.println("    <Document>");
        sortidaKML.println("        <Style id=\"myDefaultStyles\">");
        sortidaKML.println("            <LineStyle>");
        sortidaKML.println("                <color>FF0000FF</color>"); //Color de les lineas per si vermell no t'agrada
        sortidaKML.println("                <width>3.0</width>");
        sortidaKML.println("            </LineStyle>");
        sortidaKML.println("            <IconStyle>");
        sortidaKML.println("                <scale>1.0</scale>");
        sortidaKML.println("                <Icon>");
        sortidaKML.println("                    <href>http://maps.google.com/mapfiles/kml/shapes/flag.png</href>");
        sortidaKML.println("                </Icon>");
        sortidaKML.println("                <hotSpot yunits=\"fraction\" y=\"0.0\" xunits=\"fraction\" x=\"0.45\"/>");
        sortidaKML.println("            </IconStyle>");
        sortidaKML.println("        </Style>");
    }
    
    /**
     * @pre --
     * @post Posa tot el que ha d'anar al final del fitxer i escriu tot el que ha anat guardant
     * @brief Final del fitxer
     */
    private static void ending(){
        sortidaKML.println("    </Document>");
        sortidaKML.println("</kml>");
        
        sortidaKML.flush();
    }
    
    /**
     * @pre --
     * @post Posa una bandera amb nom a una latitud i longitud
     * @brief Posa una bandera
     */
    private static void posarBandera(String nom,String latitud,String longitud){
        sortidaKML.println("<Placemark>");
        sortidaKML.print("    <name>");
        sortidaKML.print(nom);
        sortidaKML.println("</name>");
        sortidaKML.println("    <styleUrl>#myDefaultStyles</styleUrl>");
        sortidaKML.println("    <Point>");
        sortidaKML.print("        <coordinates>");
        sortidaKML.print(longitud);
        sortidaKML.print(",");
        sortidaKML.print(latitud);
        sortidaKML.println(",0.0</coordinates>");
        sortidaKML.println("    </Point>");
        sortidaKML.println("</Placemark>");
    }
    
    /**
     * @pre --
     * @post Traca el circuit a les coordenades passades per parametre
     * @brief Fa el circuit
     */
    private static void ferCircuit(Deque<Coordenades> l){
        sortidaKML.println("<Placemark>");
        sortidaKML.println("    <name>Circuit</name>"); //Si et fa ilu pots posar un nom millor
        sortidaKML.println("    <styleUrl>#myDefaultStyles</styleUrl>");
        sortidaKML.println("    <LineString>");
        sortidaKML.println("        <extrude>false</extrude>");
        sortidaKML.println("        <tessellate>true</tessellate>");
        sortidaKML.println("        <altitudeMode>clampToGround</altitudeMode>");
        sortidaKML.println("        <coordinates>");
        
        for(Coordenades c : l){
            sortidaKML.print("            ");
            sortidaKML.print(c.obtLongitud());
            sortidaKML.print(",");
            sortidaKML.print(c.obtLatitud());
            sortidaKML.println(",0.0");
        }
        
        sortidaKML.println("        </coordinates>");
        sortidaKML.println("    </LineString>");
        sortidaKML.println("</Placemark>");
    }
    
    /**
     * @pre --
     * @post Genera un fitxer KML amb la ruta donada
     * @brief Genera el fitxer KML
     */
    public static void generarFitxer(Ruta r){
        //Setup
        contadorFitxer++;
        Iterator<ItemRuta> it = r.iterarItems();
        ItemRuta ir = null;
        
        try {
            try {
                setup();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(sortidaKML.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(sortidaKML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Deque<Coordenades> lc = new ArrayDeque<>();
        while(it.hasNext()){
            ir = it.next();
            if(ir instanceof Visita || ir instanceof EstadaHotel){
                if(ir.obtPuntSortida() instanceof PuntInteres){
                    //Afegir al List per fer circuit i posar bandera
                    if (ir.obtPuntSortida() instanceof PuntVisitable) {
                        lc.add(((PuntInteres)ir.obtPuntSortida()).obtCoordenades());
                        posarBandera(((PuntInteres)ir.obtPuntSortida()).obtNom(),lc.getLast().obtLatitud(),lc.getLast().obtLongitud());
                    }
                    else{
                        lc.add(((EstadaHotel) ir).obtPuntSortida().obtCoordenades());
                        posarBandera(((PuntInteres)ir.obtPuntSortida()).obtNom(),lc.getLast().obtLatitud(),lc.getLast().obtLongitud());
                    }
                }
            }
            else{
                if(ir instanceof TrajecteDirecte){
                    lc.add(((TrajecteDirecte)ir).obtPuntSortida().obtCoordenades());
                }
                else if(ir instanceof TrajecteEstacio){
                    PuntRuta pr = ((TrajecteEstacio)ir).obtPuntSortida();
                    lc.add(((Estacio)pr).obtLloc().obtCoordenades());
                }
                else if(ir instanceof TrajectePunts){
                    PuntRuta pr = (((TrajectePunts)ir).obtPuntSortida());
                    lc.add(((PuntInteres)pr).obtCoordenades());
                }
                else{
                    lc.add(((TrajecteIndirecte)ir).obtPuntSortida().obtLloc().obtCoordenades());
                }
            }
        }
        ferCircuit(lc);
        ending();
    }
}
