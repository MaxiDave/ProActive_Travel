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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs exactes
 */
public abstract class CalculExacteBeta {
    private static Map<PuntInteres, Integer> satisfaccio;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    private static void generarEDsatisfaccio(Mapa mundi, Viatge viatge){
        satisfaccio= new HashMap<>();
        Iterator<PuntInteres> it= mundi.obtIteradorPunts();
        while(it.hasNext()){
            PuntInteres pI= it.next();
            Integer satis= pI.grauSatisfaccio(viatge.obtMapSatisfaccio());
            satisfaccio.put(pI, satis);
        }
    }
    
    /** 
     * @pre: --
     * @post: Calcula i retorna rutes a partir del mapa i viatge mitjançant backtraking
     */
    public static List<Ruta> calcularRutaBack(Mapa mundi, Viatge viatge){
        generarEDsatisfaccio(mundi, viatge);
        List<Ruta> rutes= new ArrayList<>();
        if(viatge.esCurta()) rutes.add(Solucionador.algBack(mundi, viatge, "curta"));
        if(viatge.esBarata()) rutes.add(Solucionador.algBack(mundi, viatge, "barata"));
        if(viatge.esSatisfactoria()) rutes.add(Solucionador.algBack(mundi, viatge, "sat"));
        return rutes;
    }
    
    private static abstract class Solucionador{
        private static Ruta optima;
        private static Solucio actual;
        private static String tipusRuta;
        
        private static Boolean inicialitzaPrimerItem(Viatge viatge, Solucio actual){
            Boolean inicialitzat= false;
            LocalTime iniciViatge= viatge.obtDataInici().toLocalTime();
            PuntInteres inici= viatge.obtOrigen();
            if(inici instanceof PuntVisitable){
                PuntVisitable pV= (PuntVisitable)inici;
                Visita visita;
                if(pV.obtObertura().isAfter(iniciViatge)){
                    Integer satis= satisfaccio.get(inici);
                    visita= new Visita(pV, LocalDateTime.of(viatge.obtDataInici().toLocalDate(), pV.obtObertura()), satis);
                    actual.anotar(visita);
                    inicialitzat= true;
                }
                else if(iniciViatge.plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())){
                    Integer satis= satisfaccio.get(inici);
                    visita= new Visita(pV, viatge.obtDataInici(), satis);
                    actual.anotar(visita);
                    inicialitzat= true;
                }
            }
            else{
                Allotjament hotel= (Allotjament)inici;
                Integer satis= satisfaccio.get(inici);
                EstadaHotel estada= new EstadaHotel(hotel, viatge.obtDataInici(), satis);
                actual.anotar(estada);
                inicialitzat= true;
            }
            return inicialitzat;
        }
        
