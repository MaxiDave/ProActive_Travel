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
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea un mapa buit
     */
    public Mapa(){
        llocs= new HashMap<String, Lloc>();
        punts= new HashMap<String, PuntInteres>();
        transDirecte= new HashMap<PuntInteres, Map<PuntInteres, Set<MTDirecte>>>();
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: Lloc on està el punt d’interès ha d’existir
     * @post: Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pI) {
        if(punts.containsKey(pI.obtenirNom())) punts.replace(pI.obtenirNom(), pI);
        else punts.put(pI.obtenirNom(), pI);
    }
    
    /**
     * @pre: --
     * @post: Afegeix un lloc al mapa  
     */
    public void afegeixLloc(Lloc ll){
        if(llocs.containsKey(ll.obtenirNom())) llocs.replace(ll.obtenirNom(), ll);
        else llocs.put(ll.obtenirNom(), ll);
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
    public Integer obtenirDespl(PuntInteres origen, PuntInteres desti){
        int tempsMinim=Integer.MAX_VALUE;
        HashMap<PuntInteres, Set<MTDirecte>> ori = new HashMap<PuntInteres, Set<MTDirecte>>(transDirecte.get(origen));
        if(ori != null){
            Set<MTDirecte> des = ori.get(desti);
            if(des != null){
                for(MTDirecte i : des){
                    if(i.getDurada()< tempsMinim){
                        tempsMinim=i.getDurada();
                    }
                }
            }
        }
        return tempsMinim;
    }
    
    public Double obtenirCostDespl(PuntInteres origen, PuntInteres desti){
        double costMinim=Double.MAX_VALUE;
        //COMPROVACIO MTDIR
        if(transDirecte.get(origen)!=null){
            HashMap<PuntInteres, Set<MTDirecte>> ori = new HashMap<PuntInteres, Set<MTDirecte>>(transDirecte.get(origen));
            if (ori != null) {
                Set<MTDirecte> des = ori.get(desti);
                if (des != null) {
                    for (MTDirecte i : des) {
                        if (i.getPreu() < costMinim) {
                            costMinim = i.getPreu();
                        }
                    }
                }
            }
        }
        //COMPROVACIO MTURBA
        if(origen.obtenirLloc().equals(desti.obtenirLloc())){
            Lloc pare = origen.obtenirLloc();
            Iterator<MitjaTransport> it = pare.obtTransportUrba();
            double preu = Double.MAX_VALUE;
            while(it.hasNext()){
                preu = it.next().getPreu();
                if(preu<costMinim){
                    costMinim = preu;
                }
            }
        }
        return costMinim;
    }
    
    private void afegirTransportsUrbans(List<ItemRuta> items, List<ItemRuta> itemsFinals, PuntInteres ant, PuntInteres act, LocalDateTime temps){
        Iterator<MitjaTransport> it= act.obtenirLloc().obtTransportUrba();
        while(it.hasNext()){
            MitjaTransport mT= it.next();
            if(!temps.plusMinutes(mT.getDurada()).toLocalDate().isAfter(temps.toLocalDate())){
                Iterator<PuntInteres> itPunts= act.obtenirLloc().obtPuntsInteres();
                while(itPunts.hasNext()){
                    PuntInteres desti= itPunts.next();
                    if(!act.equals(desti)){
                        if(ant == null || !desti.equals(ant)) items.add(new TrajecteDirecte(new MTDirecte(mT.getNom(), act, desti, mT.getPreu(), mT.getDurada()), temps));
                        //else itemsFinals.add(new TrajecteDirecte(new MTDirecte(mT.getNom(), act, desti, mT.getPreu(), mT.getDurada()), temps));
                    }
                }
            }
        }
    }
    
    private void afegirTransportsDirectes(List<ItemRuta> items, List<ItemRuta> itemsFinals, PuntInteres ant, PuntInteres act, LocalDateTime temps){
        Map<PuntInteres, Set<MTDirecte>> t= transDirecte.get(act);
        if(t != null){
            for(Map.Entry<PuntInteres, Set<MTDirecte>> entry : t.entrySet()) {
                Iterator<MTDirecte> it= entry.getValue().iterator();
                while(it.hasNext()){
                    MTDirecte mitja= it.next();
                    if(!temps.plusMinutes(mitja.getDurada()).toLocalDate().isAfter(temps.toLocalDate())){
                        if(ant == null || !mitja.getDesti().equals(ant)) items.add(new TrajecteDirecte(mitja, temps));
                        //else itemsFinals.add(new TrajecteDirecte(mitja, temps));
                    }
                }
            }
        }
    } 
    
    private void afegirTransportsIndirectes(List<ItemRuta> items, List<ItemRuta> itemsFinals, PuntInteres ant, PuntInteres act, LocalDateTime temps){
        Iterator<Estacio> it= act.obtenirLloc().obtEstacions();
        while(it.hasNext()){
            LocalDate data= temps.toLocalDate();
            Estacio est= it.next();
            Map<LocalTime, MTIndirecte> sortidesDia= est.obtSortidesDelDia(data);
            if(sortidesDia != null){
                for(Map.Entry<LocalTime, MTIndirecte> entry : sortidesDia.entrySet()) {
                    LocalDateTime sortida= temps.minusMinutes(est.obtTempsSortidaLloc(act.obtenirLloc()));
                    Lloc desti= entry.getValue().getDesti();
                    Iterator<PuntInteres> itPunts= desti.obtPuntsInteres();
                    while(itPunts.hasNext()){
                        PuntInteres dest= itPunts.next();
                        if(ant == null || !dest.equals(ant)) items.add(new TrajecteIndirecte(entry.getValue(), sortida, act, dest));
                        //else itemsFinals.add(new TrajecteIndirecte(entry.getValue(), sortida, act, dest));
                    }
                }
            }
        }
    } 
    
    public List<ItemRuta> obtenirItemsVeins(PuntInteres ant, PuntInteres act, LocalDateTime temps, Viatge viatge){
        Map<String, Integer> MapSat= viatge.obtMapSatisfaccio();
        List<ItemRuta> items= new ArrayList<>();
        List<ItemRuta> itemsFinals= new ArrayList<>();
        Integer sat= act.grauSatisfaccio(MapSat);
        LocalTime horaDia= temps.toLocalTime();
        if(act instanceof PuntVisitable){
            PuntVisitable pV= (PuntVisitable)act;
            if(!temps.plusMinutes(pV.obtTempsVisita()).toLocalDate().isAfter(temps.toLocalDate())){
                if(pV.obtObertura().isAfter(horaDia) && pV.obtObertura().plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())){
                    items.add(new Visita(pV, LocalDateTime.of(temps.toLocalDate(), pV.obtObertura()), sat));
                }
                else if(horaDia.plusMinutes(pV.obtTempsVisita()).isBefore(pV.obtTancament())){
                    items.add(new Visita(pV, temps, sat));
                }
            }
        }
        else{
            Allotjament a= (Allotjament)act;
            items.add(new EstadaHotel(a, temps, sat));
        }
        afegirTransportsUrbans(items, itemsFinals, ant, act, temps);
        afegirTransportsDirectes(items, itemsFinals, ant, act, temps);
        afegirTransportsIndirectes(items, itemsFinals, ant, act, temps);
        //items.addAll(itemsFinals);
        return items;
    }
    
    public Set<PuntInteres> obtenirVeins(PuntInteres pi) {
        Map<PuntInteres, Set<MTDirecte>> veinsTransports = transDirecte.get(pi);
        if(veinsTransports!=null){
            Set<PuntInteres> veins = veinsTransports.keySet();
            return veins;
        }
        return null;
    }
    
    public Set<PuntInteres> obtenirHotelProper(PuntInteres pi,String tipusDijk){
        Dijkstra d = new Dijkstra();
        Dijkstra millor = new Dijkstra();
        Collection<PuntInteres> c = punts.values();
        for(PuntInteres p : c){
            if(p instanceof Allotjament){
                d.camiMinim(this, pi, p, tipusDijk);
                if(tipusDijk.matches("diners") && (d.retornaCost() < millor.retornaCost())){
                    millor=d;
                }
                else if(tipusDijk.matches("temps") && (d.retornaTemps()< millor.retornaTemps())){
                    millor=d;
                }
                else{
                    //Satisfaccio
                }
            }
        }
        Set<PuntInteres> cami = millor.retornaPuntsInteres();
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