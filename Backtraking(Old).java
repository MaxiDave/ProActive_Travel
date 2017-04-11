//Algorisme Trobar camí mínim (en temps) entre dos punts d'interès passant per punts entremtijos
//© MaxiDave, TheMasterBoss
//Versió: 1.0

Ruta concatenarRutes(Ruta a, Ruta b);
//Pre: --
//Post: Retorna una Ruta que és la concatenació de la Ruta "a" amb la Ruta "b"

Boolean esPotMillorar(Ruta actual, Ruta optima);
//Pre: --
//Post: Retorna cert si hi ha possibilitats que la Ruta "actual" pugui arribar a ser més curta que "optima", fals altrament (En temps)

Boolean esMillor(Ruta actual, Ruta optima);
//Pre: --
//Post: Retorna cert si la Ruta "actual" és més òptima que "optima" (En temps)

Ruta Backtraking(Mapa mundi, PuntInteres origen, PuntInteres desti, Vector<PuntInteres> entreMitjos){
	//Pre: entreMitjos no conté repetits, origen, ni destí
	//Post: Retorna la Ruta mínima en temps entre origen i destí, passant per entreMitjos (No importa ordre entremitjos).
	//		Si no hi ha Ruta, retorna una Ruta nula
	if(!mundi.existeixPuntInteres(origen) && !mundi.existeixPuntInteres(desti)){
		//Tractar excepció Punt Interès inexistent
	}
	else if(origen==desti){
		//Tractar excepció Origen igual a Desti
	}
	else{
		Ruta optima= new Ruta();
		Ruta actual= new Ruta();
		BacktrakingRecursiu(mundi, optima, actual, origen, origen, desti, entreMitjos, entreMitjos);
		if(optima.esCompleta(origen, desti, entreMitjos)) return optima;
		else{
			Ruta nula;
			return nula;
		}
	}
}

void BacktrakingRecursiu(Mapa mundi, Ruta optima, Ruta actual, PuntInteres anterior, PuntInteres origen, PuntInteres desti, Vector<PuntInteres> entreMitjos, Vector<PuntInteres> candidats){
	int iCan= 0;
	while(iCan < candidats.size()){
		PuntInteres candidat= candidats.get(iCan);
		Ruta subRuta= camiMinim(mundi, anterior, candidat);
		if(!subRuta.isEmpty()){	//Si està buida, no hi ha Ruta possible
			Ruta acumulada= concatenarRutes(actual, subRuta);
			if(esPotMillorar(acumulada, optima)){
				candidats.remove(iCan);
				if(!acumulada.esCompleta(origen, desti, entreMitjos)){
					BacktrakingRecursiu(mundi, optima, acumulada, candidat, origen, desti, candidats);
				}
				else{
					if(esMillor(acumulada, optima)) optima= acumulada;
				}
				candidats.add(iCan, candidat);
			}
			iCan++;
		}
		else{
			//Excepció de Ruta no trobada, aturada de la recursió
		}
	}
}


