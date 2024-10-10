package hva.vaccines;

import java.io.Serializable;
//import java.io.Serial;

import java.util.Map;
import java.util.TreeMap;
import java.util.StringJoiner;

import hva.animals.Animal;
import hva.species.Species;

public class Vaccine implements Serializable{
    
    //@Serial
    //private static final long serialVersionUID = ;
    
    private String _keyIdVaccine;

    private String _name;

    /**
    * Stores this vaccine's species that it can successfully vaccinate.
    */
    private Map<String, Species> _canVaccinateSpecies;


    public Vaccine(String keyId, String name) {
        this._keyIdVaccine = keyId;
        this._name = name;

        this._canVaccinateSpecies = new TreeMap<String, Species>();
    }


    public String getKeyId() {return this._keyIdVaccine; }

    public String getName() { return this._name; } 


    public void savetoVaccinate(Species species) {
        this._canVaccinateSpecies.put(species.getKeyId(), species);
    }


    /**
    * -description
    *
    * @param
    */
    public int calcVaccineDamage(Animal animal) {
        return 0;
    }

    
    /**
    * -description
    *
    * @param
    */
    public String translateVaccineDamage(int vaccineDamage) {
        //TODO: create switch case
        return "";
    }

    @Override
    public String toString(){
        return new StringJoiner("|")
            .add("VACINA")
            .add(Integer.toString(_keyIdVaccine))
            .add(_name)
            .toString();
    }

}
