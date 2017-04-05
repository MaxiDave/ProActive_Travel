//Algorisme Trobar camí mínim (en temps) entre dos punts d'interès
//© MaxiDave, TheMasterBoss
//Versió: 1.0

//FALTA: Acabar de quadrar com generarem les rutes (Problema trajectes/desplaçaments intermitjos)

Ruta Dijkstra(Mapa mundi, PuntInteres origen, PuntInteres desti){
	if(!mundi.existeixPuntInteres(origen) && !mundi.existeixPuntInteres(desti)){
		//Tractar excepció Puunt Interès inexistent
	}
	else if(origen==desti){
		//Tractar excepció Origen igual a Desti
	}
	else{
            int nPunts= mundi.nPuntsInteres();
            Vector<Double> temps= new Vector<Double>(); temps.setSize(nPunts+1);
            Vector<PuntInteres> pred= new Vector<PuntInteres>(); pred.setSize(nPunts+1);
            Vector<Boolean> visitat= new Vector<Boolean>(); visitat.setSize(nPunts+1);
            Vector<Desplaçament> despl= new Vector<Desplaçament>; despl.setSize(nPunts+1);

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
            Map<PuntInteres, Desplaçament> veinsTemps= mundi.obtenirDesplsMin(puntMinCua, "temps");
            for(Map.Entry<PuntInteres, Desplaçament> puntTemps: veinsTemps.entrySet()){
                PuntInteres dest= puntTemps.getKey();
                Desplaçament desp= puntTemps.getValue();
                Double tempsDesp= desp.obtenirDurada();
                Integer codiPunt= dest.obtenirCodi();
                if((!visitat.get(codiPunt)) && ((temps.get(codiPunt) == -1) || (temps.get(codiPunt) > temps.get(puntMinCua.obtenirCodi())+tempsDesp))){
                    temps.set(codiPunt, temps.get(puntMinCua.obtenirCodi())+tempsDesp);
                    pred.set(codiPunt, puntMinCua);
                    despl.set(codiPunt, desp);
                    cua.addLast(dest);
                }
            }
        }

        cua.clear();
        if(visitat.get(desti.obtenirCodi())){
            PuntInteres puntAct= desti;
            Deque<PuntInteres> pilaRuta= new ArrayDeque<PuntInteres>();
            while(puntAct != origen){
                pilaRuta.addFirst(puntAct);
                puntAct= pred.get(puntAct.obtenirCodi());
            }
            pilaRuta.addFirst(origen);
            Ruta cami= new Ruta();

	    PuntInteres a = pilaRuta.removeFirst();
	    PuntInteres b = pilaRuta.removeFirst();
	    if(pilaRuta != null){
		    while(pilaRuta.peekFirst()!=null){
			Integer codib = b.obtenirCodi();
			Integer desplb = despl[codib];
			Trajecte t(a,b,desplb);
			cami.afegeixTrajecte(t);
			a=b;
			b=pilaRuta.removeFirst();
		    }
	    }
	    else{
		Integer codib = b.obtenirCodi();
		Integer desplb = despl[codib];
		Trajecte t(a,b,desplb);
		cami.afegeixTrajecte(t);
	    }
            pilaRuta.clear();
            return cami;
        }
        else{
        	//Tractar excepció camí no trobat
        }
	}
}
