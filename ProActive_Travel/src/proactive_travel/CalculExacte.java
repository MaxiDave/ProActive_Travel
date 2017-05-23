//ProActive_Travel

/**
 * @file CalculExacte.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 6
 * @date Curs 2016-2017
 * @brief Classe CalculExacte: S'encarrega de dur a terme els càlculs relacionats en trobar
 *         les millors solucions (Backtraking) d'un viatge, en termes monetaris, temps i Satisfacció 
 * @copyright Public License
 */

//PACKAGES / IMPORTS NECESSARIS PER AL FUNCIONAMENT---------------------------------------------------------------------------------------------
package proactive_travel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DESCRIPCIÓ GENERAL
 * @brief   Classe que s'encarrega dels càlculs exactes
 * @details S'encarrega de dur a terme els càlculs relacionats en trobar
 *          les millors solucions (Backtraking) d'un viatge, en termes monetaris, temps i Satisfacció.
 *          Podria haver-se implementat com a mòdul funcional, però degut a problemes amb les classes 
 *          privades internes es va decidir fer-ho així.
 */
public class CalculExacte {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private Map<PuntInteres, Integer> satisfaccio; ///< @brief Conté informació del grau de satisfacció que proporciona cadascún dels punts d'interes respectives a aquest viatge
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Es crea un CalculExacte a partir del mapa "mundi" i del viatge
     * @brief Constructor
     */
    public CalculExacte(Mapa mundi, Viatge viatge){
        satisfaccio= new HashMap<>();
        Iterator<PuntInteres> it= mundi.obtIteradorPunts();
        while(it.hasNext()){
            PuntInteres pI= it.next();
            Integer satis= pI.grauSatisfaccio(viatge.obtMapSatisfaccio());
            satisfaccio.put(pI, satis);
        }
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre --
     * @post Calcula i retorna una llista de rutes a partir del mapa i viatge mitjançant un algorisme exacte de backtraking
     * @brief Retorna una llista de Rutes calculades
     */
    public List<Ruta> calcularRutaBack(Mapa mundi, Viatge viatge){
        List<Ruta> rutes= new ArrayList<>();
        for(int i=0; i<3; i++) rutes.add(i, null);
        if(viatge.esCurta()){
            Solucionador sol= new Solucionador(viatge, "curta", null);
            if(sol.inicialitzat()) rutes.set(0, sol.algBack(mundi, viatge));
        }
        if(viatge.esBarata()){
            Solucionador sol;
            if(rutes.get(0) != null){
                Ruta optima= new Ruta(rutes.get(0));
                optima.canviarTipus("barata");
                sol= new Solucionador(viatge, "barata", optima);
            }
            else sol= new Solucionador(viatge, "barata", null);
            if(sol.inicialitzat()) rutes.set(1, sol.algBack(mundi, viatge));
        }
        if(viatge.esSatisfactoria()){
            Solucionador sol= new Solucionador(viatge, "sat", null);
            if(sol.inicialitzat()) rutes.set(2, sol.algBack(mundi, viatge));
        }
        return rutes;
    }
    
    /**
    * DESCRIPCIÓ GENERAL
    * @brief   Classe que s'encarrega de dur a terme la base algorísmica del Backtraking
    * @details S'encarrega de dur a terme la part algorísmica relacionada en trobar
    *          les millors solucions (Backtraking) d'un viatge, en termes monetaris, temps i Satisfacció.
    */
    private class Solucionador{
        //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
        private Ruta optima; ///< @brief Conté la ruta més òptima en un moment concret de l'execució del algorisme
        private Solucio actual; ///< @brief Conté una solució que es va modificant a mesura que avança l'algorisme
        private final String tipusRuta; ///< @brief Conté el tipus de Ruta que s'està buscant, pot ser "curta", "barata" o "sat"
        
        //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
        /** 
         * @pre --
         * @post Es crea un Solucionador a partir d'un viatge, un tipus de Ruta a calcular i una ruta òptima (pot ser nula) que pot contenir una ruta
         *       calculada en anteriors algorismes de Backtraking per tal de fer més eficient el càlcul
         * @brief Constructor
         */
        private Solucionador(Viatge viatge, String tipus, Ruta optima){
            this.optima= optima;
            tipusRuta= tipus;
            actual= new Solucio(viatge.obtDataInici(), viatge);
            LocalTime iniciViatge= viatge.obtDataInici().toLocalTime();
            PuntInteres inici= viatge.obtOrigen();
            if(inici instanceof PuntVisitable){
                PuntVisitable pV= (PuntVisitable)inici;
                Visita visita;
                if(pV.obtObertura().isAfter(iniciViatge)){
                    Integer satis= satisfaccio.get(inici);
                    visita= new Visita(pV, LocalDateTime.of(viatge.obtDataInici().toLocalDate(), pV.obtObertura()), satis);
                    actual.afegirPrimerPunt(visita);
                }
                else if(iniciViatge.plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())){
                    Integer satis= satisfaccio.get(inici);
                    visita= new Visita(pV, viatge.obtDataInici(), satis);
                    actual.afegirPrimerPunt(visita);
                }
            }
            else{
                Allotjament hotel= (Allotjament)inici;
                Integer satis= satisfaccio.get(inici);
                EstadaHotel estada= new EstadaHotel(hotel, viatge.obtDataInici(), satis);
                actual.afegirPrimerPunt(estada);
            }
        }
        
        //MÈTODES PRIVATS----------------------------------------------------------------------------------------------------------------------------
        /** 
         * @pre --
         * @post Retorna cert si el solucionador s'ha inicialitzat correctament i es pot seguir calculant la ruta més òptima
         * @brief Està el solucionador inicialitzat?
         */
        private boolean inicialitzat(){
            return actual.obtRuta().teItems();
        }
        
        /** 
         * @pre --
         * @post Invoca el mètode de Backtraking recursiu trobar la solució més òptima a partir d'un mapa "mundi" i un viatge
         * @brief Mètode inicialitzador de la recursivitat
         */
        private Ruta algBack(Mapa mundi, Viatge viatge) {
            algRecursiu(mundi, viatge.obtOrigen(), viatge);
            if(optima != null) return optima;
            else return null;
        }
        
        /** 
         * @pre --
         * @post Fa els càlculs per a trobar la ruta més òptima de forma recursiva a partir del mapa "mundi", el PuntRuta actual, i el viatge
         * @brief Mètode algorísmic del Backtraking trobar solució més òptima
         */
        private void algRecursiu(Mapa mundi, PuntRuta act, Viatge viatge){
            Candidats iCan= actual.inicialitzarCandidats(mundi, act);
            while(!iCan.fi()){
                if(actual.acceptable(iCan, viatge) && esPotMillorar(actual.obtRuta(), optima)){
                    ItemRuta item= iCan.crearItem(actual);
                    actual.anotar(item, viatge);
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, item.obtPuntSortida(), viatge);
                    else if(esMillor(actual.obtRuta(), optima)) optima= new Ruta(actual.obtRuta());
                    actual.desanotar();
                }
                iCan.seguent();
            }
        }
        
