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
public class Ruta {
    //ATRIBUTS-----------------------------------------------------------------------------------------------------------------------------------
    Deque<ItemRuta> items;
    private Integer durada;
    private Integer satisfaccio;
    private Double cost;
    private final LocalDateTime inici;
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
        this.tipus= tipus;
    }
    
    //MÈTODES PÚBLICS----------------------------------------------------------------------------------------------------------------------------
    /**
     * @pre: --
     * @post: Afegeix un ItemRuta a la Ruta
     */
    public void afegeixItemRuta(ItemRuta item){
        System.out.println("Afegir");
        satisfaccio+= item.obtSatisfaccio();
        System.out.print("Cost anterior: ");
        System.out.printf("%.2f", cost);
        System.out.println();
        cost+= item.obtCost();
        System.out.println("Cost item: "+item.obtCost()+"€");
        System.out.print("Cost nou: ");
        System.out.printf("%.2f", cost);
        System.out.println();
        durada+= item.obtDurada();
        System.out.println("Durada Anterior: "+durada);
        if(items.isEmpty()){
            Integer tempsExtra= (int)Duration.between(inici, item.obtInici()).toMinutes();
            System.out.println("Temps Lliure: "+tempsExtra);
            durada+= tempsExtra;
            System.out.println("Durada Nova: "+durada);
        }
        else{
            Integer tempsExtra= (int)Duration.between(items.getLast().obtFinal(), item.obtInici()).toMinutes();
            System.out.println("Temps Lliure: "+tempsExtra);
            durada+= tempsExtra;
            System.out.println("Durada Nova: "+durada);
        }
        items.addLast(item);
    }
    
    //pre cua no buida
    public Integer treureUltimItem(){
        System.out.println("Treure");
        ItemRuta item= items.pollLast();
        durada-= item.obtDurada();
        satisfaccio-= item.obtSatisfaccio();
        System.out.print("Cost anterior: ");
        System.out.printf("%.2f", cost);
        System.out.println();
        System.out.println("Cost item: "+item.obtCost()+"€");
        cost-= item.obtCost();
        System.out.print("Cost anterior: ");
        System.out.printf("%.2f", cost);
        System.out.println();
        Integer duracioTempsLliure;
        System.out.println("Durada Anterior: "+durada);
        if(items.isEmpty()) duracioTempsLliure= (int)Duration.between(inici, item.obtInici()).toMinutes();
        else duracioTempsLliure= (int)Duration.between(items.getLast().obtFinal(), item.obtInici()).toMinutes();
        durada-= duracioTempsLliure;
        System.out.println("Temps Lliure: "+duracioTempsLliure);
        System.out.println("Durada Nova: "+durada);
        return duracioTempsLliure;
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
    
    public Boolean arribaDesti(PuntInteres desti){
        return !items.isEmpty() && (items.getLast() instanceof Visita || items.getLast() instanceof EstadaHotel) && items.getLast().obtPuntSortida().equals(desti);
    }
    
    /*
    @Override
    public String toString(){
        String out="Preu: "+cost+"€\n";
        out+="Satisfacció: "+satisfaccio+"\n";
        LocalDateTime aux= inici;
        out+=inici.toLocalDate()+"\n";
        Iterator<ItemRuta> itItem= items.iterator();
        while(itItem.hasNext()){
            ItemRuta item= itItem.next();
            if(item.obtInici().toLocalDate().isAfter(aux.toLocalDate())){
                out+=aux.toLocalTime()+"-"+item.obtInici().toLocalTime()+" Temps Lliure\n";
            }
            out+=item;
        }
        return out;
    }
    */
}