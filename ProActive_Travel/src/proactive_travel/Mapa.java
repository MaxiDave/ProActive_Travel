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
    private Map<PuntInteres, Map<PuntInteres, List<MTDirecte>>> transDirecte;
    
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea un mapa buit
     */
    public Mapa(){
        llocs= new HashMap<String, Lloc>();
        punts= new HashMap<String, PuntInteres>();
        transDirecte= new HashMap<PuntInteres, Map<PuntInteres, List<MTDirecte>>>();
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: Lloc on està el punt d’interès ha d’existir
     * @post: Afegeix un punt d’interès al mapa
     */
    public void afegeixPuntInteres(PuntInteres pI) throws Exception{
        if(!punts.containsKey(pI.obtenirNom())) punts.put(pI.obtenirNom(), pI);
        else throw new Exception("PuntInteresRepetitException");
    }
    
    /**
     * @pre: --
     * @post: Afegeix un lloc al mapa  
     */
    public void afegeixLloc(Lloc ll) throws Exception{
        if(!llocs.containsKey(ll.obtenirNom())) llocs.put(ll.obtenirNom(), ll);
        else throw new Exception("LlocRepetitException");
    }
    
    /**
     * @pre: --
     * @post: Si no existia, afegeix el Transport Directe mT al mapa. Altrament llença una excepció 
     */
    public void afegirTransportDirecte(MTDirecte mT) throws Exception{
        if(!transDirecte.containsKey(mT.getOrigen())){
            Map<PuntInteres, List<MTDirecte>> mapDesti= new HashMap<PuntInteres, List<MTDirecte>>();
            List<MTDirecte> mitjans= new ArrayList();
            mitjans.add(mT);
            mapDesti.put(mT.getDesti(), mitjans);
            transDirecte.put(mT.getOrigen(), mapDesti);
        }
        else if(!transDirecte.get(mT.getOrigen()).get(mT.getDesti()).contains(mT)) transDirecte.get(mT.getOrigen()).get(mT.getDesti()).add(mT);
        else throw new Exception("TransportDirecteRepetitException");
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
        Map<PuntInteres,MitjaTransport> minim= new HashMap<PuntInteres,MitjaTransport>();
        Map<PuntInteres, List<MTDirecte>> veins= transDirecte.get(pI.obtenirNom());
        for (Map.Entry<PuntInteres, List<MTDirecte>> i: veins.entrySet()){
            List<MTDirecte> llista= i.getValue();
            minim.put(i.getKey(), llista.get(0));
        }
        return minim;
    }
    public Integer obtenirDespl(PuntInteres origen, PuntInteres desti){
        int tempsMinim=Integer.MAX_VALUE;
        HashMap<PuntInteres, List<MTDirecte>> ori = new HashMap<PuntInteres, List<MTDirecte>>(transDirecte.get(origen));
        if(ori != null){
            List<MTDirecte> des = ori.get(desti);
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
        HashMap<PuntInteres, List<MTDirecte>> ori = new HashMap<PuntInteres, List<MTDirecte>>(transDirecte.get(origen));
        if(ori != null){
            List<MTDirecte> des = ori.get(desti);
            if(des != null){
                for(MTDirecte i : des){
                    if(i.getPreu()< costMinim){
                        costMinim=i.getPreu();
                    }
                }
            }
        }
        return costMinim;
    }
    
    public Set<ItemRuta> obtenirItemVeins(PuntInteres pI, LocalDateTime temps){
        throw new UnsupportedOperationException("Not supported yet"); 
    }
    public Set<PuntInteres> obtenirVeins(PuntInteres pi){
        throw new UnsupportedOperationException("Not supported yet"); 
        /*
        HashMap<PuntInteres, List<MTDirecte>> veinsTransports = new HashMap<String, List<MTDirecte>>(transDirecte.get(pi.obtenirNom()));
        Set<PuntInteres> veins = veinsTransports.keySet();
        return veins;
        */
    }
}