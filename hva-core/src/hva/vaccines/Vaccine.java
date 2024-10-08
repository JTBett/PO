package hva.vaccines;

import java.io.Serializable;
import java.io.Serial;

import java.util.Map;
import java.util.TreeMap;

import hva.animals.Animal;
import hva.species.Species;

public class Vaccine implements Serializable{
    
    //@Serial
    //private static final long serialVersionUID = ;
    
    private String _keyIdVaccine;

    private String _name;

    /**
    * Stores this vaccine's species that it can successfully vaccinate, sorted by their key.
    */
    private Map<String, Species> _canVaccinateSpecies;


    public Vaccine(String keyId, String name) {
        this._keyIdVaccine = keyId;
        this._name = name;

        this._canVaccinateSpecies = new TreeMap<String, Species>();
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
}
