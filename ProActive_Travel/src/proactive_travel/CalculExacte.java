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

        private static void algBack(Mapa mundi, Viatge viatge, String curta) {
            /*
            ESQUEMA Backtracking Recursiu Solucio Optima
                Inicialitzar Conjunt De Candidats
                MENTRE Queden Candidats FER
                    SI Acceptable AND Es Pot Trobar Solucio Millor LLAVORS
                        Anotar Candidat
                        SI NO Solucio Completa LLAVORS
                            Backtracking Pas Seguent
                        ALTRAMENT
                            SI Solucio Actual Millor Que Optima LLAVORS
                                 Optima Es Solucio Actual
                            FSI
                        FSI
                    Desanotar Candidat
                    FSI
                Seguent Candidat
                FMENTRE
            FESQUEMA
            */
        }
        
    }
}
