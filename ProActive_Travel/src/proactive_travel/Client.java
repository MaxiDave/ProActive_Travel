//ProActive_Travel

/**
 * @file Client.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Client: Conté informació d'un client i de les seves preferències
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.util.Iterator;
import java.util.Set;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un Client, amb unes preferències determinades
 */
public class Client {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private final String nom;                 ///< @brief Emmagatzema el nom del Client
    private final Set<String> prefs;          ///< @brief Emmagatzema les preferències del Client
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un client amb nom “n” i preferències “prefs”
     * @brief Constructor
     */
    public Client(String nom, Set<String> prefs){
        this.nom= nom;
        this.prefs= prefs;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Crea i Retorna un iterador a les preferències del Client
     * @brief Crea i Retorna un iterador a les preferències
     */
    public Iterator<String> obtPref(){
        return prefs.iterator();
    }
    
    /** 
     * @pre --
     * @post Retorna el nom com a sobreescriptura del mètode d'Object toString
     * @brief Retorna el nom com a sobreescriptura del mètode d'Object toString
     */
    @Override
    public String toString(){
        return nom;
    }
}
