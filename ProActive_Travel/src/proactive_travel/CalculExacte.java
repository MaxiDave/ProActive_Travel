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
        List<Ruta> rutes= new ArrayList<Ruta>();
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
            actual= new Solucio(viatge, tipus, viatge.obtDataInici());
            optima= null;
            tipusRuta= tipus;
            algRecursiu(mundi, viatge.obtOrigen(), actual.obtTemps());
            return optima.obtRuta();
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres anterior, LocalDateTime temps) {
            Set<ItemRuta> items=  mundi.obtenirItemVeins(anterior, temps);
            for(ItemRuta item: items){
                if(actual.acceptable(item) && esPotMillorar(actual, optima)){
                    actual.anotar(item);
                    if(!actual.esCompleta()) algRecursiu(mundi, item.obtSortida(), actual.obtTemps());
                    else{
                        if(esMillor(actual, optima)) optima= actual;
                    }
                    actual.desanotar();
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
            private Viatge viatge;
            private final String tipus;
            private Set<PuntInteres> visitats;
            
            
            private Solucio(Viatge v, String t, LocalDateTime temps){
                viatge= v;
                tipus= t;
                tempsActual= temps;
                visitats= new HashSet<PuntInteres>();
            }
            
            private boolean acceptable(ItemRuta item){
                throw new UnsupportedOperationException("Not supported yet");
            }
            
            private LocalDateTime obtTemps(){
                return tempsActual;
            }
            
            private Ruta obtRuta(){
                return ruta;
            }
            
            private boolean esCompleta(){
                throw new UnsupportedOperationException("Not supported yet");
            }
            
            private Integer obtDurada(){
                throw new UnsupportedOperationException("Not supported yet");
            }
            
            private Integer obtSatisfaccio(){
                throw new UnsupportedOperationException("Not supported yet");
            }
            
            private Double obtCost(){
                throw new UnsupportedOperationException("Not supported yet");
            }
        }
    }
}
