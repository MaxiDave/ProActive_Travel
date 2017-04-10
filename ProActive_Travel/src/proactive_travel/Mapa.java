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
    
    /**
     * @pre: --
     * @post: Crea un mapa buit
     */
    public Mapa(){
        
    }
    
    /**
     * @param pl
     * @pre: Lloc on està el punt d’interès ha d’existir
     * @post: Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pl){
        
    }
    
    /**
     * @param traj
     * @pre: --
     * @post: Afegeix un desplaçament al mapa entre dos llocs o entre dos punts d’interès a partir d’un trajecte entre ells
     */
    public void afegeixTrajecte(Trajecte traj){
        
    }
    
    /**
     * @pre: --
     * @post: Afegeix un lloc al mapa, i també (si en té), els seus punts d’interès i les seves estacions. 
     * @param ll 
     */
    public void afegeixLloc(Lloc ll){
        
    }
    
    /**
     * @pre: --
     * @post: Retorna cert si existeix el punt d’interès
     * @param pi
     * @return 
     */
    public Boolean existeixPuntInteres(PuntInteres pi){
        
    }
    
    /**
     * @return 
     * @pre: --
     * @post: Retorna el nombre de punts d’interès del mapa
     */
    public Integer nPuntsInteres(){
        
    }
    
    /**
     * @param pi
     * @param tipus
     * @return 
     * @pre: tipus == “temps” || tipus == “dist” || tipus == “cost”   
     * @post: Retorna un Map amb els punts d’interès des d’on es pot anar a partir de pI i el seu Trajecte (El de mínim temps, mínima distància o mínim cost depenent de “tipus”)
     */
    public Map<PuntInteres,Trajecte> obtenirDesplsMin(PuntInteres pi,String tipus){
        
    }
}
