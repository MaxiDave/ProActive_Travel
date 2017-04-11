/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;
/**
 *
 * @author roger i david
 */
public class GrupClients {
    
    /** @pre: --
     *  @post: Es crea un conjunt de clients amb tants clients com té “clients”, categoria desitjada i punts d’interès a visitar
     * @param clients
     * @param catDesit
     * @param pl
     * @param origen
     * @param desti
     * @param inici
     * @param duracioMax 
     */
    public GrupClients(Collection<Client> clients, Integer catDesit, Collection<PuntInteres> pl,Lloc origen,Lloc desti,Data inici,Double duracioMax){
        
    }
    
    /** @pre: --
     *  @post: Retorna un enter que representa el nombre de clients del conjunt que tenen la preferència “pref” entre les seves preferències personals
     * @param pref
     */
    public Integer obtenirSatisfaccioPreferencia(String pref){
        
    }
    
    /** @pre: --
     *  @post: Retorna cert si l’allotjament “hotel” és de la categoria que desitja el conjunt de clients
     * @param hotel
     * @return 
     */
    public Boolean categoriaDesitjada(Allotjament hotel){
        
    }
    
    /** @pre: --
     *  @post: Retorna el lloc d’origen del viatge del grup de clients
     * @return 
     */
    public Lloc obtenirOrigen(){
        
    }
    
    /** @pre: --
     *  @post: Retorna el lloc de destí del viatge del grup de clients
     * @return 
     */
    public Lloc obtenirDesti(){
        
    }
    
    /** @pre: --
     *  @post: Retorna la data (amb hora inclosa) de sortida del grup de clients
     * @return 
     */
    public Data obtenirInici(){
        
    }
    
    /** @pre: --
     *  @post: Retorna un iterador als punts d’interès prefixats que s’han de visitar sí o sí
     * @return 
     */
    Iterator<PuntInteres> obtenirInteressos(){
        
    }
}