        /** 
         * @pre --
         * @post Retorna cert si la ruta actual es pot millorar respecte la òptima
         * @brief Es pot millorar la ruta actual respecte l'optima?
         */
        private boolean esPotMillorar(Ruta actual, Ruta optima){
           if(optima == null) return true;
           else return esMillor(actual, optima);
        }
        
        /** 
         * @pre --
         * @post Retorna cert si la ruta actual és millor respecte la òptima
         * @brief És millor la ruta actual respecte l'optima?
         */
        private boolean esMillor(Ruta actual, Ruta optima){
            if(optima == null) return true;
            else if(tipusRuta.equals("curta")){
                Integer cmpD= actual.obtDurada()-optima.obtDurada();
                if(cmpD > 0) return false;
                else if(cmpD < 0) return true;
                else{
                    Integer cmpS= actual.obtSatisfaccio()-optima.obtSatisfaccio();
                    if(cmpS == 0) return actual.obtCost() < optima.obtCost();
                    else if(cmpS < 0) return false;
                    else return true;
                }
            }
            else if(tipusRuta.equals("barata")){
                Double cmpC= actual.obtCost()-optima.obtCost();
                if(cmpC > 0) return false;
                else if(cmpC < 0) return true;
                else{
                    Integer cmpS= actual.obtSatisfaccio()-optima.obtSatisfaccio();
                    if(cmpS == 0) return actual.obtDurada()<optima.obtDurada();
                    else if(cmpS < 0) return false;
                    else return true;
                }
            }
            else{
                Integer cmpS= actual.obtSatisfaccio()-optima.obtSatisfaccio();
                if(cmpS > 0) return true;
                else if(cmpS < 0) return false;
                else{
                    Double cmpC= actual.obtCost()-optima.obtCost();
                    if(cmpC == 0) return actual.obtDurada()<optima.obtDurada();
                    else if(cmpC < 0) return true;
                    else return false;
                }
            }
        }
        
