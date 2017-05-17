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
            actual= new Solucio(viatge.obtDataInici(), viatge);
            optima= null;
            tipusRuta= tipus;
            algRecursiu(mundi, null, viatge.obtOrigen(), viatge.obtDataInici(), viatge);
            if(optima != null){
                System.out.println("He trobat Ruta!");
                return optima.obtRuta();
            }
            else return null;
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres ant, PuntInteres act, LocalDateTime temps, Viatge viatge) {
            List<ItemRuta> items=  mundi.obtenirItemsVeins(ant, act, temps, viatge);
            for(ItemRuta item: items){
                System.out.println(item);
                if(actual.acceptable(item, viatge) && esPotMillorar(actual, optima)){
                    System.out.println("És acceptable");
                    actual.anotar(item);
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, act, item.obtPuntSortida(), actual.obtTemps(), viatge);
                    else{
                        System.out.println("Es completa!!!");
                        if(esMillor(actual, optima)) optima= actual;
                    }
                    actual.desanotar(item);
                }
                else System.out.println("No és acceptable");
            }
        }
        
        private static boolean esPotMillorar(Solucio actual, Solucio optima){
           if(optima == null) return true;
           else return esMillor(actual, optima);
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
            private static Ruta ruta;
            private static LocalDateTime tempsActual;
            private static Set<PuntInteres> visitats;
            private static Map<PuntInteres, Boolean> puntsObligats;
            private static Integer nObligatsVisitats;
                      
            private Solucio(LocalDateTime temps, Viatge viatge){
                nObligatsVisitats= 0;
                tempsActual= temps;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                Iterator<PuntInteres> it= viatge.obtIteradorPI();
                while(it.hasNext()) puntsObligats.put(it.next(), Boolean.FALSE);
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
                System.out.println(ruta.obtCost()+"€ "+ruta.obtDurada()+"m "+ruta.obtSatisfaccio());
            }
            
            private void desanotar(ItemRuta item){
                System.out.println("Desanotem "+item);
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
            
            private boolean acceptable(ItemRuta item, Viatge viatge){
                System.out.println(nObligatsVisitats+" "+puntsObligats.size());
                Integer duradaTempsLliure= (int)Duration.between(tempsActual, item.obtInici()).toMinutes();
                if(ruta.obtDurada()+duradaTempsLliure+item.obtDurada() > viatge.obtDurada() || ruta.obtCost()+item.obtCost() > viatge.obtPreuMax()){
                    return false;
                }
                else if(item instanceof Visita && (visitats.contains(item.obtPuntSortida()) || (item.obtPuntSortida().equals(viatge.obtDesti()) && nObligatsVisitats != puntsObligats.size()))){
                    return false;
                }
                else if((item instanceof TrajecteDirecte || item instanceof TrajecteIndirecte) && !viatge.obtDataInici().toLocalDate().equals(tempsActual.plusMinutes(duradaTempsLliure+item.obtDurada()).toLocalDate())){
                    return false;
                }       
                else return true;
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
