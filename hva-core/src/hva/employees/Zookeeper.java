package hva.employees;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import hva.habitats.Habitat;
import hva.satisfactionStrategies.ZookeperSatisfactionStrategy;

public class Zookeeper extends Employee {
    
    /**
    * Stores the zookeeper's habitats that it can mantain.
    */
    private Map<String, Habitat> _habitatsUnderResponsability;
        
    
    public Zookeeper(String keyId, String name) {
        super(keyId, name);
        this._habitatsUnderResponsability = new TreeMap<String, Habitat>();
        this.setSatisfactionStrategy(new ZookeperSatisfactionStrategy(this));
    }

    
    public Collection<Habitat> getHabitatsunderResponsibility() {
        return this._habitatsUnderResponsability.values();
    }


    /**
    * Assign a habitat to employee.
    *
    * @param responsability the habitat to assign.
    */
    @Override
    public void addResponsability(Object responsability) {
        Habitat habitat = (Habitat) responsability; 
        this._habitatsUnderResponsability.put(habitat.getKeyId(), habitat);
    }


    /**
    * Remove a habitat from zookeepers's responsabilities.
    *
    * @param responsabilityId the Id of the habitat to unassign.
    */
    @Override
    public void removeResponsability(String responsabilityId) {
        this._habitatsUnderResponsability.remove(responsabilityId);
    }

    
    /**
    * @see ZookeeperSatisfactionStrategy#calcSatisfaction()
    */
    @Override
    public int calcSatisfaction() {
        return this.getSatisfactionStrategy().calcSatisfaction();
    }
}
