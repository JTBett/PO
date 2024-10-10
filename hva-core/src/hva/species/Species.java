package hva.species;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.StringJoiner;
//import java.io.Serial;
import java.io.Serializable;

import hva.animals.Animal;
import hva.employees.Vet;

public class Species implements Serializable{

    //@Serial
    //private static final long serialVersionUID = ;

    private String _keyIdSpecies;
    private String _name;

    /**
    * Stores the vets that can vaccinate animals from this species.
    */
    private Map<String, Vet> _authorizedVets;
    
    /**
    * Stores the species's animals.
    */
    private Map<String, Animal> _animalsofSpecies;
    

    public Species(String keyId, String name) { 
        this._keyIdSpecies = keyId;
        this._name = name;

        this._authorizedVets = new TreeMap<String, Vet>();
        this._animalsofSpecies = new TreeMap<String, Animal>();
    }
    
    
    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/
    
    public String getKeyId() { return this._keyIdSpecies; }

    public String getName() { return this._name; }

    public Collection<Vet> getAuthorizedVets () {
        return this._authorizedVets.values(); 
    }
      
    public Collection<Animal> getAnimalsofSpecies () {
        return this._animalsofSpecies.values(); 
    }

    public int numberofTotalAnimals() { return this._animalsofSpecies.size(); }
    
    public int numberofTotalVets() { return this._authorizedVets.size(); }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


    /**
     * -description
     *
     * @param
     */    
    public void addVettoAuthorized(Vet vet) {
        this._authorizedVets.put(vet.getKeyId(),vet);
    }

    /**
     * -description
     *
     * @param
     */    
    public void removeVetfromAuthorized(String vetId) {
        this._authorizedVets.remove(vetId);
    }


    /**
     * -description
     *
     * @param
     */    
    public void addAnimaltoSpecies(Animal animal) {
        this._animalsofSpecies.put(animal.getKeyId(), animal);
    }

    @Override
    public String visit(Species s){
        return new StringJoiner("|")
            .add("ESPÉCIES")
            .add(Integer.toString(_keyIdSpecies))
            .add(_name)
            .toString();
    }

}
