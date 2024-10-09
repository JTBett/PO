package hva.employees;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import hva.satisfactionStrategies.VetSatisfactionStrategy;
import hva.species.Species;

public class Vet extends Employee {
    
    /**
    * Stores the veterinary's species that it can vaccinate.
    */
    private Map<String, Species> _speciesUnderResponsability;

    
    public Vet(String keyId, String name) {
        super(keyId, name);
        this._speciesUnderResponsability = new TreeMap<String, Species>();
        this.setSatisfactionStrategy(new VetSatisfactionStrategy(this));
    }


    public Collection<Species> getSpeciesunderResponsibility() {
        return this._speciesUnderResponsability.values();
    }


    /**
    * Assign a species to employee.
    *
    * @param responsability the species to assign.
    */
    @Override
    public void addResponsability(Object responsability) {
        Species species = (Species) responsability;
        _speciesUnderResponsability.put(species.getKeyId(), species);
    }


    /**
    * Remove a species from vet's responsabilities.
    *
    * @param responsabilityId the Id of the species to unassign.
    */
    @Override
    public void removeResponsability(String responsabilityId) {
        this._speciesUnderResponsability.remove(responsabilityId);
    }

    /**
    * @see VetSatisfactionStrategy#calcSatisfaction()
    */
    @Override
    public int calcSatisfaction() {
        return this.getSatisfactionStrategy().calcSatisfaction();
    }
    
}
