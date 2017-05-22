//ProActive_Travel

/**
 * @file: CalculExacte.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en trobar
 *         la millor solució (Backtraking) de Ruta, en termes monetaris, temps i Satisfacció 
 * @copyright: Public License
 */

package proactive_travel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs exactes
 */
public class CalculExacteBeta {
    private Map<PuntInteres, Integer> satisfaccio;
    
    public CalculExacteBeta(Mapa mundi, Viatge viatge){
        satisfaccio= new HashMap<>();
        Iterator<PuntInteres> it= mundi.obtIteradorPunts();
        while(it.hasNext()){
            PuntInteres pI= it.next();
            Integer satis= pI.grauSatisfaccio(viatge.obtMapSatisfaccio());
            satisfaccio.put(pI, satis);
        }
    }
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    
    /** 
     * @pre: --
     * @post: Calcula i retorna rutes a partir del mapa i viatge mitjançant backtraking
     */
    public List<Ruta> calcularRutaBack(Mapa mundi, Viatge viatge){
        List<Ruta> rutes= new ArrayList<>();
        for(int i=0; i<3; i++) rutes.add(i, null);
        if(viatge.esCurta()){
            Solucionador sol= new Solucionador(viatge, "curta", null);
            System.out.println("RUTA CURTA");
            System.out.println("------------------------------------------------------------------------------------------------------------");
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
            System.out.println("RUTA BARATA");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            if(sol.inicialitzat()) rutes.set(1, sol.algBack(mundi, viatge));
        }
        if(viatge.esSatisfactoria()){
            Solucionador sol;
            if(rutes.get(0) != null){
                Ruta optima= new Ruta(rutes.get(0));
                optima.canviarTipus("sat");
                sol= new Solucionador(viatge, "sat", optima);
            }
            else if(rutes.get(1) != null){
                Ruta optima= new Ruta(rutes.get(1));
                optima.canviarTipus("sat");
                sol= new Solucionador(viatge, "sat", optima);
            }
            else sol= new Solucionador(viatge, "sat", null);
            System.out.println("RUTA SAT");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            if(sol.inicialitzat()) rutes.set(2, sol.algBack(mundi, viatge));
        }
        return rutes;
    }
    
    private class Solucionador{
        private Ruta optima;
        private Solucio actual;
        protected final String tipusRuta;
        
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
        
        private boolean inicialitzat(){
            return actual.obtRuta().teItems();
        }
        
        private Ruta algBack(Mapa mundi, Viatge viatge) {
            algRecursiu(mundi, viatge.obtOrigen(), viatge);
            if(optima != null) return optima;
            else return null;
        }
        
        private void algRecursiu(Mapa mundi, PuntInteres act, Viatge viatge){
            Candidats iCan= actual.inicialitzarCandidats(mundi, act);
            while(!iCan.fi()){
                if((iCan.esPossibleQuedarseHotel() && actual.estadaAcceptable(iCan.hotel(), viatge)) || (!iCan.esPossibleQuedarseHotel() && actual.acceptable(iCan.actual(), viatge) && esPotMillorar(actual.obtRuta(), optima))){
                    ItemRuta item= iCan.crearItem(actual.obtTemps(), viatge);
                    //System.out.println("Anotem: "+item);
                    actual.anotar(item, viatge);
                    //System.out.println(actual.obtRuta());
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, item.obtPuntSortida(), viatge);
                    else if(esMillor(actual.obtRuta(), optima)) optima= new Ruta(actual.obtRuta());
                    actual.desanotar();
                }
                iCan.seguent();
            }
        }
        
        private boolean esPotMillorar(Ruta actual, Ruta optima){
           if(optima == null) return true;
           else return esMillor(actual, optima);
        }
        
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
                      
            private Solucio(LocalDateTime temps, Viatge viatge){
                nObligatsVisitats= 0;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                anterior= null;
                afegitPI= new ArrayDeque<>();
                Iterator<PuntInteres> it= viatge.obtIteradorPI();
                while(it.hasNext()) puntsObligats.put(it.next(), Boolean.FALSE);
            }
            
