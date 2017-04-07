/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;
import java.time.*;

public class Lloc {
    
    private final String nom;
    private final Coordenades c;
    private List<Estacio> estacions;
    private List<PuntInteres> punts;
    
    /** @pre: --
        @post:  Es crea un lloc de nom "n" i coordeandes "coor" */ 
    Lloc(String n, Coordenades coor){
        nom= n;
        c= coor;
        punts= new ArrayList<PuntInteres>();
        estacions= new ArrayList<Estacio>();
    }
    
    /** @pre: --
        @post: Retorna el nom del lloc */
    String obtenirNom(){
        return nom;
    }
    
    /** @pre: --
        @post: Retorna les coordenades del lloc */
    Coordenades obtenirCoordenades(){
        return c;
    }
    
    /** @pre: --
        @post: Afegeix l'estació "est" a la llista d'estacions del lloc*/
    void afegirEstacio(Estacio est){
        estacions.add(est);
    }
    
    /** @pre: --
        @post: Afegeix el punt d'interès "pI" als punts d'interès del lloc */
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
