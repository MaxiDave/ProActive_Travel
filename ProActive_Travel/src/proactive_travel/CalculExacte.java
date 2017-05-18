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
        private static Ruta optima;
        private static Solucio actual;
        private static String tipusRuta;
        
        private static Ruta algBack(Mapa mundi, Viatge viatge, String tipus) {
            tipusRuta= tipus;
            actual= new Solucio(viatge.obtDataInici(), viatge);
            optima= null;
            algRecursiu(mundi, null, viatge.obtOrigen(), viatge.obtDataInici(), viatge);
            if(optima != null) return optima;
            else return null;
        }
        
        private static void algRecursiu(Mapa mundi, ItemRuta ant, PuntInteres act, LocalDateTime temps, Viatge viatge){
            List<ItemRuta> items=  mundi.obtenirItemsVeins(ant, act, temps, viatge, actual.obtVisitats());
            for(ItemRuta item: items){
                //System.out.println(item);
                if(actual.acceptable(item, viatge) && esPotMillorar(actual.obtRuta(), optima)){
                    //System.out.println("És acceptable");
                    actual.anotar(item);
                    if(!actual.esCompleta(viatge)) algRecursiu(mundi, item, item.obtPuntSortida(), actual.obtTemps(), viatge);
                    else{
                        //System.out.println("Es completa!!!");
                        if(esMillor(actual.obtRuta(), optima)) optima= new Ruta(actual.obtRuta());
                    }
                    actual.desanotar(item);
                }
                //else System.out.println("No és acceptable");
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
                      
            private Solucio(LocalDateTime temps, Viatge viatge){
                nObligatsVisitats= 0;
                tempsActual= temps;
                ruta= new Ruta(tipusRuta, temps);
                visitats= new HashSet<>();
                puntsObligats= new HashMap<>();
                puntsObligats.put(viatge.obtOrigen(), Boolean.FALSE);
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
                //System.out.println(ruta.obtCost()+"€ "+ruta.obtDurada()+"m "+ruta.obtSatisfaccio());
                //System.out.println();
            }
            
            private void desanotar(ItemRuta item){
                //System.out.println("Desanotem "+item);
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
                //System.out.println(nObligatsVisitats+" "+puntsObligats.size());
                Integer duradaTempsLliure= (int)Duration.between(tempsActual, item.obtInici()).toMinutes();
                if(ruta.obtDurada()+duradaTempsLliure+item.obtDurada() > viatge.obtDurada() || ruta.obtCost()+item.obtCost() > viatge.obtPreuMax()){
                    return false;
                }
                else if(item instanceof Visita && (visitats.contains(item.obtPuntSortida()) || (item.obtPuntSortida().equals(viatge.obtDesti()) && nObligatsVisitats != puntsObligats.size()) || (tipusRuta.equals("barata") && !puntsObligats.containsKey(item.obtPuntSortida())))){
                    return false;
                }
                else if((item instanceof TrajecteDirecte || item instanceof TrajecteIndirecte) && (tempsActual.plusMinutes(duradaTempsLliure+item.obtDurada()).toLocalTime().isBefore(LocalTime.of(4,0))) && (!viatge.obtDataInici().toLocalDate().equals(tempsActual.plusMinutes(duradaTempsLliure+item.obtDurada()).toLocalDate()))){
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
            
            private Set<PuntInteres> obtVisitats(){
                return visitats;
            }
        }
    }
}
