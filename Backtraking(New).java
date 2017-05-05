//Algorisme Trobar camí mínim (en temps) entre dos punts d'interès passant per punts entremtijos
//© MaxiDave, TheMasterBoss
//Versió: 2.0

Ruta concatenarRutes(Ruta a, Ruta b);
//Pre: --
//Post: Retorna una Ruta que és la concatenació de la Ruta "a" amb la Ruta "b"

Boolean esPotMillorar(Ruta actual, Ruta optima);
//Pre: --
//Post: Retorna cert si hi ha possibilitats que la Ruta "actual" pugui arribar a ser més curta que "optima", fals altrament (En temps)

Boolean esMillor(Ruta actual, Ruta optima);
//Pre: --
//Post: Retorna cert si la Ruta "actual" és més òptima que "optima" (En temps)


public static Ruta rutaMesBreuPassantPer(Mapa mundi, PuntInteres origen, PuntInteres desti, Set<PuntInteres> aVisitar){
	//Pre: --
	//Post: Retorna cert si la Ruta "actual" és més òptima que "optima" (En temps)
	// Pre: origen i desti no son dins aVisitar. aVisitar buida => origen diferent de desti
	// Post: Retorna la ruta més curta entre origen i desti que passi per tots els punts dins aVisitar. 
	//		 Si no existeix, retorna null
	Ruta r= new Ruta();
	if(aVisitar.isEmpty()) r= camiMinim(mundi, origen, desti);
	else{
		int l= Integer.MAX_VALUE; //Menor longitud
		TreeSet<PuntInteres> aVisitar2= new TreeSet(aVisitar);
		for(PuntInteres i:c){
			Ruta r1= camiMinim(mundi, origen, i);
			if(r1.isEmpty()) break; //No hi ha camiMinim
			else{
				aVisitar2.remove(i);
				aVisitar2.removeAll(r1);
				Ruta r2= rutaMesBreuPassantPer(mundi, i, desti, aVisitar2);
				aVisitar2.add(i);
				aVisitar2.addAll(r1);
				if(!r2.isEmpty() && esMillor(concatenar(r1, r2), r)) r= concatenar(r1, r2);
			}
		}			
	}
	return r;
}

/*
	if(aVisitar.isEmpty()){
		return camiMinim(mundi, origen, desti);
	}
	else{
		Ruta actual= new Ruta();
		Ruta optima= NULL;
		Set<PuntInteres> entreMitjos= new HashSet<PuntInteres>(aVisitar);

		for(PuntInteres pI : aVisitar){
			actual= camiMinim(mundi, origen, pI);
			if(actual != NULL){
				for(PuntInteres punt : aVisitar){
					if(actual.passaPer(punt)) entreMitjos.remove(punt);
				}
				if(esPotMillorar(actual, optima)){
					actual= concatenarRutes(actual, rutaMesBreuPassantPer(mundi, pI, desti, entreMitjos));
					if(esMillor(actual, optima)) optima= actual;
				}
			}
			else{
				//Excepció de Ruta no trobada, aturada de la recursió i retorn de null
			}
		}
		return optima;
	}
	*/