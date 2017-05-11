//ProActive_Travel

/**
 * @file: CalculGreedy.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en trobar
 *         una bona solució (Greedy) de Ruta, en termes monetaris, temps i Satisfacció 
 * @copyright: Public License
 */

package proactive_travel;

import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs aproximats
 */
public abstract class CalculGreedy {
    
    private static Set<PuntInteres> puntsIntermig;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Calcula una Ruta mitjançant un algorisme voraç
     */
    public static Ruta calcularRutaGreedy(Viatge clients,Mapa mundi){
        puntsIntermig = clients.obtenirInteressos();
        if(clients.esBarat()){
            Ruta barata = calcularBarat(mundi,clients.obtOrigen());
        }
        if(clients.esCurt()){
            
        }
        if(clients.esSatisfactoria()){
            
        }
    }
    
    private static Ruta calcularBarat(Mapa mundi, PuntInteres origen){
        Boolean fi=false;
        Boolean temps=true;
        PuntInteres puntAct = origen;
        while(!fi && temps){
            Set<PuntInteres> cami = seleccionarMesViable(mundi,"diners",puntAct);
            analitzarLlocs(); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            temps = comprovarTemps();
            fi = comprovarFi();
        }
    }

    private static Set<PuntInteres> seleccionarMesViable(Mapa mundi, String tipus, PuntInteres puntAct) {
        Dijkstra d = new Dijkstra();
        Dijkstra definitiu = new Dijkstra();
        
        int temps=Integer.MAX_VALUE;
        int satisfaccio=Integer.MAX_VALUE;
        double cost=Double.MAX_VALUE;
        
        for(PuntInteres p : puntsIntermig){
            d.camiMinim(mundi, puntAct, p, tipus);
            if(tipus.equals("diners") && cost>d.retornaCost()){
                definitiu=d;
            }
            else if(tipus.equals("temps") && temps>d.retornaTemps()){
                definitiu=d;
            }
            else{
                throw new UnsupportedOperationException("Not supported yet");
            }
        }
        Set<PuntInteres> millorCami = definitiu.retornaPuntsInteres();
        return millorCami;
    }

    private static void analitzarLlocs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Ruta calcularCurt(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    private Ruta calcularSatisfactoria(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    private static Boolean comprovarTemps(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    private static Boolean comprovarFi(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
