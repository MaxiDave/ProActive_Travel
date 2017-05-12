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
        
        private static Ruta algBack(Mapa mundi, Viatge viatge, String tipus) {
            actual= new Solucio(viatge, tipus, viatge.obtDataInici());
            optima= null;
            algRecursiu(mundi, viatge.obtOrigen(), actual.obtTemps());
            return optima.obtRuta();
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres anterior, LocalDateTime temps) {
            Set<ItemRuta> items=  mundi.obtenirItemVeins(anterior, temps);
            for(ItemRuta item: items){
                if(actual.acceptable(item) && esPotMillorar(optima, actual)){
                    actual.anotar(item);
                    if(!actual.completa()) algRecursiu(mundi, item.obtSortida(), actual.obtTemps());
                    else{
                        if(esMillor(actual, optima)) optima= actual;
                    }
                    actual.desanotar();
                }
            }
        }
        
        private static class Solucio{
            private Ruta ruta;
            private LocalDateTime tempsActual;
            private Viatge viatge;
            private final String tipus;
            private boolean completa; 
            
            public Solucio(Viatge v, String t, LocalDateTime temps){
                viatge= v;
                tipus= t;
                tempsActual= temps;
                completa= false;
            }
            
            public LocalDateTime obtTemps(){
                return tempsActual;
            }
            
            public Ruta obtRuta(){
                return ruta;
            }
        }
    }
}