        private class Solucio{
            private Ruta ruta;
            private Set<PuntInteres> visitats;
            private Map<PuntInteres, Boolean> puntsObligats;
            private Integer nObligatsVisitats;
            private Deque<Boolean> afegitPI;
            private MTDirecte anterior;
            private LocalDateTime sortidaMTIndirecte;
                      
            private Solucio(LocalDateTime temps, Viatge viatge){
                nObligatsVisitats= 0;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                anterior= null;
                sortidaMTIndirecte= null;
                afegitPI= new ArrayDeque<>();
                Iterator<PuntInteres> it= viatge.obtIteradorPI();
                while(it.hasNext()) puntsObligats.put(it.next(), Boolean.FALSE);
            }
            
            private Candidats inicialitzarCandidats(Mapa mundi, PuntRuta pI){
                ItemRuta ultimItem= ruta.obtUltimItem();
                if((ruta.teItems() && ultimItem instanceof TrajecteIndirecte)) return new Candidats(mundi, (Estacio)pI, ((TrajecteIndirecte) ultimItem).obtLlocOrigen());
                else return new Candidats(mundi, pI);
            }
            
            private void afegirPrimerPunt(ItemRuta item){
                ruta.afegeixItemRuta(item);
                if(item instanceof Visita){
                    PuntInteres pI= (PuntInteres)item.obtPuntSortida();
                    visitats.add(pI);
                }
            }
            
            private void anotar(ItemRuta item, Viatge viatge){
                if(item instanceof TrajecteDirecte || item instanceof TrajectePunts){
                    ruta.afegeixItemRuta(item);
                    PuntInteres aAnar= (PuntInteres)item.obtPuntSortida();
                    if(acceptable(aAnar, viatge)){
                        anterior= null;
                        Integer satis= satisfaccio.get(aAnar);
                        ItemRuta itemPunt;
                        if(aAnar instanceof PuntVisitable){
                            PuntVisitable pV= (PuntVisitable)aAnar;
                            if(ruta.obtFinal().toLocalTime().isAfter(pV.obtObertura())) itemPunt= new Visita(pV, ruta.obtFinal(), satis);
                            else itemPunt= new Visita(pV, LocalDateTime.of(ruta.obtFinal().toLocalDate(), pV.obtObertura()), satis);
                            //System.out.println("Anotem: "+visita);
                            visitats.add((PuntInteres)itemPunt.obtPuntSortida());
                            if(puntsObligats.containsKey((PuntInteres)itemPunt.obtPuntSortida())){
                                puntsObligats.replace((PuntInteres)itemPunt.obtPuntSortida(), Boolean.TRUE);
                                nObligatsVisitats++;
                            }
                        }
                        else{
                            Allotjament hotel= (Allotjament)aAnar;
                            itemPunt= new EstadaHotel(hotel, ruta.obtFinal() ,satis);
                        }
                        ruta.afegeixItemRuta(itemPunt);
                        afegitPI.addLast(Boolean.TRUE);
                    }
                    else if(anterior == null){
                        TrajecteDirecte tD= (TrajecteDirecte)item;
                        anterior= tD.obtMitja();
                        afegitPI.addLast(Boolean.FALSE);
                    }
                    else afegitPI.addLast(Boolean.FALSE);
                }
                else if(item instanceof EstadaHotel || item instanceof TrajecteEstacio || item instanceof TrajecteIndirecte){
                    anterior= null;
                    ruta.afegeixItemRuta(item);
                    afegitPI.addLast(Boolean.FALSE);
                }
                else afegitPI.addLast(Boolean.FALSE);
            }
            
