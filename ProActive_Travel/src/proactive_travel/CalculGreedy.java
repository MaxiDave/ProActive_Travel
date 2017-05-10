//ProActive_Travel

/**
 * @file: CalculGreedy.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Mòdul funcional que s'encarrega de dur a terme els càlculs relacionats en trobar
 *         una bona solució (Greedy) de Ruta, en termes monetaris, temps i Satisfacció 
 * @copyright: Public License
 */

package proactive_travel;

import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Classe abstracte que s'encarrega dels càlculs aproximats
 */
public abstract class CalculGreedy {
    
    private Set<PuntInteres> pI;
    private final PuntInteres origen;
    private final PuntInteres desti;
    
    //MÈTODES ESTÀTICS---------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Calcula una Ruta mitjançant un algorisme voraç
     */
    public static Ruta calcularRutaGreedy(Viatge clients,Mapa mundi){
        throw new UnsupportedOperationException("Not supported yet");        
    }
}
