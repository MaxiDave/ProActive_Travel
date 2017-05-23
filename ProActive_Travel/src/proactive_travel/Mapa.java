//ProActive_Travel

/**
 * @file: Mapa.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Mapa: Conté informació de tot el relacionat amb dades geogràfiques i transports
 * @copyright: Public License
 */

package proactive_travel;
import java.util.*;
import java.time.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa un Mapa, amb les estructures de dades corresponents 
 */
public class Mapa {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private Map<String, Lloc> llocs;
    private Map<String, PuntInteres> punts;
    private Map<PuntInteres, Map<PuntInteres, Set<MTDirecte>>> transDirecte;
    private Map<PuntInteres, List<MitjaTransport>> puntsPerDurada;
    private Map<PuntInteres, List<MitjaTransport>> puntsPerPreu;
    private Map<Estacio, List<MitjaTransport>> estacionsPerDurada;
    private Map<Estacio, List<MitjaTransport>> estacionsPerPreu;
    private Map<Estacio, Map<Lloc, List<MTPunts>>> estacioAPunts;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea un mapa buit
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
     * @pre: Lloc on està el punt d’interès ha d’existir
     * @post: Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pI) {
        if(punts.containsKey(pI.obtNom())) punts.replace(pI.obtNom(), pI);
        else punts.put(pI.obtNom(), pI);
    }
    
    /**
     * @pre: --
     * @post: Afegeix un lloc al mapa  
     */
    public void afegeixLloc(Lloc ll){
        if(llocs.containsKey(ll.obtNom())) llocs.replace(ll.obtNom(), ll);
        else llocs.put(ll.obtNom(), ll);
    }
    
    /**
     * @pre: --
     * @post: Si no existia, afegeix el Transport Directe mT al mapa. Altrament llença una excepció 
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
     * @pre: primari ha d'existir a llocs i secundari a punts
     * @post: Associa el lloc secundari amb nom IDpI al lloc primari IDlloc
     */
    public void associarLloc(Lloc primari, PuntInteres secundari){
        secundari.vincularLloc(primari);
        primari.afegirPuntInteres(secundari);
    }
    
    /**
     * @pre: lloc existeix a llocs
     * @post: Si "trans" no existeix a ll, s'associa a la llista de transports urbans del lloc.
     *        Altrament llença una excepció
     */
    public void associarUrba(Lloc ll, MitjaTransport trans) throws Exception{
        ll.afegirTransportUrba(trans);
    }
    
    /**
     * @pre: --
     * @post: Retorna el Lloc associat amb l' identificador llocID.
     *        Si no existeix llença excepció
     */
    public Lloc obtenirLloc(String llocID) throws Exception{
        Lloc aux= llocs.get(llocID);
        if(aux == null) throw new Exception("LlocInexistentException");
        else return aux;
    }
    
    public Iterator<PuntInteres> obtIteradorPunts(){
        return punts.values().iterator();
    }
    
    public Iterator<Lloc> obtIteradorLlocs(){
        return llocs.values().iterator();
    }
    
    /**
     * @pre: --
     * @post: Retorna el puntInteres associat amb l' identificador puntID.
     *        Si no existeix llença excepció
     */
    public PuntInteres obtenirPI(String puntID) throws Exception{
        PuntInteres aux= punts.get(puntID);
        if(aux == null) throw new Exception("PIInexistentException");
        else return aux;
    }
    
    /**
     * @pre: --
     * @post: Retorna el nombre de punts d’interès del mapa
     */
    public Integer nPuntsInteres(){
        return punts.size();
    }
    
    /** @pre:  Lloc origen i lloc desti han d'existir
     *  @post: Si no existeix l'estació de nom nomEst a origen i/o destí, la/les crea.
     *         Afegeix al lloc origen una connexió de sortida cap al lloc desti amb un temps d'origen tempsOrigen
     *         Afegeix al lloc desti una connexió d'arribada des del lloc origen amb un temps de destí tempsDesti
     */
    public void afegirConnexioMTI(String nomEst, Lloc origen, Lloc desti, Integer tempsOrigen, Integer tempsDesti){
        origen.afegirConnexioSortidaMTI(nomEst, desti, tempsOrigen);
        desti.afegirConnexioArribadaMTI(nomEst, origen, tempsDesti);
    }
    
    /** @pre:  Origen i destí han de tenir estació de nom el mateix que el mitjà
     *  @post: Afegeix el mitjà al Lloc origen (Sortida) i al Lloc desti (Arribada)
     */
    public void afegirMTIndirecte(MTIndirecte mitja, LocalDateTime horaSortida, Lloc origen){
        origen.afegirSortidaMTI(mitja, horaSortida);
    }
    
    /**
     * @pre: --   
     * @post: Retorna un Map amb els punts d’interès des d’on es pot anar a partir de pI i el seu MTDirecte (El de mínim temps, mínima distància o mínim cost depenent de “tipus”)
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
                        if(i.getDurada()< tempsMinim){
                            tempsMinim=i.getDurada();
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
                temps = mX.getDurada();
                if(temps<tempsMinim){
                    tempsMinim = temps;
                    MT=mX;
                }
            }
        }
        if(afegir)MTs.put(desti, MT);
        return tempsMinim;
    }
    
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
                        if (i.getPreu() < costMinim) {
                            costMinim = i.getPreu();
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
                preu = mX.getPreu();
                if(preu<costMinim){
                    costMinim = preu;
                    MT=mX;
                }
            }
        }
        if(afegir)MTs.put(desti, MT);
        return costMinim;
    }
    
    
    //pre: tipus és barata, curta o sat
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
    
    public List<MTPunts> obtBaixarAPunts(Estacio est, Lloc origen){
        return estacioAPunts.get(est).get(origen);
    }
    
    private void afegirMTUrbans(PuntInteres pI, List<MitjaTransport> llista){
        Lloc primari= pI.obtenirLloc();
        Iterator<MitjaTransport> itUrba= primari.obtTransportUrba();
        while(itUrba.hasNext()){
            MitjaTransport urba= itUrba.next();
            Iterator<PuntInteres> itVeins= primari.obtPuntsInteres();
            while(itVeins.hasNext()){
                PuntInteres desti= itVeins.next();
                if(!desti.equals(pI)) llista.add(new MTDirecte(urba.getNom(), pI, desti, urba.getPreu(), urba.getDurada()));
            }
        }
    }
    
    private void afegirMTDirectes(PuntInteres pI, List<MitjaTransport> llista){
        if(transDirecte.containsKey(pI)){
            Map<PuntInteres, Set<MTDirecte>> transports= transDirecte.get(pI);
            transports.entrySet().forEach((entry) -> {
                Iterator<MTDirecte> it= entry.getValue().iterator();
                while(it.hasNext()) llista.add(it.next());
            });
        }
    }
    
    private void afegirMTIndirectes(Estacio est, List<MitjaTransport> llista){
        Iterator<MTIndirecte> itMI= est.obtMitjans();
        while(itMI.hasNext()) llista.add(itMI.next());
    }
    
    private void afegirMTEstacions(PuntInteres pI, List<MitjaTransport> llista){
        Iterator<Estacio> itEst= pI.obtenirLloc().obtEstacions();
        while(itEst.hasNext()){
            Estacio est= itEst.next();
            llista.add(new MTEstacio(pI, est, 0.0, 0));
        }
    }
    
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
    
    public void generarEDBacktraking(){
        generarEDPunts();
        generarEDLlocs();
    }
    
    public Set<PuntInteres> obtenirVeins(PuntInteres pi) {
        Map<PuntInteres, Set<MTDirecte>> veinsTransports = transDirecte.get(pi);
        if(veinsTransports!=null){
            Set<PuntInteres> veins = veinsTransports.keySet();
            return veins;
        }
        return null;
    }
    
    public ArrayDeque<PuntInteres> obtenirHotelProper(PuntInteres pi,String tipusDijk){
        Dijkstra d = new Dijkstra();
        Dijkstra millor = null;
        Collection<PuntInteres> c = punts.values();
        for(PuntInteres p : c){
            System.out.println(p.obtNom());
            if(p instanceof Allotjament){
                System.out.println("Allotjament localitzat");
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
        ArrayDeque<PuntInteres> cami = millor.retornaPuntsInteres();
        return cami;
    }

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