//ProActive_Travel

/**
 * @file Mapa.java
 * @author Roger Barnés, u1939667
 * @author David Martínez, u1939690
 * @version 1
 * @date Curs 2016-2017
 * @brief Classe Mapa: Conté informació de tot el relacionat amb dades geogràfiques i transports
 * @copyright Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief Representa un Mapa, amb les estructures de dades corresponents 
 */
public class Mapa {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private Map<String, Lloc> llocs;                                            ///< @brief Mapa de llocs
    private Map<String, PuntInteres> punts;                                     ///< @brief Mapa de punts
    private Map<PuntInteres, Map<PuntInteres, Set<MTDirecte>>> transDirecte;    ///< @brief Mapa de conexions PuntInteres a PuntInteres amb MTDirecte
    private Map<PuntInteres, List<MitjaTransport>> puntsPerDurada;              ///< @brief Mapa de PuntInteres amb els seus transports ordenats per temps
    private Map<PuntInteres, List<MitjaTransport>> puntsPerPreu;                ///< @brief Mapa de PuntInteres amb els seus transports ordenats per preu
    private Map<Estacio, List<MitjaTransport>> estacionsPerDurada;              ///< @brief Mapa de les Estacions amb els seus transports ordenats per temps
    private Map<Estacio, List<MitjaTransport>> estacionsPerPreu;                ///< @brief Mapa de les Estacions amb els seus transports ordenats per temps
    private Map<Estacio, Map<Lloc, List<MTPunts>>> estacioAPunts;               ///< @brief Mapa de les Estacions amb els seus destins
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre --
     * @post Crea un mapa buit
     * @brief Constructor
     */
    public Mapa(){
        llocs= new HashMap<>();
        punts= new HashMap<>();
        transDirecte= new HashMap< >();
        puntsPerDurada= new HashMap<>();
        puntsPerPreu= new HashMap<>();
        estacionsPerDurada= new HashMap<>();
        estacionsPerPreu= new HashMap<>();
        estacioAPunts= new HashMap< >();
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre Lloc on està el punt d’interès ha d’existir
     * @post Afegeix un punt d’interès al mapa
     * @brief Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pI) {
        if(punts.containsKey(pI.obtNom())) punts.replace(pI.obtNom(), pI);
        else punts.put(pI.obtNom(), pI);
    }
    
    /**
     * @pre --
     * @post Afegeix un lloc al mapa  
     * @brief Afegeix un lloc al mapa 
     */
    public void afegeixLloc(Lloc ll){
        if(llocs.containsKey(ll.obtNom())) llocs.replace(ll.obtNom(), ll);
        else llocs.put(ll.obtNom(), ll);
    }
    
    /**
     * @pre --
     * @post Si no existia, afegeix el Transport Directe mT al mapa. Altrament llença una excepció 
     * @brief Si no existia, afegeix el Transport Directe mT al mapa. Altrament llença una excepció 
     */
    public void afegirTransportDirecte(MTDirecte mT) {
        if(!transDirecte.containsKey(mT.getOrigen())){
            Map<PuntInteres, Set<MTDirecte>> mapDesti= new HashMap<PuntInteres, Set<MTDirecte>>();
            Set<MTDirecte> mitjans= new TreeSet<>();
            mitjans.add(mT);
            mapDesti.put(mT.getDesti(), mitjans);
            transDirecte.put(mT.getOrigen(), mapDesti);
        }
        else if(!transDirecte.get(mT.getOrigen()).containsKey(mT.getDesti())){
            Set<MTDirecte> mitjans= new TreeSet<>();
            mitjans.add(mT);
            transDirecte.get(mT.getOrigen()).put(mT.getDesti(), mitjans);
        }
        else if(!transDirecte.get(mT.getOrigen()).get(mT.getDesti()).contains(mT)){
            transDirecte.get(mT.getOrigen()).get(mT.getDesti()).add(mT);
        }
        else{
            transDirecte.get(mT.getOrigen()).get(mT.getDesti()).remove(mT);
            transDirecte.get(mT.getOrigen()).get(mT.getDesti()).add(mT);
        }
    }
    
