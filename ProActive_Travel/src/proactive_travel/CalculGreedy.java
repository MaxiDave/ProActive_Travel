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
    private static void inicialitzarAtributs(Viatge clients){
        puntsIntermig = clients.obtenirInteressos();
        puntsIntermig.remove(clients.obtOrigen());
        puntsIntermig.remove(clients.obtDesti());
        actual = clients.obtDataInici();
        nCli = clients.nClients();
        finalViatge = clients.obtDataMax();
        origen = clients.obtOrigen();
        desti = clients.obtDesti();
        visitats = new HashSet<PuntInteres>();
    }
    
    /** 
     * @pre --
     * @post Metode de Debug que t'ensenya el valor dels atributs locals i de alguns atributs de Viatge
     * @brief Mostra els atributs locals i de Viatge
     */
    private static void debugAtributs(Viatge clients){
        System.out.println("La data actual es:");
        System.out.println(actual);
        System.out.println("El numero de clients es:");
        System.out.println(nCli);
        System.out.println("El final del viatge es:");
        System.out.println(finalViatge);
        System.out.println("L'origen es:");
        System.out.println(origen);
        System.out.println("El desti es:");
        System.out.println(desti);
        System.out.println("Durada segons clients");
        System.out.println(clients.obtDurada());
        System.out.println("Final segons clients");
        System.out.println(clients.obtDataMax());
        System.out.println("Els visitats son");
        for(PuntInteres p : visitats){
            System.out.println(p.obtNom());
        }
        System.out.println("Els punts intermig son");
        for(PuntInteres p : puntsIntermig){
            System.out.println(p.obtNom());
        }
    }
    
    /** 
     * @pre Viatge i Mapa han de ser valids
     * @post Calcula les rutes donades per viatge i les desa en un fitxer .txt i en un fitxer .kml
     */
    public static List<Ruta> calcularRutaGreedy(Viatge clients,Mapa mundi){
        Ruta r = new Ruta("barata",actual);
        List<Ruta> ll = new ArrayList<Ruta>();
        if(clients.esBarata()){
            inicialitzarAtributs(clients);
            Ruta barata = calcularBarat(mundi, clients.obtOrigen(), clients.preferenciesClients());
            r = barata;
            System.out.println(barata);
            ll.add(barata);
            sortidaKML.generarFitxer(r);
        }
        else{
            ll.add(null);
        }
        if(clients.esCurta()){
            inicialitzarAtributs(clients);
            Ruta temps = calcularRapid(mundi, clients.obtOrigen(), clients.preferenciesClients());
            r = temps;
            System.out.println(temps);
            ll.add(temps);
            sortidaKML.generarFitxer(r);
        }
        else{
            ll.add(null);
        }
        if(clients.esSatisfactoria()){
            inicialitzarAtributs(clients);
            Ruta satis = calcularSatisfactoria(mundi, clients.obtOrigen(), clients.preferenciesClients());
            r = satis;
            System.out.println(satis);
            ll.add(satis);
            sortidaKML.generarFitxer(r);
        }
        else{
            ll.add(null);
        }
        Sortida.mostrarRutes(ll, clients, "Resultat.txt");
        return ll;
    }
    
    /** 
     * @pre --
     * @post Retorna la ruta mes barata trobada per estrategia voraç
     */
    private static Ruta calcularBarat(Mapa mundi, PuntInteres origen, Map<String, Integer> preferenciesClients){
        Boolean fi=false;
        Boolean temps=true;
        PuntInteres puntAct = origen;
        Ruta barata = new Ruta("barata",actual);
        //Tractament origen
        ArrayDeque<PuntInteres> preparacio = new ArrayDeque<PuntInteres>();
        Map<PuntInteres, MitjaTransport> MT1 = null;
        preparacio.add(origen);
        analitzarLlocs(preparacio, barata, preferenciesClients, mundi, MT1, puntAct);
        
        while (!fi && temps) {
            Dijkstra d = seleccionarMesViable(mundi, "diners", puntAct);
            ArrayDeque<PuntInteres> cami = d.retornaPuntsInteres();
            Map<PuntInteres, MitjaTransport> camiMT = d.retornaMitjans();
            puntAct = analitzarLlocs(cami, barata, preferenciesClients, mundi, camiMT,puntAct); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            temps = comprovarTemps();
            fi = comprovarFi();
        }
        //Tractar desti
        Dijkstra d = new Dijkstra();
        d.camiMinim(mundi, puntAct, desti, "diners");
        Map<PuntInteres, MitjaTransport> MT2 = d.retornaMitjans();
        ArrayDeque<PuntInteres> ending = d.retornaPuntsInteres();
        analitzarLlocs(ending,barata,preferenciesClients,mundi, MT2, puntAct);
        return barata;
    }
    
    /** 
     * @pre --
     * @post Retorna la ruta mes rapida trobada per estrategia voraç
     */
    private static Ruta calcularRapid(Mapa mundi, PuntInteres origen, Map<String, Integer> preferenciesClients){
        Boolean fi=false;
        Boolean temps=true;
        PuntInteres puntAct = origen;
        Ruta rapida = new Ruta("curta",actual);
        //Tractament origen
        ArrayDeque<PuntInteres> preparacio = new ArrayDeque<PuntInteres>();
        preparacio.add(origen);
        Map<PuntInteres,MitjaTransport> MT1 = null;
        analitzarLlocs(preparacio, rapida, preferenciesClients, mundi, MT1, puntAct);
        
        while (!fi && temps) {
            Dijkstra d = seleccionarMesViable(mundi, "temps", puntAct);
            ArrayDeque<PuntInteres> cami = d.retornaPuntsInteres();
            Map<PuntInteres, MitjaTransport> camiMT = d.retornaMitjans();
            puntAct = analitzarLlocs(cami, rapida, preferenciesClients, mundi,camiMT, puntAct); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            temps = comprovarTemps();
            fi = comprovarFi();
        }
        //Tractar desti <<------------------------------------------------------ FALTA
        Dijkstra d = new Dijkstra();
        d.camiMinim(mundi, puntAct, desti, "temps");
        ArrayDeque<PuntInteres> ending = d.retornaPuntsInteres();
        Map<PuntInteres,MitjaTransport> MT2 = d.retornaMitjans();
        analitzarLlocs(ending,rapida,preferenciesClients,mundi, MT2, puntAct);
        return rapida;
    }
    
    /** 
     * @pre --
     * @post Retorna el PuntInteres mes proper/barat/satisfactori per a poder visitar de la llista dels obligatoris
     */
    private static Dijkstra seleccionarMesViable(Mapa mundi, String tipus, PuntInteres puntAct) {
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
        return definitiu;
    }

    /** 
     * @pre --
     * @post Analitza tots els punts per on es passa i va construint la ruta, tambe controla la hora i dia i retorna el Punt de Interes on s'ha quedat
     */
    private static PuntInteres analitzarLlocs(ArrayDeque<PuntInteres> cami, Ruta barata, Map<String, Integer> preferenciesClients, Mapa mundi, Map<PuntInteres, MitjaTransport> camiMT, PuntInteres puntAct) {
        PuntInteres act = null;
        PuntInteres ant = puntAct;
        for(PuntInteres p : cami){ 
            if(camiMT!=null && camiMT.get(p)!=null && p!=ant){
                
                if(camiMT.get(p) instanceof MTIndirecte){
                    
                }
                else{
                     
                    MitjaTransport mtu = camiMT.get(p);
                    MTDirecte cast = new MTDirecte(mtu.getNom(),ant,p,mtu.getPreu(),mtu.getDurada());
                    TrajecteDirecte td = new TrajecteDirecte(cast,actual);
                    barata.afegeixItemRuta(td);
                    actual=td.obtFinal();
                }
            }
            
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
                if(actual.toLocalTime().compareTo(((PuntVisitable) p).obtTancament())>0){
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
            ant=p;
            comprovarTemps(); // Mirar si anar a buscar un hotel
        }
        return act;
    }

    /** 
     * @pre --
     * @post Busca l'hotel mes proper avança un dia i escriu la ruta d'anada i de tornada
     */
    private static void buscarHotel(Mapa mundi, Ruta barata, PuntInteres p, Map<String, Integer> preferenciesClients) {
        Dijkstra d=mundi.obtenirHotelProper(p, "diners");
        ArrayDeque<PuntInteres> camiHotel = d.retornaPuntsInteres();
        Map<PuntInteres,MitjaTransport> mitjansHotel= d.retornaMitjans();
        PuntInteres act = null;
        PuntInteres ant = p;
        for (PuntInteres p2 : camiHotel) {
            //Anar afegint a ruta el trajecte <<-----------------------------------------------FALTA
            if (p2 != ant) {
                MitjaTransport mt = mitjansHotel.get(p2);
                MTDirecte md = new MTDirecte(mt.getNom(), ant, p2, mt.getPreu(), mt.getDurada());
                TrajecteDirecte td = new TrajecteDirecte(md, actual);
                barata.afegeixItemRuta(td);
                actual=td.obtFinal();
            }
            if (p2 instanceof Allotjament) {
                Integer satis = p2.grauSatisfaccio(preferenciesClients);
                EstadaHotel e = new EstadaHotel(((Allotjament) p2), actual, satis);
                barata.afegeixItemRuta(e);
                actual = e.obtFinal();
                act = p2;
            }
            ant=p2;
        }
        while(actual.getHour()<4){
            if(actual.getHour()<3){
                actual=actual.plusHours(1);
            }
            else{
                actual=actual.plusMinutes(1);
            }
        }
        
        Dijkstra d2 = new Dijkstra();
        d2.camiMinim(mundi, act, p, "diners");
        ArrayDeque<PuntInteres> camiTornada = d2.retornaPuntsInteres();
        Map<PuntInteres,MitjaTransport> mitjansHotel2= d2.retornaMitjans();
        for (PuntInteres pi3 : camiTornada) {
            //Anar afegint a ruta el trajecte <<-----------------------------------------------FALTA
            if (pi3 != ant) {
                MitjaTransport mt = mitjansHotel2.get(pi3);
                MTDirecte md = new MTDirecte(mt.getNom(), ant, pi3, mt.getPreu(), mt.getDurada());
                TrajecteDirecte td = new TrajecteDirecte(md, actual);
                barata.afegeixItemRuta(td);
            }
            
            if (pi3.equals(p)) {
                while (actual.toLocalTime().compareTo(((PuntVisitable) p).obtObertura()) < 0) {
                    actual=actual.plusMinutes(1);
                }
                Visita v = new Visita(((PuntVisitable) p), actual, p.grauSatisfaccio(preferenciesClients));
                barata.afegeixItemRuta(v);
                actual = v.obtFinal();
                puntsIntermig.remove(p);
            }
        }
    }
    
    /** 
     * @pre --
     * @post Retorna la ruta mes satisfactoria trobada per estrategia voraç
     */
    private static Ruta calcularSatisfactoria(Mapa mundi, PuntInteres origen, Map<String,Integer> preferenciesClients){
        Boolean fi=false;
        Boolean temps=true;
        PuntInteres puntAct = origen;
        Ruta rapida = new Ruta("satisfactoria",actual);
        //Tractament origen
        ArrayDeque<PuntInteres> preparacio = new ArrayDeque<PuntInteres>();
        Map<PuntInteres, MitjaTransport> MT1 = null;
        preparacio.add(origen);
        analitzarLlocs(preparacio, rapida, preferenciesClients, mundi, MT1, puntAct);
        
        while (!fi && temps) {
            Dijkstra d = seleccionarMesViable(mundi, "temps", puntAct);
            ArrayDeque<PuntInteres> cami = d.retornaPuntsInteres();
            Map<PuntInteres, MitjaTransport> camiMT = d.retornaMitjans();
            puntAct = analitzarLlocs(cami, rapida, preferenciesClients, mundi, camiMT, puntAct); //Mirar si valen la pena per visitar i anar mirant la hora del dia ja que s'ha de anar a hotels
            temps = comprovarTemps();
            fi = comprovarFi();
        }
        //Tractar desti <<------------------------------------------------------ FALTA
        Dijkstra d = new Dijkstra();
        d.camiMinim(mundi, puntAct, desti, "temps");
        ArrayDeque<PuntInteres> ending = d.retornaPuntsInteres();
        Map<PuntInteres, MitjaTransport> MT2 = d.retornaMitjans();
        analitzarLlocs(ending,rapida,preferenciesClients,mundi, MT2, puntAct);
        return rapida;
    }
    
    /** 
     * @pre --
     * @post Retorna si final de viatge, fals altrement
     */
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
    
    /** 
     * @pre --
     * @post Retorna true si s'han acabat els punts intermig
     */
    private static Boolean comprovarFi(){
        return puntsIntermig.isEmpty();
    }
}
