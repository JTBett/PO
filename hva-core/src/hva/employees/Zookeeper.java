package hva.employees;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import hva.habitats.Habitat;
import hva.satisfactionStrategies.SatisfactionStrategy;
import hva.satisfactionStrategies.ZookeperSatisfactionStrategy;

public class Zookeeper extends Employee {
    
    /**
    * Stores the zookeeper's habitats that it can mantain, sorted by their key.
    */
    private Map<String, Habitat> _habitatsUnderResponsability /*= new TreeMap<>()*/;
        
    
    public Zookeeper() {
        this.setSatisfactionStrategy(new ZookeperSatisfactionStrategy(this));
    }

    
    public Collection<Habitat> getHabitatsunderResponsibility() {
        return this._habitatsUnderResponsability.values();
    }


    /**
    * -description
    *
    * @param
    */
    @Override
    public void addResponsability(Object responsability) {
        if (responsability instanceof Habitat) {
            Habitat habitat = (Habitat) responsability; 
            this._habitatsUnderResponsability.put(habitat.getKeyId(), habitat);
        }
        // TODO:else
    }


    /**
    * -description
    *
    * @param
    */
    @Override
    public void removeResponsability(String responsabilityId) {
        this._habitatsUnderResponsability.remove(responsabilityId);
    }
}
