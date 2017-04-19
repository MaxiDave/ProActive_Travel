//Algorisme cami minim (temps) entre dos punts de interes
//David Martinez i Roger Barnes
//Versio 1.2

private Set<PuntInteres> nodesAgafats;
private Set<PuntInteres> nodesPerAgafar;

private Map<PuntInteres,PuntInteres> predecessors;
private Map<PuntInteres,Integer> temps;

private MTDirecte MtDir;

Dijkstra() {
	nodesAgafats = new HashSet<PuntInteres>();
	nodesPerAgafar = new HashSet<PuntInteres>();
	temps = new HashMap<PuntInteres, Double>();
	predecessors = new HashMap<PuntInteres, PuntInteres>();
}

public Ruta camiMinim(Mapa mundi, PuntInteres origen, PuntInteres desti){
	temps.put(origen,0);
	nodesPerAgafar.add(origen);
	while(nodesPerAgafar.size()>0 && !(nodesAgafats.contains(desti))){
		PuntInteres pi = buscarMinim(origen,nodesPerAgafar);
		nodesAgafats.add(pi);
		nodesPerAfegir.remove(pi);
		buscarDistanciesMinimes(mundi,pi);
	}
}

private PuntInteres buscarMinim(PuntInteres origen, Set<PuntInteres> nodesPerAgafar){
	PuntInteres minim = NULL;
	for(PuntInteres p : nodesPerAgafar){
		if(minim==NULL){
			minim=p;
		}
		else{
			if(MtDir.obtenirDespl(origen, p) < MtDir.obtenirDespl(origen, minim)){ //Obtenir despl
				minim=p;
			}
		}
		return minim;
}

private void buscarDistanciesMinimes(Mapa mundi, PuntInteres pi){
	List<Vertex> nodesVeins = mundi.obtenirVeins(pi); //obtenir veins
	for(PuntInteres p : nodesVeins){
		if(obtenirTemps(p) > obtenirTemps(pi) + MtDir.obtenirDesp(pi,p)){
			temps.put(p, obtenirTemps(pi) + MtDir.obtenirDesp(pi,p));
			predecessor.put(p,pi);
			nodesPerAfegir(p);
		}
	}
}

private obtenirTemps(PuntInteres pi){
	Integer t = temps.get(pi);
	if(t==null){
		return Integer.MAX_VALUE; //Valor maxim
	}
	else{
		return t;
	}
}
