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
 * @brief: Classe que retorna els camins mes rapids/barats/satisfactoris a seguir
 */
public class Dijkstra {
    private Set<PuntInteres> nodesAgafats;                      ///< @brief: Nodes per on dijlstra ha passat
    private Set<PuntInteres> nodesPerAgafar;                    ///< @brief: Nodes que dijkstra te com a condidats

    private Map<PuntInteres,PuntInteres> predecessors;          ///< @brief: PuntInteres i el seu predecessor
    private Map<PuntInteres,MitjaTransport> transports;         ///< @brief: PuntInteres i el millor transport per anarhi
    
    private Map<PuntInteres,Integer> temps;                     ///< @brief: PuntInteres i el temps que es tarda a arribar
    private Map<PuntInteres,Double> cost;                       ///< @brief: PuntInteres i el cost per arribar
    private Map<PuntInteres,Integer> satisfaccio;               ///< @brief: PuntInteres i la satisfaccio que dona
    
    private String tipus;                                       ///< @brief: Tipus de la ruta que esta fent
    private PuntInteres dest;                                   ///< @brief: Punt desti
    
    /**
     * @pre --
     * @post Totes les variables queden inicialitzades
     * @brief: Variables inicialitzades
     */
    Dijkstra() {
	nodesAgafats = new HashSet<PuntInteres>();
	nodesPerAgafar = new HashSet<PuntInteres>();
	temps = new HashMap<PuntInteres, Integer>();
        cost = new HashMap<PuntInteres, Double>();
	predecessors = new HashMap<PuntInteres, PuntInteres>();
        transports = new HashMap<PuntInteres, MitjaTransport>();
        satisfaccio = new HashMap<PuntInteres, Integer>();
        tipus = new String();
    }
    
    /**
     * @pre S'ha de haver cridat camiMinim
     * @post Retorna els punts de interes que dijkstra ha trobat mes rapids/barats/satisfactoris
     * @brief: Retorna els punts d'interes que conformaran la ruta
     */
    public ArrayDeque<PuntInteres> retornaPuntsInteres(){
        ArrayDeque<PuntInteres> invers = new ArrayDeque();
        PuntInteres d=dest;
        invers.add(d);
        while(predecessors.containsKey(d)){
            invers.add(predecessors.get(d));
            d=predecessors.get(d);
        }
        //invers.add(d);
        
        ArrayDeque<PuntInteres> cami = new ArrayDeque();
        while(!invers.isEmpty()){
            cami.add(invers.pollLast());
        }
        
        return cami;
    }
    
    /**
     * @pre S'ha d'haver cridat camiMinim
     * @post Retorna un mapa amb els PuntsInteres i el mitja de transport millor per arribarhi
     * @brief: Retorna el millor mitja de transport per cada punt d'interes
     */
    public Map<PuntInteres, MitjaTransport> retornaMitjans(){
        //HashMap<PuntInteres,Mitjatransport> camiMT = new HashMap();
        return transports;
    }
    
    /**
     * @pre S'ha d'haver cridat camiMinim
     * @post Retorna el temps que es tarda fins a desti
     * @brief: Retorna el temps
     */
    public Integer retornaTemps(){
        return temps.get(dest);
    }
    
    /**
     * @pre S'ha d'haver cridat camiMinim
     * @post Retorna el cost fins a desti
     * @brief: Retorna el cost
     */
    public Double retornaCost(){
        return cost.get(dest);
    }
    
    /**
     * @pre S'ha d'haver cridat camiMinim
     * @post Retorna la satisfaccio fins a desti
     * @brief: Retorna la satisfaccio
     */
    public Integer retornaSatisfaccio(){
        return satisfaccio.get(dest);
    }
    
    /**
     * @pre --
     * @post Calcula el cami minim de un origen a un desti aplicant dijkstra, retorna un -1 si no hi ha ruta i 0 si hi ha ruta
     * @brief: Calcula el cami minim entre dos punts
     */
    public Integer camiMinim(Mapa mundi, PuntInteres origen, PuntInteres desti, String tipusDij){
        dest=desti;
        if(tipusDij.equals("temps")) tipus="temps";
        else if(tipusDij.equals("diners")) tipus="diners";
        else tipus="satisfaccio";
	temps.put(origen,0);
        cost.put(origen, 0.0);
	nodesPerAgafar.add(origen);
	while(nodesPerAgafar.size()>0 && !(nodesAgafats.contains(desti))){
            PuntInteres pi = buscarMinim(origen,nodesPerAgafar,mundi);
            nodesAgafats.add(pi);
            nodesPerAgafar.remove(pi);
            if(tipus.equals("temps")) buscarDistanciesMinimes(mundi,pi);
            else if(tipus.equals("diners")) buscarCostsMinims(mundi,pi);
            else buscarSatisfaccioMaxima(mundi,pi);
	}
        if(!nodesAgafats.contains(desti)){
            return -1;
        }
        return 0;
    }
    
