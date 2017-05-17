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
    private static PuntInteres desti;
    private static PuntInteres origen;
    private static Set<PuntInteres> visitats;
    
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
        origen = clients.obtOrigen();
        desti = clients.obtDesti();
        visitats = new HashSet<PuntInteres>();
        
        Ruta r = new Ruta("barata",actual);
        
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
        Ruta barata = new Ruta("barata",actual);
        //Tractament origen
        Set<PuntInteres> preparacio = new HashSet<PuntInteres>();
        preparacio.add(origen);
        analitzarLlocs(preparacio, barata, preferenciesClients, mundi);
        
        while (!fi && temps) {
            Set<PuntInteres> cami = seleccionarMesViable(mundi, "diners", puntAct);
            puntAct = analitzarLlocs(cami, barata, preferenciesClients, mundi); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            temps = comprovarTemps();
            fi = comprovarFi();
            System.out.println("-----------------------------------------------------------");
            System.out.println(puntAct.obtenirNom());
            System.out.println("-----------------------------------------------------------");
        }
        //Tractar desti <<------------------------------------------------------ FALTA
        System.out.println(puntAct.obtenirNom());
        Dijkstra d = new Dijkstra();
        d.camiMinim(mundi, puntAct, desti, "diners");
        Set<PuntInteres> ending = d.retornaPuntsInteres();
        analitzarLlocs(ending,barata,preferenciesClients,mundi);
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
        HashSet<PuntInteres> millorCami = definitiu.retornaPuntsInteres();
        return millorCami;
    }

    private static PuntInteres analitzarLlocs(Set<PuntInteres> cami, Ruta barata, Map<String, Integer> preferenciesClients, Mapa mundi) {
        PuntInteres act = null;
        for(PuntInteres p : cami){
            if(p.grauSatisfaccio(preferenciesClients) > nCli/3 && p instanceof PuntVisitable && !puntsIntermig.contains(p) && desti!=p && origen!=p && !visitats.contains(p)){
                if(((PuntVisitable) p).estaObert(actual.toLocalTime())){
                    Visita v = new Visita(((PuntVisitable) p),actual,p.grauSatisfaccio(preferenciesClients));
                    barata.afegeixItemRuta(v);
                    actual=v.obtFinal();
                    visitats.add(p);
                    if(puntsIntermig.contains(p)){
                        puntsIntermig.remove(p);
                    }
                }
            }
            else if(!visitats.contains(p) && (puntsIntermig.contains(p) || desti.equals(p) || origen.equals(p))){
                while(actual.toLocalTime().compareTo(((PuntVisitable) p).obtObertura())<0){
                    actual=actual.plusMinutes(1);
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
                visitats.add(p);
                act=p;
            }
            comprovarTemps(); // Mirar si anar a buscar un hotel
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
