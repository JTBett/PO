package hva.animals;

import java.io.Serial;
import java.io.Serializable;

import hva.habitats.Habitat;
import hva.satisfactionStrategies.AnimalSatisfactionStrategy;
import hva.satisfactionStrategies.SatisfactionStrategy;


public class Animal implements Serializable{
    
    //@Serial
    //private static final long serialVersionUID = ;

    private String _keyIdAnimal;
    private String _name;
    private String _speciesId;

    private Habitat _habitat;
    
    private SatisfactionStrategy _satisfactionStrategy;

    
    public Animal(String keyId, String name, String speciesId, Habitat habitat) {
        this._keyIdAnimal = keyId;
        this._name = name;
        this._speciesId = speciesId;
        this._habitat = habitat;
        this._satisfactionStrategy = new AnimalSatisfactionStrategy(this);
    }
    
    
    public String getKeyId() { return this._keyIdAnimal; }

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
