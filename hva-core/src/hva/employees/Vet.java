package hva.employees;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import hva.satisfactionStrategies.SatisfactionStrategy;
import hva.satisfactionStrategies.VetSatisfactionStrategy;
import hva.species.Species;

public class Vet extends Employee {
    
    /**
    * Stores the veterinary's species that it can vaccinate, sorted by their key.
    */
    private Map<String, Species> _speciesUnderResponsability /*= new TreeMap<>()*/;

    
    public Vet(String keyId, String name) {
        super(keyId, name);
        this.setSatisfactionStrategy(new VetSatisfactionStrategy(this));
    }


    public Collection<Species> getSpeciesunderResponsibility() {
        return this._speciesUnderResponsability.values();
    }


    /**
    * -description
    *
    * @param
    */
    @Override
    public void addResponsability(Object responsability) {
        if (responsability instanceof Species) { 
            Species species = (Species) responsability;
            _speciesUnderResponsability.put(species.getKeyId(), species);
        }
        //TODO: else
    }


    /**
    * -description
    *
    * @param
    */
    @Override
    public void removeResponsability(String responsabilityId) {
        this._speciesUnderResponsability.remove(responsabilityId);
    }

    
    @Override
    public int calcSatisfaction() {
        return this.getSatisfactionStrategy().calcSatisfaction();
    }
    
}