    /**
     * @pre primari ha d'existir a llocs i secundari a punts
     * @post Associa el lloc secundari amb nom IDpI al lloc primari IDlloc
     * @brief Associa el lloc secundari amb nom IDpI al lloc primari IDlloc
     */
    public void associarLloc(Lloc primari, PuntInteres secundari){
        secundari.vincularLloc(primari);
        primari.afegirPuntInteres(secundari);
    }
    
    /**
     * @pre lloc existeix a llocs
     * @post Si "trans" no existeix a ll, s'associa a la llista de transports urbans del lloc.
     *        Altrament llença una excepció
     * @brief Associa el MitjaTransport al Lloc
     */
    public void associarUrba(Lloc ll, MitjaTransport trans) throws Exception{
        ll.afegirTransportUrba(trans);
    }
    
    /**
     * @pre --
     * @post Retorna el Lloc associat amb l' identificador llocID.
     *        Si no existeix llença excepció
     * @brief Obtenir Lloc a partir del seu Nom
     */
    public Lloc obtenirLloc(String llocID) throws Exception{
        Lloc aux= llocs.get(llocID);
        if(aux == null) throw new Exception("LlocInexistentException");
        else return aux;
    }
    
    /**
     * @pre --
     * @post Retorna un Iterador als PuntInteres
     * @brief Obtenir Iterador a PuntInteres
     */
    public Iterator<PuntInteres> obtIteradorPunts(){
        return punts.values().iterator();
    }
    
    /**
     * @pre --
     * @post Retorna un Iterador als Llocs
     * @brief Obtenir Iterador a Lloc
     */
    public Iterator<Lloc> obtIteradorLlocs(){
        return llocs.values().iterator();
    }
    
    /**
     * @pre --
     * @post Retorna el puntInteres associat amb l' identificador puntID.
     *        Si no existeix llença excepció
     * @brief Retorna el PuntInteres de nom puntID
     */
    public PuntInteres obtenirPI(String puntID) throws Exception{
        PuntInteres aux= punts.get(puntID);
        if(aux == null) throw new Exception("PIInexistentException");
        else return aux;
    }
    
    /**
     * @pre --
     * @post Retorna el nombre de punts d’interès del mapa
     * @brief Retorna el nombre de punts d’interès del mapa
     */
    public Integer nPuntsInteres(){
        return punts.size();
    }
    
    /** 
     * @pre  Lloc origen i lloc desti han d'existir
     * @post Si no existeix l'estació de nom nomEst a origen i/o destí, la/les crea.
     *         Afegeix al lloc origen una connexió de sortida cap al lloc desti amb un temps d'origen tempsOrigen
     *         Afegeix al lloc desti una connexió d'arribada des del lloc origen amb un temps de destí tempsDesti
     * @brief Afegeix una connexió MTIndirecte
     */
    public void afegirConnexioMTI(String nomEst, Lloc origen, Lloc desti, Integer tempsOrigen, Integer tempsDesti){
        origen.afegirConnexioSortidaMTI(nomEst, desti, tempsOrigen);
        desti.afegirConnexioArribadaMTI(nomEst, origen, tempsDesti);
    }
    
    /** 
     * @pre  Origen i destí han de tenir estació de nom el mateix que el mitjà
     * @post Afegeix el mitjà al Lloc origen (Sortida) i al Lloc desti (Arribada)
     * @brief Afegeix un MTIndirecte
     */
    public void afegirMTIndirecte(MTIndirecte mitja, LocalDateTime horaSortida, Lloc origen){
        origen.afegirSortidaMTI(mitja, horaSortida);
    }
    
    /**
     * @pre --   
     * @post Retorna un Map amb els punts d’interès des d’on es pot anar a partir de pI i el seu MTDirecte (El de mínim temps)
     * @brief Retorna els mapa dels punts de interes amb el seu transport
     */
    public Map<PuntInteres,MitjaTransport> obtenirDesplsMins(PuntInteres pI,String tipus){
        Map<PuntInteres,MitjaTransport> minim= new HashMap<>();
        Map<PuntInteres, Set<MTDirecte>> veins= transDirecte.get(pI);
        for (Map.Entry<PuntInteres, Set<MTDirecte>> i: veins.entrySet()){
            TreeSet<MTDirecte> llista= (TreeSet)i.getValue();
            minim.put(i.getKey(), llista.first());
        }
        return minim;
    }
    
