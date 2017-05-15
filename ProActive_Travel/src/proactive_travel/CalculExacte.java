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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs exactes
 */
public abstract class CalculExacte {
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /** 
     * @pre: --
     * @post: Calcula i retorna rutes a partir del mapa i viatge mitjançant backtraking
     */
    public static List<Ruta> calcularRutaBack(Mapa mundi, Viatge viatge){
        List<Ruta> rutes= new ArrayList<>();
        if(viatge.esCurta()) rutes.add(Solucionador.algBack(mundi, viatge, "curta"));
        if(viatge.esBarata()) rutes.add(Solucionador.algBack(mundi, viatge, "barata"));
        if(viatge.esSatisfactoria()) rutes.add(Solucionador.algBack(mundi, viatge, "sat"));
        return rutes;
    }
    
    private static abstract class Solucionador{
        private static Solucio optima;
        private static Solucio actual;
        private static String tipusRuta;
        
        private static Ruta algBack(Mapa mundi, Viatge viatge, String tipus) {
            actual= new Solucio(viatge.obtDataInici());
            optima= null;
            tipusRuta= tipus;
            algRecursiu(mundi, viatge.obtOrigen(), viatge.obtDataInici(), viatge);
            if(optima.esCompleta(viatge))return optima.obtRuta();
            else return null;
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres anterior, LocalDateTime temps, Viatge viatge) {
            List<ItemRuta> items=  mundi.obtenirItemsVeins(anterior, temps, viatge, tipusRuta);
            for(ItemRuta item: items){
                if(actual.acceptable(item, viatge) && esPotMillorar(actual, optima)){
                    actual.anotar(item);
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, item.obtPuntSortida(), actual.obtTemps(), viatge);
                    else{
                        if(esMillor(actual, optima)) optima= actual;
                    }
                    actual.desanotar(item);
                }
            }
        }
        
        private static boolean esPotMillorar(Solucio actual, Solucio optima){
            if(optima == null) return true;
            else{
                throw new UnsupportedOperationException("Not supported yet");
            }
        }
        
        private static boolean esMillor(Solucio actual, Solucio optima){
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
            private Ruta ruta;
            private LocalDateTime tempsActual;
            private Set<PuntInteres> visitats;
                      
            private Solucio(LocalDateTime temps){
                tempsActual= temps;
                visitats= new HashSet<PuntInteres>();
            }
            
            private void anotar(ItemRuta item){
                ruta.afegeixItemRuta(item);
                tempsActual= tempsActual.plusMinutes(item.obtDurada());
                visitats.add(item.obtPuntSortida());
            }
            
            private void desanotar(ItemRuta item){
                tempsActual= tempsActual.minusMinutes(item.obtDurada());
                ruta.treureUltimItem();
                visitats.remove(item.obtPuntSortida());
            }
            
            private boolean acceptable(ItemRuta item, Viatge viatge){
                Integer duradaTempsLliure= (int)Duration.between(tempsActual, item.obtInici()).toMinutes();
                if(ruta.obtDurada()+duradaTempsLliure+item.obtDurada() > viatge.obtDurada() || ruta.obtCost()+item.obtCost() > viatge.obtPreuMax()) return false;
                else{
                    if(item instanceof Visita){
                        if(visitats.contains(item)) return false;
                        else{
                            LocalTime fi= item.obtFinal().toLocalTime();
                            if(fi.isAfter(LocalTime.of(0, 0))) return false;
                            else return true;
                        }
                    }
                    else if(item instanceof EstadaHotel){
                        if(tempsActual.toLocalTime().isAfter(LocalTime.of(0, 0))) return false;
                        else return true;
                    }
                    else throw new UnsupportedOperationException("Not supported yet");
                }
            }
            
            private LocalDateTime obtTemps(){
                return tempsActual;
            }
            
            private Ruta obtRuta(){
                return ruta;
            }
            
            private boolean esCompleta(Viatge viatge){
                if(ruta.obtDesti().equals(viatge.obtDesti())){
                    Iterator<PuntInteres> it= viatge.obtIteradorPI();
                    boolean completa= true;
                    while(it.hasNext() && completa){
                        completa= visitats.contains(it.next());
                    }
                    return completa;
                }
                else return false;
            }
            
            private Integer obtDurada(){
                return ruta.obtDurada();
            }
            
            private Integer obtSatisfaccio(){
                return ruta.obtSatisfaccio();
            }
            
            private Double obtCost(){
                return ruta.obtCost();
            }
        }
    }
}
