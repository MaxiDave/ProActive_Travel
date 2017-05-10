/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.util.*;

/**
 *
 * @author Roger i David
 * Tipus de Dijkstra: Temps o Diners
 */
public class Dijkstra {
    private Set<PuntInteres> nodesAgafats;
    private Set<PuntInteres> nodesPerAgafar;

    private Map<PuntInteres,PuntInteres> predecessors;
    private Map<PuntInteres,Integer> temps;
    private Map<PuntInteres,Double> cost;
    
    private String tipus;
    
    Dijkstra() {
	nodesAgafats = new HashSet<PuntInteres>();
	nodesPerAgafar = new HashSet<PuntInteres>();
	temps = new HashMap<PuntInteres, Integer>();
        cost = new HashMap<PuntInteres, Double>();
	predecessors = new HashMap<PuntInteres, PuntInteres>();
        tipus = new String();
    }
    
    public Ruta camiMinim(Mapa mundi, PuntInteres origen, PuntInteres desti, String tipusDij){
        if(tipusDij.equals("temps")) tipus="temps";
        else tipus="diners";
        
	temps.put(origen,0);
	nodesPerAgafar.add(origen);
	while(nodesPerAgafar.size()>0 && !(nodesAgafats.contains(desti))){
		PuntInteres pi = buscarMinim(origen,nodesPerAgafar,mundi);
		nodesAgafats.add(pi);
		nodesPerAgafar.remove(pi);
                if(tipus.equals("temps")) buscarDistanciesMinimes(mundi,pi);
                else buscarCostsMinims(mundi,pi);
	}
        if(!nodesAgafats.contains(desti)){
            return null;
        }
        else{
            //Crear ruta?
            throw new UnsupportedOperationException("Not supported yet"); 
        }
    }
    
    private PuntInteres buscarMinim(PuntInteres origen, Set<PuntInteres> nodesPerAgafar, Mapa mundi){
	PuntInteres minim = null;
	for(PuntInteres p : nodesPerAgafar){
            if(minim==null){
		minim=p;
            }
            else{
                if(tipus.equals("temps")){
                    if(mundi.obtenirDespl(origen, p) < mundi.obtenirDespl(origen, minim)){ //Obtenir despl
                        minim=p;
                    }
                }
                else{
                    if(mundi.obtenirCostDespl(origen, p) < mundi.obtenirCostDespl(origen, minim)){ //Obtenir despl
                        minim=p;
                    }
                }       
            }
        }
        return minim;
    }
        
    private void buscarDistanciesMinimes(Mapa mundi, PuntInteres pi){
	Set<PuntInteres> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
	for(PuntInteres p : nodesVeins){
            if(obtenirTemps(p) > obtenirTemps(pi) + mundi.obtenirDespl(pi,p)){
		temps.put(p, obtenirTemps(pi) + mundi.obtenirDespl(pi,p));
		predecessors.put(p,pi);
		nodesPerAgafar.add(p);
            }
	}
    }
    
    private void buscarCostsMinims(Mapa mundi, PuntInteres pi){
	Set<PuntInteres> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
	for(PuntInteres p : nodesVeins){
            if(obtenirTemps(p) > obtenirCost(pi) + mundi.obtenirCostDespl(pi,p)){
		cost.put(p, obtenirCost(pi) + mundi.obtenirCostDespl(pi,p));
		predecessors.put(p,pi);
		nodesPerAgafar.add(p);
            }
	}
    }
    
    private int obtenirTemps(PuntInteres pi){
	Integer t = temps.get(pi);
	if(t==null){
		return Integer.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
    }
    
    private Double obtenirCost(PuntInteres pi){
	Double t = cost.get(pi);
	if(t==null){
		return Double.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
    }
}
