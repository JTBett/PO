package hva.animals;

import hva.habitats.Habitat;
import hva.satisfactionStrategies.AnimalSatisfactionStrategy;
import hva.satisfactionStrategies.SatisfactionStrategy;


public class Animal {
    
    private String _keyIdAnimal;

    private String _name;

    private String _speciesId;

    private Habitat _habitat;
    
    private SatisfactionStrategy _satisfactionStrategy;

    
    public Animal() {
        //TODO: constructor arguments
        //TODO: constructor itself
        this._satisfactionStrategy = new AnimalSatisfactionStrategy(this);
    }
    

    public String getSpeciesId() { return this._speciesId; }

    public Habitat getHabitat() { return this._habitat; }


    /**
     * @see AnimalSatisfactionStrategy#calcSatisfaction()
    */
    public int calcSatisfactionAnimal() {
        return _satisfactionStrategy.calcSatisfaction();
    }   


    /**
     * -description
     *
     * @param
     */    
    public void changeHabitat(Habitat habitat) {

    }

}
