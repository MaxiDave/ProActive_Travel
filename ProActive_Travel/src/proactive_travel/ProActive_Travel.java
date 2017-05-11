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
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Mòdul principal que conté el MAIN
 */
public abstract class ProActive_Travel{
    
    public static File file; ///< @brief: variable on es guarda el fitxer d'entrada de dades
    
    /**
     * @pre: --
     * @post: Genera un mapa i un viatge a partir d'un fitxer d'entrada de dades i
     *        calcula Rutes a partir d'aquest.
     */
    public static void main(String[] args){
        //Inicia el GUI i crea l'scanner del fitxer seleccionat
        FitxerGUI.inicia();
        Scanner fitxer= Entrada.creaScanner(file);
        
        //Es creen les estructures de dades principals
        List<Viatge> viatges= new ArrayList<Viatge>();
        Map<String, Client> clients= new HashMap<String, Client>();
        Mapa mundi= new Mapa();
        
        //Es duu a terme el procés d'entrada de dades a partir del Scanner al mapa i al Mapa de clients
        Entrada.inicialitzaAplicatiu(fitxer, clients, mundi, viatges);
    }
}
