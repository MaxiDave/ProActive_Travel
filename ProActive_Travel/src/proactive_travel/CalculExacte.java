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
    public static Ruta calcularRutaBack(Mapa mundi, PuntInteres origen, PuntInteres desti, Set<PuntInteres> aVisitar){        
        //Pre: --
	//Post: Retorna cert si la Ruta "actual" és més òptima que "optima" (En temps)
	// Pre: origen i desti no son dins aVisitar. aVisitar buida => origen diferent de desti
	// Post: Retorna la ruta més curta entre origen i desti que passi per tots els punts dins aVisitar. 
	//		 Si no existeix, retorna null
        throw new UnsupportedOperationException("Not supported yet"); 
        /*
	Ruta r= new Ruta();
	if(aVisitar.isEmpty()) r= camiMinim(mundi, origen, desti);
	else{
		int l= Integer.MAX_VALUE; //Menor longitud
		TreeSet<PuntInteres> aVisitar2= new TreeSet(aVisitar);
		for(PuntInteres i:aVisitar){
			Ruta r1= camiMinim(mundi, origen, i);
			if(r1.isEmpty()) break; //No hi ha camiMinim
			else{
				aVisitar2.remove(i);
				aVisitar2.removeAll(r1);
				Ruta r2= rutaMesBreuPassantPer(mundi, i, desti, aVisitar2);
				aVisitar2.add(i);
				aVisitar2.addAll(r1);
				if(!r2.isEmpty() && CalculRuta.esMillor(CalculRuta.concatenar(r1, r2), r)) r= CalculRuta.concatenar(r1, r2);
			}
		}			
	}
	return r;
*/
    }
}
