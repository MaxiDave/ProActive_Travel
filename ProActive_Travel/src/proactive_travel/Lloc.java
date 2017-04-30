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
    private Map<String, Estacio> estacions;
    private Set<PuntInteres> punts;
    private Set<TransportUrba> transports;
    
    /** @pre: --
        @post:  Es crea un lloc de nom "n" i coordeandes "coor" */ 
    public Lloc(String n, Coordenades coor){
        nom= n;
        c= coor;
        punts= new HashSet<PuntInteres>();
        estacions= new HashMap<String, Estacio>();
        transports= new HashSet<TransportUrba>();
    }
    
    /** @pre: --
        @post: Retorna el nom del lloc */
    public String obtenirNom(){
        return nom;
    }
    
    /** @pre: --
        @post: Retorna les coordenades del lloc */
    public Coordenades obtenirCoordenades(){
        return c;
    }
    
    /** @pre: --
        @post: Afegeix l'estació "est" a la llista d'estacions del lloc*/
    private void afegirEstacio(Estacio est){
        estacions.put(est.getNom(), est);
    }
    
    /** @pre: --
     *  @post: Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una sortida a l'estació de nom nomEst cap al lloc desti
     *         amb un temps d'origen de "temps"
     */
    public void afegirConnexioSortidaMTI(String nomEst, Lloc desti, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioSortida(desti, temps);
    }
    
    /** @pre: --
     *  @post: Si l'estació de nom nomEst no existeix, la crea.
     *         Afegeix una arribada a l'estació de nom nomEst des del lloc origen
     *         amb un temps de destí de "temps"
     */
    public void afegirConnexioArribadaMTI(String nomEst, Lloc origen, Integer temps){
        if(!estacions.containsKey(nomEst)) estacions.put(nomEst, new Estacio(nomEst));
        Estacio actual= estacions.get(nomEst);
        actual.afegirConnexioArribada(origen, temps);
    }
    
    /** @pre: Ha d'existir una connexió de Sortida a l'estació del mitjà "mitja"
     *  @post: S'afegeix el mitjà de transport indirecte com a sortida de l'estació corresponent
     */
    public void afegirSortidaMTI(MTIndirecte mitja, LocalDateTime horaSortida){
        Estacio actual= estacions.get(mitja.getNom());
        actual.afegirSortida(mitja, horaSortida);
    }
    
    /** @pre: --
        @post: Afegeix el punt d'interès "pI" als punts d'interès del lloc */
    public void afegirPuntInteres(PuntInteres pI){
        punts.add(pI);
    }
    
    /** @pre: --
        @post: Afegeix el transport urbà al Lloc */
    public void afegirTransportUrba(TransportUrba tU){
        transports.add(tU);
    } 
}
