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
    private static LocalDateTime finalViatge;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Calcula una Ruta mitjançant un algorisme voraç
     */
    public static Ruta calcularRutaGreedy(Viatge clients,Mapa mundi){
        puntsIntermig = clients.obtenirInteressos();
        puntsIntermig.remove(clients.obtOrigen());
        puntsIntermig.remove(clients.obtDesti());
        actual = clients.obtDataInici();
        nCli = clients.nClients();
        finalViatge = clients.obtDataInici();
        finalViatge.plusDays(clients.obtDurada());
        
        Ruta r = new Ruta(actual);
        
        if(clients.esBarata()){
            Ruta barata = calcularBarat(mundi,clients.obtOrigen(),clients.preferenciesClients());
            r=barata;
        }
        if(clients.esCurta()){
            
        }
        if(clients.esSatisfactoria()){
            
        }
        return r;
    }
    
    private static Ruta calcularBarat(Mapa mundi, PuntInteres origen, Map<String, Integer> preferenciesClients){
        Boolean fi=false;
        Boolean temps=true;
        PuntInteres puntAct = origen;
        Ruta barata = new Ruta(actual);
        while (!fi && temps) {
            Set<PuntInteres> cami = seleccionarMesViable(mundi, "diners", puntAct);
            System.out.println("A");
            puntAct = analitzarLlocs(cami, barata, preferenciesClients, mundi); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            System.out.println("B");
            temps = comprovarTemps();
            fi = comprovarFi();
        }
        //Tractar desti <<------------------------------------------------------ FALTA
        return barata;
    }
    
    private static Set<PuntInteres> seleccionarMesViable(Mapa mundi, String tipus, PuntInteres puntAct) {
        Dijkstra d = new Dijkstra();
        Dijkstra definitiu = new Dijkstra();
        
        int temps=Integer.MAX_VALUE;
        int satisfaccio=Integer.MAX_VALUE;
        double cost=Double.MAX_VALUE;
        
        for(PuntInteres p : puntsIntermig){
            int z=d.camiMinim(mundi, puntAct, p, tipus);
            if(z==-1){
                throw new UnsupportedOperationException("No hi ha ruta");
            }
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
        System.out.println("PuntsIntermig perfecte (CalculGreedy)");
        Set<PuntInteres> millorCami = definitiu.retornaPuntsInteres();
        System.out.println("No pot fallar aqui");
        return millorCami;
    }

    private static PuntInteres analitzarLlocs(Set<PuntInteres> cami, Ruta barata, Map<String, Integer> preferenciesClients, Mapa mundi) {
        PuntInteres act = null;
        for(PuntInteres p : cami){
            if(p.grauSatisfaccio(preferenciesClients) > nCli/3 && p instanceof PuntVisitable){
                if(((PuntVisitable) p).estaObert(actual.toLocalTime())){
                    Visita v = new Visita(((PuntVisitable) p),actual,p.grauSatisfaccio(preferenciesClients));
                    barata.afegeixItemRuta(v);
                    actual=v.obtFinal();
                    if(puntsIntermig.contains(p)){
                        puntsIntermig.remove(p);
                    }
                }
            }
            else if(puntsIntermig.contains(p)){
                while(actual.toLocalTime().compareTo(((PuntVisitable) p).obtObertura())<0){
                    actual.plusMinutes(1);
                }
                if(actual.toLocalTime().compareTo(((PuntVisitable) p).obtObertura())>0){
                    buscarHotel(mundi,barata,p,preferenciesClients);
                }
                else{
                    Visita v = new Visita(((PuntVisitable) p),actual,p.grauSatisfaccio(preferenciesClients));
                    barata.afegeixItemRuta(v);
                    actual=v.obtFinal();
                    puntsIntermig.remove(p);
                }
            }
            comprovarTemps(); // Mirar si anar a buscar un hotel
            act=p;
        }
        return act;
    }

    private static void buscarHotel(Mapa mundi, Ruta barata, PuntInteres p, Map<String, Integer> preferenciesClients) {
        Set<PuntInteres> camiHotel = mundi.obtenirHotelProper(p, "diners");
        PuntInteres act = null;
        for (PuntInteres p2 : camiHotel) {
            //Anar afegint a ruta el trajecte <<-----------------------------------------------FALTA
            if (p2 instanceof Allotjament) {
                Integer satis = p2.grauSatisfaccio(preferenciesClients);
                EstadaHotel e = new EstadaHotel(((Allotjament) p2), actual, satis);
                barata.afegeixItemRuta(e);
                actual = e.obtFinal();
                act = p2;
            }
        }
        Dijkstra d2 = new Dijkstra();
        d2.camiMinim(mundi, act, p, "diners");

        Set<PuntInteres> camiTornada = d2.retornaPuntsInteres();
        for (PuntInteres pi3 : camiTornada) {
            //Anar afegint a ruta el trajecte <<-----------------------------------------------FALTA
            if (pi3.equals(p)) {
                while (actual.toLocalTime().compareTo(((PuntVisitable) p).obtObertura()) < 0) {
                    actual.plusMinutes(1);
                }
                Visita v = new Visita(((PuntVisitable) p), actual, p.grauSatisfaccio(preferenciesClients));
                barata.afegeixItemRuta(v);
                actual = v.obtFinal();
                puntsIntermig.remove(p);
            }
        }
    }
    
    private Ruta calcularCurt(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    private Ruta calcularSatisfactoria(){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    private static Boolean comprovarTemps(){
        boolean temps;
        if(actual.compareTo(finalViatge)<0){
            temps=true;
        }
        else{
            temps=false;
        }
        return temps;
    }
    
    private static Boolean comprovarFi(){
        return puntsIntermig.isEmpty();
    }
}