    /**
     * @pre --   
     * @post Retorna el temps minim per anar de un punt a un altre
     * @brief Retorna el temps minim entre dos punts
     */
    public Integer obtenirDespl(PuntInteres origen, PuntInteres desti,Map<PuntInteres,MitjaTransport> MTs,boolean afegir){
        int tempsMinim=Integer.MAX_VALUE;
        MitjaTransport MT= null;
        //COMPROVACIO MTDIR
        if(transDirecte.get(origen)!=null){
            HashMap<PuntInteres, Set<MTDirecte>> ori = new HashMap<PuntInteres, Set<MTDirecte>>(transDirecte.get(origen));
            if(ori != null){
                Set<MTDirecte> des = ori.get(desti);
                if(des != null){
                    for(MTDirecte i : des){
                        if(i.obtDurada()< tempsMinim){
                            tempsMinim=i.obtDurada();
                            MT=i;
                        }
                    }
                }
            }
        }
        //COMPROVACIO MTURBA
        if(origen.obtenirLloc().equals(desti.obtenirLloc())){
            Lloc pare = origen.obtenirLloc();
            Iterator<MitjaTransport> it = pare.obtTransportUrba();
            int temps = Integer.MAX_VALUE;
            MitjaTransport mX = null;
            while(it.hasNext()){
                mX = it.next();
                temps = mX.obtDurada();
                if(temps<tempsMinim){
                    tempsMinim = temps;
                    MT=mX;
                }
            }
        }
        if(afegir){
            //if(MT!=null)System.out.println( MT.getNom());
            MTs.put(desti, MT);
        }
        return tempsMinim;
    }
    
    /**
     * @pre --   
     * @post Retorna un Map amb els punts d’interès des d’on es pot anar a partir de pI i el seu MTDirecte (El de mínim cost)
     * @brief Retorna el cost minim entre dos punts
     */
    public Double obtenirCostDespl(PuntInteres origen, PuntInteres desti,Map<PuntInteres,MitjaTransport> MTs, boolean afegir){
        double costMinim=Double.MAX_VALUE;
        MitjaTransport MT= null;
        //COMPROVACIO MTDIR
        if(transDirecte.get(origen)!=null){
            HashMap<PuntInteres, Set<MTDirecte>> ori = new HashMap<PuntInteres, Set<MTDirecte>>(transDirecte.get(origen));
            if (ori != null) {
                Set<MTDirecte> des = ori.get(desti);
                if (des != null) {
                    for (MTDirecte i : des) {
                        if (i.obtPreu() < costMinim) {
                            costMinim = i.obtPreu();
                            MT=i;
                        }
                    }
                }
            }
        }
        //COMPROVACIO MTURBA
        if(origen!=null && origen.obtenirLloc().equals(desti.obtenirLloc())){
            Lloc pare = origen.obtenirLloc();
            Iterator<MitjaTransport> it = pare.obtTransportUrba();
            double preu = Double.MAX_VALUE;
            MitjaTransport mX = null;
            while(it.hasNext()){
                mX = it.next();
                preu = mX.obtPreu();
                if(preu<costMinim){
                    costMinim = preu;
                    MT=mX;
                }
            }
        }
        if(afegir)MTs.put(desti, MT);
        return costMinim;
    }
    
    /**
     * @pre tipus és barata, curta o sat
     * @post Retorna la llista dels MitjaTransport disponibles desde el PuntRuta "pR" di és del tipus "tipus"
     * @brief Retorna la llista dels MitjaTransport disponibles desde el PuntRuta "pR" di és del tipus "tipus"
     */
    public List<MitjaTransport> obtMitjansPunt(PuntRuta pR, String tipus){
        List<MitjaTransport> llista;
        if(pR instanceof PuntInteres){
            PuntInteres pI= (PuntInteres)pR;
            if(tipus.equals("curta")) llista= puntsPerDurada.get(pI);
            else llista= puntsPerPreu.get(pI);
            if(llista == null) llista= new ArrayList<>();
        }
        else{
            Estacio est= (Estacio)pR;
            if(tipus.equals("curta")) llista= estacionsPerDurada.get(est);
            else llista= estacionsPerPreu.get(est);
            if(llista == null) llista= new ArrayList<>();
        }
        return llista;
    }
    
