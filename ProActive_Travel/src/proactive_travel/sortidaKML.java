/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.io.*;
import java.util.*;

/**
 *
 * @author Roger
 */

public class sortidaKML {
    private PrintWriter sortidaKML;
    public void setup() throws FileNotFoundException, UnsupportedEncodingException{
        //Preparacio del fitxer
        sortidaKML = new PrintWriter("NOM_FITXER.kml","UTF-8");
        
        //Capçalera del fitxer
        sortidaKML.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sortidaKML.println("kml xmlns=\"http://earth.google.com/kml/2.2\"");
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
    
    public void ending(){
        sortidaKML.println("    </Document>");
        sortidaKML.println("</kml>");
    }
    
    public void posarBandera(String nom,String latitud,String longitud){
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
    
    public void ferCircuit(List<Coordenades> l){
        sortidaKML.println("<Placemark>");
        sortidaKML.println("    <name>Circuit</name>"); //Si et fa ilu pots posar un nom millor
        sortidaKML.println("    <styleUrl>#myDefaultStyles</styleUrl>");
        sortidaKML.println("    <LineString>");
        sortidaKML.println("        <extrude>false</extrude>");
        sortidaKML.println("        <tessellate>true</tessellate>");
        sortidaKML.println("        <altitudeMode>clampToGround</altitudeMode>");
        sortidaKML.println("        <coordinates>");
        
        for(Coordenades c : l){
            sortidaKML.print(c.obtLongitud());
            sortidaKML.print(",");
            sortidaKML.print(c.obtLatitud());
            sortidaKML.println(",0.0");
        }
        
        sortidaKML.println("        </coordinates>");
        sortidaKML.println("    </LineString>");
        sortidaKML.println("</Placemark>");
    }
}
