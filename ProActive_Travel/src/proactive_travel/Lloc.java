/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;

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
    void afegirPuntInteres(PuntInteres pI){
        punts.add(pI);
    }
    
    /** @pre: --
        @post: Retorna un iterador a les estacions associades a lloc */
    Iterator<Estacio> obtenirEstacions(){
        return estacions.iterator();
    }
}
