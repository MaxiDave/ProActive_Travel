/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;
import java.time.*;

public class Lloc {
    /** @pre: --
        @post:  Es crea un lloc de nom “n”, i amb les preferències i activitats “prefs” i “acts” respectivament.
     *          Visitable o no depenent de “vist” (Cert visitable, fals allotjament) i el preu del lloc visitable/allotjament (Per defecte 0).
     *          En cas de ser un lloc visitable, també s’entra el temps de visita recomanat (En format Duration de java.time). */ 
    Lloc(String n,Vector<Preferencia> prefs,Vector<Activitat> acts,boolean vist,double preu,Duration tempsV){
        
    }
    
    /** @pre: --
        @post: Retorna un iterador que apunta a les preferències del lloc */
    Iterator<Preferencia> obtenirPreferencies(){
        
    }
    
    /** @pre: --
        @post: Retorna un iterador que apunta a les activitats del lloc */
    Iterator<Activitat> obtenirActivitats(){
        
    }
    
    /** @pre: --
        @post: Retorna el preu del lloc visitable (per persona) o del allotjament (per habitació doble), depenent si és un lloc visitable o un allotjament. */
    Double obtenirPreu(){
        
    }
    
    /** @pre: Lloc visitable
        @post: Retorna el temps en format Duration (java.time) de visita recomanat */
    Duration obtenirTempsVisita(){
        
    }
    
    /** @pre: Lloc visitable
        @post: Retorna cert si el lloc visitable està obert en un determinat moment “act” (java.time) */
    Boolean estaObert(Instant act){
        
    }
    
    
    /** @pre: Lloc allotjament
        @post: Retorna la categoria de l’allotjament (Enter que representa les estrelles, +gran +categoria) */
    int obtenirCategoria(){
        
    }
    
    /** @pre: --
        @post: Retorna el nom del lloc */
    String obtenirNom(){
        
    }
    
    /** @pre: --
        @post: Retorna el nom del lloc */
    Iterator<String> obtenirNomMitjansDirectes(){
        
    }
    
    /** @pre: --
        @post: Retorna cert si el lloc és visitable, fals altrament */
    Boolean esVisitable(){
        
    }
}