    /**
     * @pre --   
     * @post Retorna la llista dels MTPunts disponibles desde l'Estacio "est" si s'ha vingut del Lloc "origen"
     * @brief Retorna la llista dels MTPunts disponibles desde l'Estacio "est" si s'ha vingut del Lloc "origen"
     */
    public List<MTPunts> obtBaixarAPunts(Estacio est, Lloc origen){
        return estacioAPunts.get(est).get(origen);
    }
    
    /**
     * @pre --   
     * @post Afegeix els MTUrbans possibles desde el PuntInteres "pI" a la llista
     * @brief Afegeix els MTUrbans possibles desde el PuntInteres "pI" a la llista
     */
    private void afegirMTUrbans(PuntInteres pI, List<MitjaTransport> llista){
        Lloc primari= pI.obtenirLloc();
        if(primari != null){
            Iterator<MitjaTransport> itUrba= primari.obtTransportUrba();
            while(itUrba.hasNext()){
                MitjaTransport urba= itUrba.next();
                Iterator<PuntInteres> itVeins= primari.obtPuntsInteres();
                while(itVeins.hasNext()){
                    PuntInteres desti= itVeins.next();
                    if(!desti.equals(pI)) llista.add(new MTDirecte(urba.obtNom(), pI, desti, urba.obtPreu(), urba.obtDurada()));
                }
            }
        }
    }
    
    /**
     * @pre --   
     * @post Afegeix els MTDirectes possibles desde el PuntInteres "pI" a la llista
     * @brief Afegeix els MTDirectes possibles desde el PuntInteres "pI" a la llista
     */
    private void afegirMTDirectes(PuntInteres pI, List<MitjaTransport> llista){
        if(transDirecte.containsKey(pI)){
            Map<PuntInteres, Set<MTDirecte>> transports= transDirecte.get(pI);
            transports.entrySet().forEach((entry) -> {
                Iterator<MTDirecte> it= entry.getValue().iterator();
                while(it.hasNext()) llista.add(it.next());
            });
        }
    }
    
    /**
     * @pre --   
     * @post Afegeix els MTIndirectes possibles desde l'Estacio "est" a la llista
     * @brief Afegeix els MTIndirectes possibles desde l'Estacio "est" a la llista
     */
    private void afegirMTIndirectes(Estacio est, List<MitjaTransport> llista){
        Iterator<MTIndirecte> itMI= est.obtMitjans();
        while(itMI.hasNext()) llista.add(itMI.next());
    }
    
    /**
     * @pre --   
     * @post Afegeix els MTEstacions possibles desde el PuntInteres "pI" a la llista
     * @brief Afegeix els MTEstacions possibles desde el PuntInteres "pI" a la llista
     */
    private void afegirMTEstacions(PuntInteres pI, List<MitjaTransport> llista){
        Lloc primari= pI.obtenirLloc();
        if(primari != null){
            Iterator<Estacio> itEst= primari.obtEstacions();
            while(itEst.hasNext()){
                Estacio est= itEst.next();
                llista.add(new MTEstacio(pI, est, 0.0, 0));
            }
        }
    }
    
    /**
     * @pre --   
     * @post Genera les ED necessàries de Punts per a l'execució del Backtraking
     * @brief Genera les ED necessàries de Punts per a l'execució del Backtraking
     */
    private void generarEDPunts(){
        Iterator<PuntInteres> itPunts= obtIteradorPunts();
        while(itPunts.hasNext()){
            PuntInteres pI= itPunts.next();
            List<MitjaTransport> llista= new ArrayList<>();
            afegirMTUrbans(pI, llista);
            afegirMTDirectes(pI, llista);
            List<MitjaTransport> llista2= new ArrayList<>(llista);
            llista.sort(MitjaTransport.COMPARA_PER_PREU);
            llista2.sort(MitjaTransport.COMPARA_PER_DURADA);
            List<MitjaTransport> llista3= new ArrayList<>();
            afegirMTEstacions(pI, llista3);
            llista.addAll(llista3);
            llista2.addAll(llista3);
            puntsPerDurada.put(pI, llista);
            puntsPerPreu.put(pI, llista2);
        }
    }
    
