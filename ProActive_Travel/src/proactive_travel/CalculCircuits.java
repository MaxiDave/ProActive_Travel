package proactive_travel;

import java.time.*;
import java.util.*;

public abstract class CalculCircuits {
    
    /** @pre: --
        @post: Calcula un circuit mitjançant backtraking */
    public static void calcularCircuitBack(Vector<Client> clients,Instant ini,Duration dMax,double pMax,int cMax,Lloc inici,Lloc fi,Vector<Lloc> visitar){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    
    /** @pre: --
        @post: Calcula un circuit mitjançant un algorisme voraç */
    public static void calcularCircuitGreedy(Vector<Client> clients,Instant ini,Duration dMax,double pMax,int cMax,Lloc inici,Lloc fi,Vector<Lloc> visitar){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
}
