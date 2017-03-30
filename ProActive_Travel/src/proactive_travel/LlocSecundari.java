/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.time.Duration;
import java.util.Vector;


public class LlocSecundari extends Lloc{
    
    public LlocSecundari(String n, Vector<Preferencia> prefs, Vector<Activitat> acts, boolean vist, double preu, Duration tempsV) {
        super(n, prefs, acts, vist, preu, tempsV);
    }
    
    /** @pre: --
        @post: Retorna el nom del LlocPrimari al que està associat */
    String obtenirnomPrimari(){
        
    }
}
