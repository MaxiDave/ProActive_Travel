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

import java.time.*;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs aproximats
 */
public abstract class CalculGreedy {
    
    private static Set<PuntInteres> puntsIntermig;
    private static LocalDateTime actual;
    private static Integer nCli;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Calcula una Ruta mitjançant un algorisme voraç
     */
    public static Ruta calcularRutaGreedy(Viatge clients,Mapa mundi){
        puntsIntermig = clients.obtenirInteressos();
        actual = clients.obtDataInici();
        nCli = clients.nClients();
        
        if(clients.esBarata()){
            Ruta barata = calcularBarat(mundi,clients.obtOrigen(),clients.preferenciesClients());
        }
        if(clients.esCurta()){
            
        }
        if(clients.esSatisfactoria()){
            
        }
    }
    
    private static Ruta calcularBarat(Mapa mundi, PuntInteres origen, Map<String, Integer> preferenciesClients){
        Boolean fi=false;
        Boolean temps=true;
        Boolean fiCami=false;
        PuntInteres puntAct = origen;
        Ruta barata = new Ruta();
        while(!fi && temps){
            Set<PuntInteres> cami = seleccionarMesViable(mundi,"diners",puntAct);
            fiCami=false;
            while(!fiCami){
                analitzarLlocs(cami,barata,preferenciesClients); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
                temps = comprovarTemps();
                fi = comprovarFi();
                fiCami = comprovarFiCami();
            }
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

    private static void analitzarLlocs(Set<PuntInteres> cami, Ruta barata, Map<String, Integer> preferenciesClients) {
        for(PuntInteres p : cami){
            if(p.satisfaPreferencia(preferenciesClients) > nCli/3 && p instanceof PuntVisitable){
                if(((PuntVisitable) p).estaObert(actual.toLocalTime())){
                    /*ItemRuta visi= new ItemRuta();
                    Visita v = new Visita(((PuntVisitable) p));
                    visi.afegirVisita(v);*/
                }
            }
        }
    }

    private static Boolean comprovarFiCami() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
