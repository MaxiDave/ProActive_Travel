/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roger
 */

public class sortidaKML {
    private static PrintWriter sortidaKML;
    private static int contadorFitxer=0;
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
    
    private static void ending(){
        sortidaKML.println("    </Document>");
        sortidaKML.println("</kml>");
        
        sortidaKML.flush();
    }
    
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
