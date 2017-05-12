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
     * @post: Calcula una Ruta mitjançant backtraking
     */
    public static Ruta calcularRutaBack(Mapa mundi, Viatge viatge){        
        if(viatge.esCurta()) Solucionador.algBack(mundi, viatge, "curta");
        if(viatge.esBarata()) Solucionador.algBack(mundi, viatge, "barata");
        if(viatge.esSatisfactoria()) Solucionador.algBack(mundi, viatge, "sat");
        return null;
    }
    
    private static abstract class Solucionador{
        private static Solucio optima;
        private static Solucio actual;
        
        private static void algBack(Mapa mundi, Viatge viatge, String tipus) {
            actual= new Solucio(viatge, tipus, viatge.obtDataInici());
            algRecursiu(mundi, viatge.obtOrigen(), actual.obtTemps());
        }
        
        private static void algRecursiu(Mapa mundi, PuntInteres anterior, LocalDateTime temps) {
            Set<Trajecte> veins=  mundi.obtenirVeinsTransports(anterior, temps);
            for(Trajecte t : veins){
                if(actual.acceptable(t) && calculRuta.esPotMillorar(optima, actual)){
                    actual.anotar(t);
                    if(!actual.completa()) algRecursiu(mundi, t.getDesti(), actual.obtTemps());
                    else{
                        if(esMillor(actual, optima)) optima= actual;
                    }
                    actual.desanotar();
                }
            }
        }
        
        private static class Solucio{
            private static LocalDateTime tempsActual;
            private static Viatge viatge;
            private static String tipus;
            private static boolean completa= false; 
            
            public Solucio(Viatge v, String t, LocalDateTime temps){
                viatge= v;
                tipus= t;
                tempsActual= temps;
            }
            
            public LocalDateTime obtTemps(){
                return tempsActual;
            }
        }
    }
}
