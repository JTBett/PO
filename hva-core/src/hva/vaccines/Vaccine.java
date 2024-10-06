package hva.vaccines;

import java.util.Map;
import java.util.TreeMap;

import hva.animals.Animal;
import hva.species.Species;

public class Vaccine {
    
    private String _keyIdVaccine;

    private String _name;

    /**
    * Stores this vaccine's species that it can successfully vaccinate, sorted by their key.
    */
    private Map<String, Species> _canVaccinateSpecies /*= new TreeMap<>()*/;


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
