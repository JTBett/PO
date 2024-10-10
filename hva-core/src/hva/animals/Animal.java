package hva.animals;

//import java.io.Serial;
import java.io.Serializable;

import hva.util.Visitable;
import hva.util.Visitor;
import hva.habitats.Habitat;
import hva.satisfactionStrategies.AnimalSatisfactionStrategy;
import hva.satisfactionStrategies.SatisfactionStrategy;

import java.util.StringJoiner;


public class Animal implements Serializable,Visitable{
    
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
    
    
    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/
    
    public String getKeyId() { return this._keyIdAnimal; }
    
    public String getName() { return this._name; }
    
    public String getSpeciesId() { return this._speciesId; }
    
    public Habitat getHabitat() { return this._habitat; }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


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

    @Override
    public <T> T accept(Visitor<T> visitor) {
      return visitor.visit(this);
    }    
}