        private static Ruta algBack(Mapa mundi, Viatge viatge, String tipus) {
            tipusRuta= tipus;
            actual= new Solucio(viatge.obtDataInici(), viatge);
            optima= null;
            if(inicialitzaPrimerItem(viatge, actual)){
                algRecursiu(mundi, viatge.obtOrigen(), viatge);
                if(optima != null) return optima;
                else return null;
            }
            else return null;
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres act, Viatge viatge){
            Candidats iCan= actual.inicialitzarCandidats(mundi, act);
            while(!iCan.fi()){
                if((iCan.esPossibleQuedarseHotel() && actual.estadaAcceptable(iCan.hotel(), viatge)) || (actual.acceptable(iCan.actual(), viatge) && esPotMillorar(actual.obtRuta(), optima))){
                    ItemRuta item= iCan.crearItem(actual.obtTemps(), viatge);
                    System.out.println("Anotem: "+item);
                    actual.anotar(item, viatge);
                    System.out.println(actual.obtRuta());
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, item.obtPuntSortida(), viatge);
                    else if(esMillor(actual.obtRuta(), optima)) optima= new Ruta(actual.obtRuta());
                    actual.desanotar(item);
                }
                iCan.seguent();
            }
        }
        
        private static boolean esPotMillorar(Ruta actual, Ruta optima){
           if(optima == null) return true;
           else return esMillor(actual, optima);
        }
        
        private static boolean esMillor(Ruta actual, Ruta optima){
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
                    else if(cmpC < 0) return false;
                    else return true;
                }
            }
        }
        
        private static class Solucio{
            private static Ruta ruta;
            private static LocalDateTime tempsActual;
            private static Set<PuntInteres> visitats;
            private static Map<PuntInteres, Boolean> puntsObligats;
            private static Integer nObligatsVisitats;
            private static Deque<Boolean> afegitPI;
                      
            private Solucio(LocalDateTime temps, Viatge viatge){
                nObligatsVisitats= 0;
                tempsActual= temps;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                afegitPI= new ArrayDeque<>();
                Iterator<PuntInteres> it= viatge.obtIteradorPI();
                while(it.hasNext()) puntsObligats.put(it.next(), Boolean.FALSE);
            }
            
            private Candidats inicialitzarCandidats(Mapa mundi, PuntInteres pI){
                return new Candidats(mundi, pI);
            }
            
            private void anotar(ItemRuta item){
                ruta.afegeixItemRuta(item);
                Integer duradaTempsLliure= (int)Duration.between(tempsActual, item.obtInici()).toMinutes();
                tempsActual= tempsActual.plusMinutes(item.obtDurada()+duradaTempsLliure);
                if(item instanceof Visita){
                    visitats.add(item.obtPuntSortida());
                    if(puntsObligats.containsKey(item.obtPuntSortida())){
                        puntsObligats.replace(item.obtPuntSortida(), Boolean.TRUE);
                        nObligatsVisitats++;
                    }
                }
            }
            
            private void anotar(ItemRuta item, Viatge viatge){
                ruta.afegeixItemRuta(item);
                Integer duradaTempsLliure= (int)Duration.between(tempsActual, item.obtInici()).toMinutes();
                tempsActual= tempsActual.plusMinutes(item.obtDurada()+duradaTempsLliure);
                PuntInteres aAnar= item.obtPuntSortida();
                if(acceptable(aAnar, viatge)){
                    afegitPI.addLast(Boolean.TRUE);
                    PuntVisitable pV= (PuntVisitable)aAnar;
                    Integer satis= satisfaccio.get(aAnar);
                    ItemRuta visita;
                    if(tempsActual.toLocalTime().isAfter(pV.obtObertura())) visita= new Visita(pV, tempsActual, satis);
                    else visita= new Visita(pV, LocalDateTime.of(viatge.obtDataInici().toLocalDate(), pV.obtObertura()), satis);
                    System.out.println("Anotem: "+visita);
                    ruta.afegeixItemRuta(visita);
                    if(visita instanceof Visita){
                        visitats.add(visita.obtPuntSortida());
                        if(puntsObligats.containsKey(visita.obtPuntSortida())){
                            puntsObligats.replace(visita.obtPuntSortida(), Boolean.TRUE);
                            nObligatsVisitats++;
                        }
                    }
                }
                else afegitPI.addLast(Boolean.FALSE);
            }
            
            private void desanotar(ItemRuta item){
                System.out.println("Desanotem: "+item);
                Boolean afegirAnteriorPI= afegitPI.pollLast();
                if(afegirAnteriorPI){
                    System.out.println("S'havia afegit PI, es treu");
                    Integer duradaTempsLliure= ruta.treureUltimItem();
                    tempsActual= tempsActual.minusMinutes(item.obtDurada()+duradaTempsLliure);
                    if(item instanceof Visita){
                        visitats.remove(item.obtPuntSortida());
                        if(puntsObligats.containsKey(item.obtPuntSortida())){
                            puntsObligats.replace(item.obtPuntSortida(), Boolean.FALSE);
                            nObligatsVisitats--;
                        }
                    }
                }
                Integer duradaTempsLliure= ruta.treureUltimItem();
                tempsActual= tempsActual.minusMinutes(item.obtDurada()+duradaTempsLliure);
            }
            
            private boolean estadaAcceptable(Allotjament hotel, Viatge viatge){
                return(!tempsActual.plusDays(1).isAfter(viatge.obtDataMax()) && ruta.obtCost()+hotel.obtenirPreu() < viatge.obtPreuMax());
            }
            
            private boolean superaMaxim(MTDirecte mD, Viatge viatge){
                return(ruta.obtCost()+mD.getPreu() > viatge.obtPreuMax() || ruta.obtDurada()+mD.getDurada() > viatge.obtDurada());
            }
            
            private boolean acceptable(PuntInteres pI, Viatge viatge){
                if(pI instanceof PuntVisitable){
                    PuntVisitable pV= (PuntVisitable)pI;
                    if(!visitats.contains(pI)){
                        if(pV.equals(viatge.obtDesti())) return (nObligatsVisitats == puntsObligats.size()) && (pV.obtObertura().isAfter(tempsActual.toLocalTime()) || tempsActual.toLocalTime().plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())) && (!tempsActual.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                        else return (pV.obtObertura().isAfter(tempsActual.toLocalTime()) || tempsActual.toLocalTime().plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())) && (!tempsActual.plusMinutes(pV.obtTempsVisita()).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+pV.obtenirPreu() <= viatge.obtPreuMax());
                    }
                    else return false;
                }
                else{
                    Allotjament hotel= (Allotjament)pI;
                    return(LocalDateTime.of(tempsActual.toLocalDate().plusDays(1), LocalTime.of(4, 0)).isAfter(viatge.obtDataMax())) && (ruta.obtCost()+hotel.obtenirPreu() <= viatge.obtPreuMax());
                }
            }
            
            private boolean acceptable(MitjaTransport mitja, Viatge viatge){
                if(mitja instanceof MTDirecte){
                    MTDirecte mD= (MTDirecte)mitja;
                    if(superaMaxim(mD, viatge)) return false;
                    else{
                        LocalDate fi= tempsActual.plusMinutes(mD.getDurada()).toLocalDate();
                        return(fi.equals(viatge.obtDataInici().toLocalDate()) || !fi.isAfter(tempsActual.toLocalDate()));
                    }
                }
                else{
                    throw new UnsupportedOperationException("Not supported yet"); 
                }
            }
            
            private LocalDateTime obtTemps(){
                return tempsActual;
            }
            
            private Ruta obtRuta(){
                return ruta;
            }
            
            private boolean esCompleta(Viatge viatge){
                if(ruta.arribaDesti(viatge.obtDesti())) return nObligatsVisitats == puntsObligats.size();
                else return false;
            }
        }
        
        private static class Candidats{
            private static List<MitjaTransport> mitjans;
            private static Integer listCounter;
            private static PuntInteres pI;
            
            private Candidats(Mapa mundi, PuntInteres pI){
                this.pI= pI;
                mitjans= mundi.obtMitjansPunt(pI);
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
                System.out.println(listCounter);
            }
        }
    }
}
