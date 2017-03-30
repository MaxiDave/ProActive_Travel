/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proactive_travel;

import java.time.*;
import java.util.*;

public class LlocPrimari extends Lloc{
    
    public LlocPrimari(String n, Vector<Preferencia> prefs, Vector<Activitat> acts, boolean vist, double preu, Duration tempsV) {
        super(n, prefs, acts, vist, preu, tempsV);
    }
    
    /** @pre: --
        @post: Retorna cert si el lloc primari conté llocs secundaris associats */
    Boolean teSecundaris(){
        
    }
    
    /** @pre: --
        @post: Retorna un iterador als llocs secundaris del lloc primari */
    Iterator<LlocSecundari> obtenirSecundaris(){
        
    }
    
    /** @pre: --
        @post: Retorna un iterador amb els noms dels transports indirectes disponibles en el lloc */
    Iterator<String> obtenirNomMitjansIndirectes(){
        
    }
}
