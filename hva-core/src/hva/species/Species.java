package hva.species;

import java.util.Map;
import java.util.TreeMap;

import hva.animals.Animal;
import hva.employees.Vet;

public class Species {

    private String _keyIdSpecies;

    private String _name;

    /**
    * Stores the vets that can vaccinate animals from this species, sorted by their key.
    */
    private Map<String, Vet> _authorizedVets /*= new TreeMap<>()*/;
    
    /**
    * Stores the species's animals, sorted by their key.
    */
    private Map<String, Animal> _animalsofSpecies /*= new TreeMap<>()*/;
    

    public Species() { 

    }

    
    public String getKeyId() { return this._keyIdSpecies; }


    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/

    /**
     * 
     * @return the total of animals form this species.
     */   
    public int numberofTotalAnimals() { return this._animalsofSpecies.size(); }
    
    
    /**
     *
     * @return the total of vets responsible for this species.
     */   
    public int numberofTotalVets() { return this._authorizedVets.size(); }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


    /**
     * -description
     *
     * @param
     */    
    public void addVettoAuthorized(Vet vet) {

    }

    /**
     * -description
     *
     * @param
     */    
    public void removeVetfromAuthorized(String vetId) {

    }


    /**
     * -description
     *
     * @param
     */    
    public void addAnimaltoSpecies(Animal animal) {

    }

}