    /**
     * @pre --   
     * @post Genera les ED necessàries per a l'execució del Backtraking per a poder baixar a Punts desde estació
     * @brief Genera les ED necessàries per a l'execució del Backtraking
     */
    private void afegirBaixarAPunts(Estacio est){
        Iterator<Lloc> itLlocs= obtIteradorLlocs();
        Map<Lloc, List<MTPunts>> mapMTPunts= new HashMap< >();
        while(itLlocs.hasNext()){
            Lloc origen= itLlocs.next();
            Integer temps= est.obtTempsArribadaLloc(origen);
            if(temps != null){
                List<MTPunts> llistaPunts= new ArrayList<>();
                Iterator<PuntInteres> itPunts= est.obtLloc().obtPuntsInteres();
                while(itPunts.hasNext()) llistaPunts.add(new MTPunts(est, itPunts.next(), (double)0, temps));
                mapMTPunts.put(origen, llistaPunts);
            }
        }
        estacioAPunts.put(est, mapMTPunts);
    }
    
    /**
     * @pre --   
     * @post Genera les ED necessàries de Llocs per a l'execució del Backtraking
     * @brief Genera les ED necessàries de Llocs per a l'execució del Backtraking
     */
    private void generarEDLlocs(){
        Iterator<Lloc> itLlocs= obtIteradorLlocs();
        while(itLlocs.hasNext()){
            Lloc ll= itLlocs.next();
            Iterator<Estacio> itEst= ll.obtEstacions();
            while(itEst.hasNext()){
                Estacio est= itEst.next();
                List<MitjaTransport> llista= new ArrayList<>();
                afegirMTIndirectes(est, llista);
                afegirBaixarAPunts(est);
                List<MitjaTransport> llista2= new ArrayList<>(llista);
                llista.sort(MitjaTransport.COMPARA_PER_PREU);
                llista2.sort(MitjaTransport.COMPARA_PER_DURADA);
                estacionsPerDurada.put(est, llista);
                estacionsPerPreu.put(est, llista2);
            }
        }
    }
    
    /**
     * @pre --   
     * @post Genera les ED necessàries per a l'execució del Backtraking
     * @brief Genera les ED necessàries per a l'execució del Backtraking
     */
    public void generarEDBacktraking(){
        generarEDPunts();
        generarEDLlocs();
    }
    
    /**
     * @pre --   
     * @post Retorna un set amb els veins de un PuntInteres concret, considerant nomes MTDirecte
     * @brief Retorna els veins de un punt
     */
    public Set<PuntInteres> obtenirVeins(PuntInteres pi) {
        Map<PuntInteres, Set<MTDirecte>> veinsTransports = transDirecte.get(pi);
        if(veinsTransports!=null){
            Set<PuntInteres> veins = veinsTransports.keySet();
            return veins;
        }
        return null;
    }
    
    /**
     * @pre --   
     * @post Retorna el dijkstra a l'hotel mes proper
     * @brief Retorna el dijkstra a l'hotel mes proper
     */
    public Dijkstra obtenirHotelProper(PuntInteres pi,String tipusDijk){
        Dijkstra d = new Dijkstra();
        Dijkstra millor = null;
        Collection<PuntInteres> c = punts.values();
        for(PuntInteres p : c){
            if(p instanceof Allotjament){
                d.camiMinim(this, pi, p, tipusDijk);
                if(tipusDijk.matches("diners") && millor!=null){
                    if(d.retornaCost() < millor.retornaCost()){
                        millor=d;
                    }
                }
                else if(tipusDijk.matches("temps") && millor!=null){
                    if(d.retornaTemps()< millor.retornaTemps()){
                        millor=d;
                    }
                }
                else{
                    millor=d;
                }
            }
        }
        return millor;
    }

    /**
     * @pre --   
     * @post Retorna tots els veins de un punt concret, nomes considerant transport urba
     * @brief Retorna els veins amb transport urba a un punt
     */
    Set<PuntInteres> obtenirVeinsUrba(PuntInteres pi) {
        Lloc pare = pi.obtenirLloc();
        Set<PuntInteres> veins = new HashSet<>();
        Iterator<MitjaTransport> it = pare.obtTransportUrba();
        if(it.hasNext()){
            Iterator<PuntInteres> p2 = pare.obtPuntsInteres();
            while(p2.hasNext()){
                veins.add(p2.next());
            }
        }
        return veins;
    }
}