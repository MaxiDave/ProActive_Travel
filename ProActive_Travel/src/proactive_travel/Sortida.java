//ProActive_Travel

/**
 * @file: Sortida.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que s'encarrega de dur a terme càlculs de Sortida
 * @copyright: Public License
 */

package proactive_travel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: 
 */

public abstract class Sortida {
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Mostra les rutes “resultat” al fitxer nomFitxer
     */
    public static void mostrarRutes(List<Ruta> resultat, Viatge viatge, String nomFitxer){
        PrintWriter sortida= null;
        try {
            sortida = new PrintWriter(nomFitxer, "UTF-8");
            sortida.println("INFORMACIÓ DEL VIATGE");
            sortida.println();
            sortida.println(viatge);
            sortida.println();
            if(resultat.isEmpty()){
                sortida.println("NO S'HA TROBAT CAP RUTA");
            }
            else{
                sortida.println("RUTES TROBADES");
                sortida.println();
                Iterator<Ruta> it= resultat.iterator();
                while(it.hasNext()){
                    Ruta aux= it.next();
                    if(aux.obtTipus().equals("ruta barata")) sortida.println("RUTA MÉS BARATA");
                    else if(aux.obtTipus().equals("ruta curta")) sortida.println("RUTA MÉS CURTA");
                    else sortida.println("RUTA MÉS SATISFACTÒRIA");
                    sortida.println(aux);
                    sortida.println();
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Sortida.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sortida.close();
        }
    }
}
