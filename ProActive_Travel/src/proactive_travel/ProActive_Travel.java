//ProActive_Travel

/**
 * @file: ProActive_Travel.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que conté el programa principal del programa (main class)
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Mòdul principal que conté el MAIN
 */
public abstract class ProActive_Travel{
    
    public static List<Viatge> viatges;
    public static Map<String, Client> clients;
    public static Mapa mundi;
    
    /**
     * @pre: --
     * @post: Genera un mapa i un viatge a partir d'un fitxer d'entrada de dades i
     *        calcula Rutes a partir d'aquest.
     */
    public static void main(String[] args){
        //Inicia el GUI i crea l'scanner del fitxer seleccionat
        GUI.inicia();
    }
}
