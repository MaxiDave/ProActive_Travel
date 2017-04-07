package proactive_travel;

import java.util.*;

public class Client {
    
    private String nom;
    private Vector<String> preferencies;
    
    /** @pre: --
        @post: Es crea un client amb nom “n” i preferències “prefs” */
    public Client(String n, Vector<String> prefs){
        nom= n;
        preferencies= prefs;
    }
    
    /** @pre: --
        @post: Retorna el nom del client */
    public String obtenirNom(){
        return nom;
    }
    /** @pre: --
        @post: Retorna un iterador a les preferències del client */
    public Iterator<String> obtenirPreferencies(){
        return preferencies.iterator();
    }
}
