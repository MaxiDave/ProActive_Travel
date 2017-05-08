//ProActive_Travel

/**
 * @file: Client.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Client: Conté informació d'un client i de les seves preferències
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Client, amb unes preferències determinades
 */
public class Client {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private String nom;
    private Set<String> preferencies;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Es crea un client amb nom “n” i preferències “prefs” 
     */
    public Client(String n, Set<String> prefs){
        nom= n;
        preferencies= prefs;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Retorna el nom del client 
     */
    public String obtenirNom(){
        return nom;
    }
    
    public Iterator<String> obtPref(){
        return preferencies.iterator();
    }
}
