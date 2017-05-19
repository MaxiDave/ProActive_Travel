//ProActive_Travel

/**
 * @file: Ruta.java
 * @author: Roger Barnés, u1939667
 * @author: David Martínez, u1939690
 * @version: 1
 * @date: Curs 2016-2017
 * @warning: --
 * @brief: Classe Ruta: Conté informació d'una Ruta amb els seus ItemRuta
 * @copyright: Public License
 */

package proactive_travel;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

/**
 * DESCRIPCIÓ GENERAL
 * @brief: Representa una Ruta
 */
public class Ruta{
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    private Deque<ItemRuta> items;
    private Integer durada;
    private Integer satisfaccio;
    private Double cost;
    private final LocalDateTime inici;
    private LocalDateTime fi;
    private final String tipus;
    //CONSTRUCTOR--------------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Crea una Ruta inicial buida
     */
    public Ruta(String tipus, LocalDateTime inici){
        items= new ArrayDeque<>();
        durada= 0;
        satisfaccio= 0;
        cost= (double)0;
        this.inici= inici;
        this.fi= inici;
        this.tipus= tipus;
    }
    
    /**
     * @pre: --
     * @post: Crea una Ruta que és una còpia de r
     */
    public Ruta(Ruta r){
        items= new ArrayDeque<>(r.items);
        durada= new Integer(r.durada);
        satisfaccio= new Integer(r.satisfaccio);
        cost= new Double(r.cost);
        inici= r.inici.plusMinutes(0);
        tipus= new String(r.tipus);
        fi= LocalDateTime.of(r.fi.toLocalDate(), r.fi.toLocalTime());
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Afegeix un ItemRuta a la Ruta
     */
    public void afegeixItemRuta(ItemRuta item){
        //System.out.println("Afegir");
        satisfaccio+= item.obtSatisfaccio();
        //System.out.print("Cost anterior: ");
        //System.out.printf("%.2f", cost);
        //System.out.println();
        cost+= item.obtCost();
        //System.out.println("Cost item: "+item.obtCost()+"€");
        //System.out.print("Cost nou: ");
        //System.out.printf("%.2f", cost);
        //System.out.println();
        Integer duradaItem= 0;
        duradaItem+= item.obtDurada();
        //System.out.println("Durada Anterior: "+durada);
        if(items.isEmpty()){
            Integer tempsExtra= (int)Duration.between(inici, item.obtInici()).toMinutes();
            //System.out.println("Temps Lliure: "+tempsExtra);
            duradaItem+= tempsExtra;
            //System.out.println("Durada Nova: "+durada);
        }
        else{
            Integer tempsExtra= (int)Duration.between(items.getLast().obtFinal(), item.obtInici()).toMinutes();
            //System.out.println("Temps Lliure: "+tempsExtra);
            duradaItem+= tempsExtra;
            //System.out.println("Durada Nova: "+durada);
        }
        fi= fi.plusMinutes(duradaItem);
        durada+= duradaItem;
        items.addLast(item);
    }
    
    //pre cua no buida
    public ItemRuta treureUltimItem(){
        //System.out.println("Treure");
        ItemRuta item= items.pollLast();
        Integer duradaItem= 0;
        duradaItem+= item.obtDurada();
        satisfaccio-= item.obtSatisfaccio();
        //System.out.print("Cost anterior: ");
        //System.out.printf("%.2f", cost);
        //System.out.println();
        //System.out.println("Cost item: "+item.obtCost()+"€");
        cost-= item.obtCost();
        //System.out.print("Cost anterior: ");
        //System.out.printf("%.2f", cost);
        //System.out.println();
        Integer duracioTempsLliure;
        //System.out.println("Durada Anterior: "+durada);
        if(items.isEmpty()) duracioTempsLliure= (int)Duration.between(inici, item.obtInici()).toMinutes();
        else duracioTempsLliure= (int)Duration.between(items.getLast().obtFinal(), item.obtInici()).toMinutes();
        duradaItem-= duracioTempsLliure;
        fi= fi.minusMinutes(duradaItem);
        durada-= duradaItem;
        return item;
        //System.out.println("Temps Lliure: "+duracioTempsLliure);
        //System.out.println("Durada Nova: "+durada);
    }
    
    public PuntInteres obtDesti(){
        return items.getLast().obtPuntSortida();
    }
    
    public Integer obtDurada(){
        return durada; 
    }
    
    public Integer obtSatisfaccio(){
        return satisfaccio; 
    }
    
    public Double obtCost(){
        return cost;
    }
    
    public String obtTipus(){
        return tipus;
    }
    
    public boolean teItems(){
        return !items.isEmpty();
    }
    
    public Boolean arribaDesti(PuntInteres desti){
        return !items.isEmpty() && (items.getLast() instanceof Visita || items.getLast() instanceof EstadaHotel) && items.getLast().obtPuntSortida().equals(desti);
    }
    
    public LocalDateTime obtFinal(){
        return fi;
    }
    
    @Override
    public String toString(){
        String costArrodonit= String.format("%.2f", cost);
        String out="Preu: "+costArrodonit+"€\n";
        out+="Satisfacció: "+satisfaccio+"\n";
        out+=inici.toLocalDate()+"\n";
        Iterator<ItemRuta> itItem= items.iterator();
        LocalDateTime aux= inici;
        while(itItem.hasNext()){
            ItemRuta item= itItem.next();
            if(item.obtInici().toLocalTime().isAfter(aux.toLocalTime())) out+=aux.toLocalTime()+"-"+item.obtInici().toLocalTime()+" Temps Lliure\n";
            if(item.obtInici().toLocalDate().isAfter(aux.toLocalDate())) out+=item.obtInici().toLocalDate();
            out+=item;
            aux= item.obtFinal();
        }
        return out;
    }
}