            private void desanotar(){
                anterior= null;
                //System.out.println("Desanotem: "+item);
                Boolean afegirAnteriorPI= afegitPI.pollLast();
                if(afegirAnteriorPI){
                    //System.out.println("S'havia afegit PI, es treu");
                    ItemRuta item= ruta.treureUltimItem();
                    if(item instanceof Visita){
                        visitats.remove((PuntInteres)item.obtPuntSortida());
                        if(puntsObligats.containsKey((PuntInteres)item.obtPuntSortida())){
                            puntsObligats.replace((PuntInteres)item.obtPuntSortida(), Boolean.FALSE);
                            nObligatsVisitats--;
                        }
                    }
                }
                ruta.treureUltimItem();
            }
            
            private boolean estadaAcceptable(Allotjament hotel, Viatge viatge){
                return(!ruta.obtFinal().plusDays(1).isAfter(viatge.obtDataMax()) && ruta.obtCost()+hotel.obtenirPreu() < viatge.obtPreuMax());
            }
            
            private boolean superaMaxim(MitjaTransport mT, Viatge viatge){
                return(ruta.obtCost()+mT.getPreu() > viatge.obtPreuMax() || ruta.obtDurada()+mT.getDurada() > viatge.obtDurada());
            }
            
            private boolean esPotVisitar(PuntVisitable pV, LocalDateTime actual, Viatge viatge){
                LocalTime hora= actual.toLocalTime();
                if(pV.obtObertura().isAfter(hora)){
                    LocalDateTime obertura= LocalDateTime.of(actual.toLocalDate(), pV.obtObertura());
                    return (!obertura.plusMinutes(pV.obtTempsVisita()).toLocalDate().isAfter(actual.toLocalDate())) && (!obertura.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                }
                else if(!hora.plusMinutes(pV.obtTempsVisita()).isAfter(pV.obtTancament())) return (!actual.plusMinutes(pV.obtTempsVisita()).toLocalDate().isAfter(actual.toLocalDate())) && (!actual.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                else return false;
            }
            
            private boolean acceptable(PuntInteres pI, Viatge viatge){ 
                if(pI instanceof PuntVisitable && !visitats.contains(pI)){
                    PuntVisitable pV= (PuntVisitable)pI;
                    if(pV.equals(viatge.obtDesti())) return (nObligatsVisitats == puntsObligats.size()) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else if(tipusRuta.equals("barata")) return (puntsObligats.containsKey(pI) || pV.obtenirPreu() == 0) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else if(tipusRuta.equals("sat")) return (puntsObligats.containsKey(pI) || satisfaccio.get(pI) > 0) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else return puntsObligats.containsKey(pI) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                }
                else if(pI instanceof Allotjament){
                    Allotjament hotel= (Allotjament)pI;
                    return(viatge.categoriaDesitjada(hotel) && !LocalDateTime.of(ruta.obtFinal().toLocalDate().plusDays(1), LocalTime.of(4, 0)).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+hotel.obtenirPreu() <= viatge.obtPreuMax());
                }
                else return false;
            }
            
            private boolean acceptable(MitjaTransport mitja, Viatge viatge){
                if(mitja instanceof MTDirecte){
                    MTDirecte mD= (MTDirecte)mitja;
                    if((anterior != null && anterior.equals(mD)) || superaMaxim(mD, viatge)) return false;
                    else{
                        LocalDate fi= ruta.obtFinal().plusMinutes(mD.getDurada()).toLocalDate();
                        return(fi.equals(viatge.obtDataInici().toLocalDate()) || fi.equals(viatge.obtDataMax().toLocalDate()) || !fi.isAfter(ruta.obtFinal().toLocalDate()));
                    }
                }
                else if(mitja instanceof MTEstacio){
                    if(ruta.obtUltimItem() instanceof TrajecteDirecte){
                        TrajecteDirecte tD= (TrajecteDirecte)ruta.obtUltimItem();
                        MTDirecte mD= tD.obtMitja();
                        return !mD.getOrigen().obtenirLloc().equals(mD.getDesti().obtenirLloc());
                    }
                    else return true;
                }
                else if(mitja instanceof MTIndirecte){
                   MTIndirecte mI= (MTIndirecte)mitja;
                   LocalDateTime actual= ruta.obtFinal().plusMinutes(mI.getOrigen().obtTempsSortidaLloc(mI.getDesti().obtLloc()));
                   LocalDateTime sortida= mI.getOrigen().obtSortida(mI, actual);
                   if(sortida != null){
                        LocalDate fi= sortida.plusMinutes(mI.getDurada()).toLocalDate();
                        LocalDate diaSortida= sortida.toLocalDate();
                        if(diaSortida.equals(viatge.obtDataInici().toLocalDate()) || diaSortida.equals(viatge.obtDataMax().toLocalDate()) || !fi.isAfter(actual.toLocalDate())){
                            sortidaMTIndirecte= sortida;
                            return true;
                        }
                        else return false;
                    }
                    else return false;
                }
                else{
                    MTPunts mP= (MTPunts)mitja;
                    if(superaMaxim(mP, viatge)) 
                        return false;
                    else{
                        LocalDate fi= ruta.obtFinal().plusMinutes(mP.getDurada()).toLocalDate();
                        return(ruta.obtFinal().toLocalDate().equals(viatge.obtDataInici().toLocalDate()) || ruta.obtFinal().toLocalDate().equals(viatge.obtDataMax().toLocalDate()) || !fi.isAfter(ruta.obtFinal().toLocalDate()));
                    }
                }
            }
            
            private boolean acceptable(Candidats iCan, Viatge viatge){
                if(iCan.esPossibleQuedarseHotel()){
                    return actual.estadaAcceptable(iCan.hotel(), viatge);
                }
                else return actual.acceptable(iCan.actual(), viatge);
            }
            
            private LocalDateTime obtTemps(){
                return ruta.obtFinal();
            }
            
            private Ruta obtRuta(){
                return ruta;
            }
            
            private boolean esCompleta(Viatge viatge){
                if(ruta.arribaDesti(viatge.obtDesti())) return nObligatsVisitats == puntsObligats.size();
                else return false;
            }
        }
        
        private class Candidats{
            private final PuntRuta pR;
            private final List<MitjaTransport> mitjans;
            private List<MTPunts> baixarAPunts;
            private Integer listCounter;
            private Integer baixarCounter;
            
            private Candidats(Mapa mundi, PuntRuta pI){
                pR= pI;
                mitjans= mundi.obtMitjansPunt(pR, tipusRuta);
                baixarAPunts= new ArrayList<>();
                baixarCounter= 0;
                if(pR instanceof Allotjament) listCounter= -1;
                else listCounter= 0;
            }
            
            private Candidats(Mapa mundi, Estacio est, Lloc origen){
                pR= est;
                mitjans= mundi.obtMitjansPunt(pR, tipusRuta);
                baixarAPunts= mundi.obtBaixarAPunts(est, origen);
                baixarCounter= 0;
                listCounter= 0;
            }
            
            private Boolean fi(){
                return listCounter==mitjans.size() && baixarCounter==baixarAPunts.size();
            }
            
            private Boolean esPossibleQuedarseHotel(){
                return(pR instanceof Allotjament && listCounter==-1);
            }
            
            //pre: es possible quedarse hotel
            private Allotjament hotel(){
                return (Allotjament)pR;
            }
            
            private MitjaTransport actual(){
                if(baixarAPunts.isEmpty()) return(mitjans.get(listCounter));
                else return(baixarAPunts.get(baixarCounter));
            }
            
            private ItemRuta crearItem(Solucio sol){
                LocalDateTime tempsActual= sol.obtTemps();
                ItemRuta item;
                if(esPossibleQuedarseHotel()){
                    Integer satis= satisfaccio.get((PuntInteres)pR);
                    item= new EstadaHotel((Allotjament)pR, tempsActual, satis);
                }
                else{
                    MitjaTransport mT;
                    if(baixarAPunts.isEmpty()) mT= mitjans.get(listCounter);
                    else mT= baixarAPunts.get(baixarCounter);
                    if(mT instanceof MTDirecte){
                        item= new TrajecteDirecte((MTDirecte)mT, tempsActual);
                    }
                    else if(mT instanceof MTEstacio){
                        item= new TrajecteEstacio((MTEstacio)mT, tempsActual);
                    }
                    else if(mT instanceof MTIndirecte){
                        item= new TrajecteIndirecte((MTIndirecte)mT, sol.sortidaMTIndirecte);
                        sol.sortidaMTIndirecte= null;
                    }
                    else{
                        item= new TrajectePunts((MTPunts)mT, tempsActual);
                    }
                }
                return item;
            }
            
            private void seguent(){
                if(baixarAPunts.isEmpty()) listCounter++;
                else{
                    baixarCounter++;
                    if(baixarCounter==mitjans.size()) baixarAPunts= new ArrayList<>();
                }
            }
        }
    }
}
