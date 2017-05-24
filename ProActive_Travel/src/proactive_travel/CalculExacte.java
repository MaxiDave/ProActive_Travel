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
import java.util.ArrayList;
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
            actual= new Solucio(viatge.obtDataInici(), viatge.obtIteradorPI());
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
                    ItemRuta item= iCan.crearItem(actual, viatge);
                    actual.anotar(item);
                    //System.out.println(actual.obtRuta());
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
            return true;
            //if(optima == null || tipusRuta.equals("sat")) return true;
            //else return esMillor(actual, optima);
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

        /**
        * DESCRIPCIÓ GENERAL
        * @brief   Classe que emmagatzema la solució parcial del Backtraking a mesura que es va executant
        * @details S'encarrega de dur a terme la part d'emmagatzament de dades en ED útils de cares al càlcul,
        *          i també de la Ruta que va elaborant
        */
        private class Solucio{
            //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
            private Ruta ruta;                                  ///< @brief Emmagatzema la Ruta parcial que es va modificant a mida que l'algorisme de Backtraking treballa
            private Set<PuntInteres> visitats;                  ///< @brief Contenidor que emmagatzema la informació de quins punts d'interès ha visitat
            private Map<PuntInteres, Boolean> puntsObligats;    ///< @brief Contenidor que emmagatzema els punts als que la Ruta està obligada a passar juntament amb un booleà que indica si ja hi ha passat o no
            private Integer nObligatsVisitats;                  ///< @brief Emmagatzema el nombre de punts intermitjos obligats que s'han visitat, per tal de saber quan tots els puntsObligats estan a cert
            private LocalDateTime hora;                         ///< @brief Emmagatzema una hora de sortida en cas de que un MitjaTransport sigui acceptable i s'hagi d'anotar, per tal de no tornar a calcular-ho
            
            //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
            /** 
             * @pre --
             * @post Es crea una solució a partir d'una data d'inici i d'un iterador al PuntInteres els quals la Ruta està obligada a passar
             * @brief Constructor
             */
            private Solucio(LocalDateTime temps, Iterator<PuntInteres> itPunts){
                nObligatsVisitats= 0;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                while(itPunts.hasNext()) puntsObligats.put(itPunts.next(), Boolean.FALSE);
            }
            
            //MÈTODES PRIVATS----------------------------------------------------------------------------------------------------------------------------
            /** 
            * @pre --
            * @post Crea i Retorna els candidats a afegir a la Ruta a partir del PuntRuta actual "pI" i el Mapa "mundi"
            * @brief Inicialitza els candidats
            */
            private Candidats inicialitzarCandidats(Mapa mundi, PuntRuta pI){
                ItemRuta ultimItem= ruta.obtUltimItem();
                if((ruta.teItems() && ultimItem instanceof TrajecteIndirecte)) return new Candidats(mundi, (Estacio)pI, ((TrajecteIndirecte) ultimItem).obtLlocOrigen());
                else return new Candidats(mundi, pI);
            }
            
            /** 
            * @pre --
            * @post Afegeix el primer punt de la Ruta com l'ItemRuta "item"
            * @brief Afegeix el primer punt de la Ruta (Tractat com a situació especial)
            */
            private void afegirPrimerPunt(ItemRuta item){
                ruta.afegeixItemRuta(item);
                if(item instanceof Visita){
                    PuntInteres pI= (PuntInteres)item.obtPuntSortida();
                    visitats.add(pI);
                }
            }
            
            /** 
            * @pre ItemRuta "item" és acceptable 
            * @post Anota l'ItemRuta "item" a la solució, i si és una visita l'afegeix a visitats, i si també és dels punts obligats posa puntsObligats(punt -> cert)
            * @brief Anota l'ItemRuta "item"
            */
            private void anotar(ItemRuta item){
                ruta.afegeixItemRuta(item);
                if(item instanceof Visita){
                    PuntInteres pI= (PuntInteres)item.obtPuntSortida();
                    visitats.add(pI);
                    if(puntsObligats.containsKey(pI)){
                        puntsObligats.replace(pI, Boolean.TRUE);
                        nObligatsVisitats++;
                    }
                }
            }
            
            /** 
            * @pre Ruta no buida
            * @post Desanota l'últim ItemRuta de la Ruta actual, i si és una visita el treu de visitats, i si és necessari, posa fals el puntsObligats del seu PuntInteres
            * @brief Desanota l'últim ItemRuta
            */
            private void desanotar(){
                ItemRuta item= ruta.treureUltimItem();
                if(item instanceof Visita){
                    PuntInteres pI= (PuntInteres)item.obtPuntSortida();
                    if(puntsObligats.containsKey(pI)){
                        puntsObligats.replace(pI, Boolean.FALSE);
                        nObligatsVisitats--;
                    }
                    visitats.remove(pI);
                }
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el candidat MitjaTransport "mT" supera el màxim permés en preu o durada estipulats en el Viatge "viatge"
            * @brief Supera el candidat el màxim permés?
            */
            private boolean superaMaxim(MitjaTransport mT, Viatge viatge){
                return(ruta.obtCost()+mT.obtPreu() > viatge.obtPreuMax() || ruta.obtDurada()+mT.obtDurada() > viatge.obtDurada());
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el PuntVisitable candidat "pV" es pot visitar a partir del moment "actual" i de les condicions estipulades en el Viatge "viatge"
            * @brief Supera el candidat el màxim permés?
            */
            private boolean esPotVisitar(PuntVisitable pV, LocalDateTime actual, Viatge viatge){
                LocalTime horaActual= actual.toLocalTime();
                if(pV.obtObertura().isAfter(horaActual)){
                    LocalDateTime obertura= LocalDateTime.of(actual.toLocalDate(), pV.obtObertura());
                    hora= obertura;
                    return (!obertura.plusMinutes(pV.obtTempsVisita()).toLocalDate().isAfter(actual.toLocalDate())) && (!obertura.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                }
                else if(!horaActual.plusMinutes(pV.obtTempsVisita()).isAfter(pV.obtTancament())){
                    hora= actual;
                    return (!actual.plusMinutes(pV.obtTempsVisita()).toLocalDate().isAfter(actual.toLocalDate())) && (!actual.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                }
                else return false;
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el candidat PuntInteres "pI" és acceptable a partir de les condicions estipulades en el Viatge "viatge"
            * @brief És el PuntInteres "pI" acceptable?
            */
            private boolean puntAcceptable(PuntInteres pI, Viatge viatge){
                if(pI instanceof PuntVisitable && !visitats.contains(pI)){
                    PuntVisitable pV= (PuntVisitable)pI;
                    if(pV.equals(viatge.obtDesti())) return (nObligatsVisitats == puntsObligats.size()) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else if(tipusRuta.equals("barata")) return (puntsObligats.containsKey(pI) || pV.obtenirPreu() == 0) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else if(tipusRuta.equals("sat")) return (puntsObligats.containsKey(pI) || satisfaccio.get(pI) > 0) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                    else return puntsObligats.containsKey(pI) && esPotVisitar(pV, ruta.obtFinal(), viatge);
                }
                else if(pI instanceof Allotjament){
                    Allotjament hotel= (Allotjament)pI;
                    return(!LocalDateTime.of(ruta.obtFinal().toLocalDate().plusDays(1), LocalTime.of(4, 0)).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+hotel.obtenirPreu() <= viatge.obtPreuMax());
                }
                else return false;
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el candidat MitjaTransport "mT" és acceptable a partir de les condicions estipulades en el Viatge "viatge"
            * @brief És el MitjaTransport "mT" acceptable?
            */
            private boolean mitjaAcceptable(MitjaTransport mT, Viatge viatge){
                if(mT instanceof MTDirecte){
                    MTDirecte mD= (MTDirecte)mT;
                    if((ruta.obtUltimItem() instanceof TrajecteDirecte && ((TrajecteDirecte)ruta.obtUltimItem()).obtMitja().equals(mD)) || superaMaxim(mD, viatge)) return false;
                    else{
                        LocalDate fi= ruta.obtFinal().plusMinutes(mD.obtDurada()).toLocalDate();
                        return !fi.isAfter(ruta.obtFinal().toLocalDate());
                    }
                }
                else if(mT instanceof MTEstacio){
                    if(ruta.obtUltimItem() instanceof TrajecteDirecte){
                        TrajecteDirecte tD= (TrajecteDirecte)ruta.obtUltimItem();
                        MTDirecte mD= tD.obtMitja();
                        return !mD.getOrigen().obtenirLloc().equals(mD.getDesti().obtenirLloc());
                    }
                    else return true;
                }
                else if(mT instanceof MTIndirecte){
                    MTIndirecte mI= (MTIndirecte)mT;
                    LocalDateTime actual= ruta.obtFinal().plusMinutes(mI.getOrigen().obtTempsSortidaLloc(mI.getDesti().obtLloc()));
                    if(actual.toLocalDate().isAfter(ruta.obtFinal().toLocalDate())) return false;
                    else{
                        LocalDateTime sortida= mI.getOrigen().obtSortida(mI, actual);
                        if(sortida != null){
                             LocalDate fi= sortida.plusMinutes(mI.obtDurada()).toLocalDate();
                             LocalDate diaSortida= sortida.toLocalDate();
                             if(diaSortida.equals(viatge.obtDataInici().toLocalDate()) || diaSortida.equals(viatge.obtDataMax().toLocalDate()) || !fi.isAfter(actual.toLocalDate())){
                                 hora= sortida;
                                 return true;
                             }
                             else return false;
                         }
                         else return false;
                    }
                }
                else{
                    MTPunts mP= (MTPunts)mT;
                    if(superaMaxim(mP, viatge)) 
                        return false;
                    else{
                        LocalDate fi= ruta.obtFinal().plusMinutes(mP.obtDurada()).toLocalDate();
                        return(ruta.obtFinal().toLocalDate().equals(viatge.obtDataInici().toLocalDate()) || ruta.obtFinal().toLocalDate().equals(viatge.obtDataMax().toLocalDate()) || !fi.isAfter(ruta.obtFinal().toLocalDate()));
                    }
                }
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el candidat Candidats "iCan" és acceptable a partir de les condicions estipulades en el Viatge "viatge"
            * @brief És el Candidats "iCan" acceptable?
            */
            private boolean acceptable(Candidats iCan, Viatge viatge){
                if(iCan.esPuntInteres()) return puntAcceptable(iCan.puntActual(), viatge);
                else return mitjaAcceptable(iCan.mitjaActual(), viatge);
            }
            
            /** 
            * @pre --
            * @post Retorna la Ruta parcial de la Solució
            * @brief Retorna la Ruta parcial de la Solució
            */
            private Ruta obtRuta(){
                return ruta;
            }
            
            /** 
            * @pre --
            * @post Retorna cert si la solució actual és completa a partir del destí del Viatge "desti"
            * @brief La solució és completa?
            */
            private boolean esCompleta(Viatge viatge){
                if(ruta.arribaDesti(viatge.obtDesti())) return nObligatsVisitats == puntsObligats.size();
                else return false;
            }
        }
        
        /**
        * DESCRIPCIÓ GENERAL
        * @brief   Classe que emmagatzema els candidats de la solució en cada pas recursiu
        * @details S'encarrega de dur a terme la part de control dels candidats
        */
        private class Candidats{
            private final PuntRuta pR;                      ///< @brief Representa el punt de la ruta on es troba l'algorisme quan inicialitza els candidats
            private final List<MitjaTransport> mitjans;     ///< @brief Conté els MitjaTransport candidats per agafar respecte pR 
            private List<MTPunts> baixarAPunts;             ///< @brief Conté els MTPunts candidats per agafar respecte pR en cas que sigui una Estacio 
            private Integer listCounter;                    ///< @brief Contador que ens indica a quina posició de la llista "mitjans" ens trobem
            private Integer baixarCounter;                  ///< @brief Contador que ens indica a quina posició de la llista "baixarAPunts" ens trobem
            
            /** 
            * @pre --
            * @post Es generen els candidats a partir del Mapa "mundi" i el PuntRuta "pR"
            * @brief Constructor per defecte
            */
            private Candidats(Mapa mundi, PuntRuta pR){
                this.pR= pR;
                mitjans= mundi.obtMitjansPunt(pR, tipusRuta);
                baixarAPunts= new ArrayList<>();
                baixarCounter= 0;
                if(pR instanceof PuntInteres) listCounter= -1;
                else listCounter= 0;
            }
            
            /** 
            * @pre --
            * @post Es generen els candidats a partir del Mapa "mundi", l'estació actual "est" i el lloc d'origen
            * @brief Constructor a partir d'Estació si es ve d'un MTIndirecte
            */
            private Candidats(Mapa mundi, Estacio est, Lloc origen){
                pR= est;
                mitjans= mundi.obtMitjansPunt(pR, tipusRuta);
                baixarAPunts= mundi.obtBaixarAPunts(est, origen);
                baixarCounter= 0;
                listCounter= 0;
            }
            
            /** 
            * @pre --
            * @post Retorna cert si és fi, és a dir, si no queden candidats
            * @brief Queden candidats?
            */
            private Boolean fi(){
                return listCounter==mitjans.size() && baixarCounter==baixarAPunts.size();
            }
            
            /** 
            * @pre --
            * @post Retorna cert si el candidat actual és un PuntInteres
            * @brief És el candidat un PuntInteres ?
            */
            private Boolean esPuntInteres(){
                return pR instanceof PuntInteres && listCounter==-1;
            }
            
            /** 
            * @pre Candidats actual és un PuntInteres 
            * @post Retorna el PuntInteres actual candidat
            * @brief Retorna el PuntInteres actual candidat
            */
            private PuntInteres puntActual(){
                return (PuntInteres)pR;
            }
            
            /** 
            * @pre Candidats actual és un MitjaTransport 
            * @post Retorna el MitjaTransport actual candidat
            * @brief Retorna el MitjaTransport actual candidat
            */
            private MitjaTransport mitjaActual(){
                if(baixarAPunts.isEmpty()) return(mitjans.get(listCounter));
                else return(baixarAPunts.get(baixarCounter));
            }
            
            /** 
            * @pre --
            * @post Crea un ItemRuta amb el candidat actual a partir de la Solucio "sol" i el Viatge "viatge"
            * @brief Retorna el PuntInteres actual candidat
            */
            private ItemRuta crearItem(Solucio sol, Viatge viatge){
                LocalDateTime tempsActual= sol.obtRuta().obtFinal();
                ItemRuta item;
                if(pR instanceof Allotjament && listCounter==-1){
                    Integer satis= satisfaccio.get((PuntInteres)pR);
                    if(viatge.categoriaDesitjada((Allotjament)pR)) satis++;
                    item= new EstadaHotel((Allotjament)pR, tempsActual, satis);
                }
                else if(pR instanceof PuntVisitable && listCounter==-1){
                    Integer satis= satisfaccio.get((PuntInteres)pR);
                    item= new Visita((PuntVisitable)pR, sol.hora, satis);
                    sol.hora= null;
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
                        item= new TrajecteIndirecte((MTIndirecte)mT, sol.hora);
                        sol.hora= null;
                    }
                    else{
                        item= new TrajectePunts((MTPunts)mT, tempsActual);
                    }
                }
                return item;
            }
            
            /** 
            * @pre !Candidats.fi()
            * @post Passa al següent candidat
            * @brief Passa al següent candidat
            */
            private void seguent(){
                if(baixarAPunts.isEmpty()) listCounter++;
                else{
                    baixarCounter++;
                    if(baixarCounter==baixarAPunts.size()){
                        baixarCounter= 0;
                        baixarAPunts= new ArrayList<>();
                    }
                }
            }
        }
    }
}