    /**
     * @pre --
     * @post Retorna el millor punt per fixar
     * @brief: Busca el millor punt per agafar
     */
    private PuntInteres buscarMinim(PuntInteres origen, Set<PuntInteres> nodesPerAgafar, Mapa mundi){
	PuntInteres minim = null;
	for(PuntInteres p : nodesPerAgafar){
            if(minim==null){
		minim=p;
            }
            else{
                if(tipus.equals("temps")){
                    if(mundi.obtenirDespl(origen, p,transports,false) < mundi.obtenirDespl(origen, minim, transports,false)){ //Obtenir despl
                        minim=p;
                    }
                }
                else if(tipus.equals("diners")){
                    if(mundi.obtenirCostDespl(origen, p,transports,false) < mundi.obtenirCostDespl(origen, minim,transports,false)){ //Obtenir despl
                        minim=p;
                    }
                }
                else{
                    if(mundi.obtenirDespl(origen, p,transports,false) > mundi.obtenirDespl(origen, minim,transports,false)){ //Obtenir despl
                        minim=p;
                    }
                }
            }
        }

        return minim;
    }
        
    /**
     * @pre --
     * @post Actualitza els veins de un dijkstra per temps
     * @brief: Actualitza veins per temps
     */
    private void buscarDistanciesMinimes(Mapa mundi, PuntInteres pi){
        if (mundi.obtenirVeins(pi) != null) {
            Set<PuntInteres> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
            //Veins MTDir
            for (PuntInteres p : nodesVeins) {
                if (obtenirTemps(p) > obtenirTemps(pi) + mundi.obtenirDespl(pi, p,transports,false)) {
                    temps.put(p, obtenirTemps(pi) + mundi.obtenirDespl(pi, p,transports,true));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
        //Veins MTUrba
        if(pi!=null){
            Set<PuntInteres> nodesVeinsUrba = mundi.obtenirVeinsUrba(pi);
            for (PuntInteres p : nodesVeinsUrba) {
                if (obtenirTemps(p) > obtenirTemps(pi) + mundi.obtenirDespl(pi, p,transports,false)) {
                    temps.put(p, obtenirTemps(pi) + mundi.obtenirDespl(pi, p,transports,true));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
    }
    
    /**
     * @pre --
     * @post Actualitza els veins de un dijkstra per cost
     * @brief: Actualitza veins per cost
     */
    private void buscarCostsMinims(Mapa mundi, PuntInteres pi){
	if(mundi.obtenirVeins(pi)!=null){
            Set<PuntInteres> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
            //Veins MTDir
            for (PuntInteres p : nodesVeins) {;
                if (obtenirCost(p) > obtenirCost(pi) + mundi.obtenirCostDespl(pi, p,transports,false)) {
                    cost.put(p, obtenirCost(pi) + mundi.obtenirCostDespl(pi, p,transports,true));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
        //Veins TUrba
        if(pi!=null){
            Set<PuntInteres> nodesVeinsUrba = mundi.obtenirVeinsUrba(pi);
            for (PuntInteres p : nodesVeinsUrba) {
                if (obtenirCost(p) > obtenirCost(pi) + mundi.obtenirCostDespl(pi, p,transports,false)) {
                    cost.put(p, obtenirCost(pi) + mundi.obtenirCostDespl(pi, p,transports,true));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
    }
    
    /**
     * @pre --
     * @post Actualitza els veins de un dijkstra per satisfaccio
     * @brief: Actualitza veins per satisfaccio
     */
    private void buscarSatisfaccioMaxima(Mapa mundi, PuntInteres pi) {
        if(mundi.obtenirVeins(pi)!=null){
            Set<PuntInteres> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
            //Veins MTDir
            for (PuntInteres p : nodesVeins) {;
                if (obtenirSatisfaccio(p) < obtenirSatisfaccio(pi) + obtenirSatisfaccio(p)) {
                    satisfaccio.put(p, obtenirSatisfaccio(pi) + obtenirSatisfaccio(p));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
        //Veins TUrba
        if(pi!=null){
            Set<PuntInteres> nodesVeinsUrba = mundi.obtenirVeinsUrba(pi);
            for (PuntInteres p : nodesVeinsUrba) {
                if (obtenirSatisfaccio(p) > obtenirSatisfaccio(pi) + obtenirSatisfaccio(p)) {
                    satisfaccio.put(p, obtenirSatisfaccio(pi) + obtenirSatisfaccio(p));
                    predecessors.put(p, pi);
                    nodesPerAgafar.add(p);
                }
            }
        }
    }
    
    /**
     * @pre --
     * @post Retorna el temps que es tarda en arribar en aquell punt, Integer.MAX_VALUE si no es pot arribar
     * @brief: Retorna el temps per anar a un punt
     */
    private int obtenirTemps(PuntInteres pi){
	Integer t = temps.get(pi);
	if(t==null){
		return Integer.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
    }
    
    /**
     * @pre --
     * @post Retorna el cost que es te en arribar en aquell punt, Double.MAX_VALUE si no es pot arribar
     * @brief: Retorna el cost per anar a un punt
     */
    private Double obtenirCost(PuntInteres pi){
	Double t = cost.get(pi);
	if(t==null){
		return Double.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
    }

    /**
     * @pre --
     * @post Retorna la satisfaccio que es proporciona arribar en aquell punt, Int.MAX_VALUE si no es pot arribar
     * @brief: Retorna la satisfaccio per anar a un punt
     */
    private Integer obtenirSatisfaccio(PuntInteres pi) {
        Integer t = satisfaccio.get(pi);
	if(t==null){
		return Integer.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
    }
}
