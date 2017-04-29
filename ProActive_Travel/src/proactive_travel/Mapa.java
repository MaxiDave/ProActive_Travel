/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;

/**
 *
 * @author Roger
 */
public class Mapa {
    
    private Map<String, Lloc> llocs;
    private Map<String, PuntInteres> punts;
    private Map<String, Map<String, List<MTDirecte>>> transDirecte;
    
    /**
     * @pre: --
     * @post: Crea un mapa buit
     */
    public Mapa(){
        llocs= new HashMap<String, Lloc>();
        punts= new HashMap<String, PuntInteres>();
        transDirecte= new HashMap<String, Map<String, List<MTDirecte>>>();
    }
    
    /**
     * @pre: Lloc on està el punt d’interès ha d’existir
     * @post: Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pI){
        punts.put(pI.obtenirNom(), pI);
    }
    
    /**
     * @pre: --
     * @post: Si l'origen i el destí del mitjà de transport fegeix el Transport Directe mT al mapa. 
     */
    public void afegirTransportDirecte(MTDirecte mT){
        mT.getOrigen().obtenirNom();
        mT.getDesti().obtenirNom();
    }
    
    /**
     * @pre: --
     * @post: Afegeix un lloc al mapa, i també (si en té), els seus punts d’interès i les seves estacions. 
     * @param ll 
     */
    public void afegeixLloc(Lloc ll){
        llocs.put(ll.obtenirNom(), ll);
    }
    
    /**
     * @pre: --
     * @post: Si existeix IDlloc a claus de llocs i IDpI a claus de punts,
     *        associa el lloc secundari amb nom IDpI al lloc primari IDlloc
     */
    public void associarLloc(String IDlloc, String IDpI){
        Lloc primari= llocs.get(IDlloc);
        PuntInteres secundari= punts.get(IDpI);
        if(primari != null && secundari != null){
            secundari.vincularLloc(primari);
            primari.afegirPuntInteres(secundari);
        }
        else{
            //Excepció no existeix Lloc o PuntInteres
        }
    }
    
    /**
     * @pre: --
     * @post: Si existeix IDlloc a claus de llocs, associa el TransportUrba
     *        trans a la llista de transports urbans de lloc
     */
    public void associarUrba(String IDlloc, TransportUrba trans){
         Lloc ll= llocs.get(IDlloc);
         if(ll != null){
             ll.afegirTransportUrba(trans);
         }
         else{
             //Excepció no existeix Lloc
         }
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si existeix el punt d’interès
     */
    public Boolean existeixPuntInteres(PuntInteres pI){
        return punts.containsKey(pI.obtenirNom());
    }
    
    /**
     * @pre: --
     * @post: Retorna el Lloc associat amb l' identificador llocID.
     *        Si no existeix retorna null
     */
    public Lloc obtenirLloc(String llocID){
        return llocs.get(llocID);
    }
    
    /**
     * @pre: --
     * @post: Retorna el puntInteres associat amb l' identificador puntID.
     *        Si no existeix retorna null
     */
    public PuntInteres obtenirPI(String puntID){
        return punts.get(puntID);
    }
    
    /**
     * @pre: --
     * @post: Retorna el nombre de punts d’interès del mapa
     */
    public Integer nPuntsInteres(){
        return punts.size();
    }
    
    /**
     * @pre: --
     * @post: Afegeix a l'estació nomEst (Si no existeix la crea) al lloc de nom origenID 
     *        una connexió de sortida cap al lloc de nom destiID amb un temps d'oriden tempsOrigen
     */
    public void assignarOrigenMTI(String origenID, String destiID, String nomEst, Integer tempsOrigen){
        Lloc origen= llocs.get(origenID);
        Lloc desti= llocs.get(destiID);
         if(origen != null && desti != null){
             
         }
         else{
             //Excepció no existeix origen i/o desti
         }
    }
    
    /**
     * @pre: tipus == “temps” || tipus == “cost”   
     * @post: Retorna un Map amb els punts d’interès des d’on es pot anar a partir de pI i el seu Trajecte (El de mínim temps, mínima distància o mínim cost depenent de “tipus”)
     */
    public Map<PuntInteres,Trajecte> obtenirDesplsMin(PuntInteres pi,String tipus){
        Map<PuntInteres, Trajecte> noImplementat= null;
        return noImplementat;
    }
}
