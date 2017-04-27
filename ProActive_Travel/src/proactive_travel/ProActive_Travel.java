
package proactive_travel;

import java.util.*;

/**
 * @author David Martínez, Roger Barnés
 */
public class ProActive_Travel {
    public static void main(String[] args) {
        Scanner fitxer= Entrada.entrarNomFitxer();
        List<Client> clients= new ArrayList<Client>();
        Mapa mundi= new Mapa();
        Entrada.inicialitzaAplicatiu(fitxer, clients, mundi);
    }
}
