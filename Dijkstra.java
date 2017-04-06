//Algorisme Trobar camí mínim (en temps) entre dos punts d'interès
//© MaxiDave, TheMasterBoss
//Versió: 1.1

Ruta camiMinim(Mapa mundi, PuntInteres origen, PuntInteres desti){
	//Pre: --
	//Post: Retorna una Ruta amb el camí més curt en temps entre origen i destí. 
	//		Si no hi ha camí, retorna una Ruta buida
	if(!mundi.existeixPuntInteres(origen) && !mundi.existeixPuntInteres(desti)){
		//Tractar excepció Punt Interès inexistent
	}
	else if(origen==desti){
		//Tractar excepció Origen igual a Desti
	}
	else{
            int nPunts= mundi.nPuntsInteres();
            Vector<Double> temps= new Vector<Double>(); temps.setSize(nPunts+1);
            Vector<PuntInteres> pred= new Vector<PuntInteres>(); pred.setSize(nPunts+1);
            Vector<Boolean> visitat= new Vector<Boolean>(); visitat.setSize(nPunts+1);
            Vector<Trajecte> traj= new Vector<Trajecte>; traj.setSize(nPunts+1);

            for(int i=1; i <= nV; i++){
                temps.add(i, -1.0);
                pred.add(i, origen);
                visitat.add(i, false);
            }
            temps.set(origen.obtenirCodi(), 0.0);

            Deque<PuntInteres> cua= new ArrayDeque<PuntInteres>();
            cua.addLast(origen);

            while(!cua.isEmpty() && !visitat.get(desti.obtenirCodi())){
                PuntInteres puntMinCua= NULL; 
                double tempsMin= 0;
            
                for(PuntInteres puntI : cua){
                    if(puntMinCua == NULL){
                        puntMinCua= new PuntInteres();
                	puntMinCua= puntI;
                    } 
                    else if(temps.get(puntI.obtenirCodi()) < temps.get(puntMinCua.obtenirCodi())){
                        puntMinCua= puntI;
                    }
            }

            cua.remove(puntMinCua);
            
            visitat.set(puntMinCua.obtenirCodi(), true);
            Map<PuntInteres, Trajecte> veinsTemps= mundi.obtenirDesplsMin(puntMinCua, "temps");
            for(Map.Entry<PuntInteres, Trajecte> puntTemps: veinsTemps.entrySet()){
                PuntInteres dest= puntTemps.getKey();
                Trajecte desp= puntTemps.getValue();
                Double tempsDesp= desp.getDurada();
                Integer codiPunt= dest.obtenirCodi();
                if((!visitat.get(codiPunt)) && ((temps.get(codiPunt) == -1) || (temps.get(codiPunt) > temps.get(puntMinCua.obtenirCodi())+tempsDesp))){
                    temps.set(codiPunt, temps.get(puntMinCua.obtenirCodi())+tempsDesp);
                    pred.set(codiPunt, puntMinCua);
                    traj.set(codiPunt, desp);
                    cua.addLast(dest);
                }
            }
        }
        cua.clear();

        Ruta cami= new Ruta();

        if(visitat.get(desti.obtenirCodi())){
            PuntInteres puntAct= desti;
            Deque<PuntInteres> pilaRuta= new ArrayDeque<PuntInteres>();
            while(puntAct != origen){
                pilaRuta.addFirst(puntAct);
                puntAct= pred.get(puntAct.obtenirCodi());
            }
            pilaRuta.addFirst(origen);

		    PuntInteres ant= pilaRuta.removeFirst();
		    PuntInteres act= pilaRuta.removeFirst();
		    if(pilaRuta != null){
			    while(pilaRuta.peekFirst()!=null){
				Integer codiAct= act.obtenirCodi();
				Trajecte t= traj.get(codiAct);
				cami.afegeixSubtrajecte(t);
				ant=act;
				act=pilaRuta.removeFirst();
			}
	    	else{
				Integer codiAct= act.obtenirCodi();
				Trajecte t= traj.get(codiAct);
				cami.afegeixSubtrajecte(t);
	    	}
            pilaRuta.clear();
        }
        return cami;
	}
}