            private Candidats inicialitzarCandidats(Mapa mundi, PuntInteres pI){
                return new Candidats(mundi, pI);
            }
            
            private void afegirPrimerPunt(ItemRuta item){
                ruta.afegeixItemRuta(item);
                if(item instanceof Visita) visitats.add(item.obtPuntSortida());
            }
            
            private void anotar(ItemRuta item, Viatge viatge){
                if(item instanceof TrajecteDirecte){
                    ruta.afegeixItemRuta(item);
                    PuntInteres aAnar= item.obtPuntSortida();
                    if(acceptable(aAnar, viatge)){
                        anterior= null;
                        Integer satis= satisfaccio.get(aAnar);
                        ItemRuta itemPunt;
                        if(aAnar instanceof PuntVisitable){
                            PuntVisitable pV= (PuntVisitable)aAnar;
                            if(ruta.obtFinal().toLocalTime().isAfter(pV.obtObertura())) itemPunt= new Visita(pV, ruta.obtFinal(), satis);
                            else itemPunt= new Visita(pV, LocalDateTime.of(ruta.obtFinal().toLocalDate(), pV.obtObertura()), satis);
                            //System.out.println("Anotem: "+visita);
                            visitats.add(itemPunt.obtPuntSortida());
                            if(puntsObligats.containsKey(itemPunt.obtPuntSortida())){
                                puntsObligats.replace(itemPunt.obtPuntSortida(), Boolean.TRUE);
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
                else if(item instanceof EstadaHotel){
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
                        visitats.remove(item.obtPuntSortida());
                        if(puntsObligats.containsKey(item.obtPuntSortida())){
                            puntsObligats.replace(item.obtPuntSortida(), Boolean.FALSE);
                            nObligatsVisitats--;
                        }
                    }
                }
                ruta.treureUltimItem();
            }
            
            private boolean estadaAcceptable(Allotjament hotel, Viatge viatge){
                return(!ruta.obtFinal().plusDays(1).isAfter(viatge.obtDataMax()) && ruta.obtCost()+hotel.obtenirPreu() < viatge.obtPreuMax());
            }
            
            private boolean superaMaxim(MTDirecte mD, Viatge viatge){
                return(ruta.obtCost()+mD.getPreu() > viatge.obtPreuMax() || ruta.obtDurada()+mD.getDurada() > viatge.obtDurada());
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
                    return(!LocalDateTime.of(ruta.obtFinal().toLocalDate().plusDays(1), LocalTime.of(4, 0)).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+hotel.obtenirPreu() <= viatge.obtPreuMax());
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
                else{
                    throw new UnsupportedOperationException("Not supported yet"); 
                }
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
            private final List<MitjaTransport> mitjans;
            private Integer listCounter;
            private final PuntInteres pI;
            
            private Candidats(Mapa mundi, PuntInteres pI){
                this.pI= pI;
                mitjans= mundi.obtMitjansPunt(pI, tipusRuta);
                if(pI instanceof Allotjament) listCounter= -1;
                else listCounter= 0;
            }
            
            private Boolean fi(){
                return listCounter==mitjans.size();
            }
            
            private Boolean esPossibleQuedarseHotel(){
                return(pI instanceof Allotjament && listCounter==-1);
            }
            
            //pre: es possible quedarse hotel
            private Allotjament hotel(){
                return (Allotjament)pI;
            }
            
            private MitjaTransport actual(){
                return(mitjans.get(listCounter));
            }
            
            private ItemRuta crearItem(LocalDateTime tempsActual, Viatge viatge){
                ItemRuta item;
                if(esPossibleQuedarseHotel()){
                    Integer satis= satisfaccio.get(pI);
                    item= new EstadaHotel((Allotjament)pI, tempsActual, satis);
                }
                else if(mitjans.get(listCounter) instanceof MTDirecte){
                    item= new TrajecteDirecte((MTDirecte)mitjans.get(listCounter), tempsActual);
                }
                else throw new UnsupportedOperationException("Not supported yet");
                return item;
            }
            
            private void seguent(){
                listCounter++;
            }
        }
    }
}
