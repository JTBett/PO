package hva.habitats;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import hva.animals.Animal;
import hva.employees.Zookeeper;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.seasons.Season;
import hva.trees.Trees;

public class Habitat {

    private String _keyIdHabitat;

    private String _name;

    private int _area;

    private Season _currentSeason;


    /**
    * Stores the habitat's animals, sorted by their key.
    */
    private Map<String, Animal> _animalsonHabitat;
        
    /**
    * Stores the habitat's trees, sorted by their key.
    */
    private Map<String, Trees> _trees;

    /**
    * Stores the zookepers that can maintain this habitat, sorted by their key.
    */
    private Map<String, Zookeeper> _canMantain;

    /**
    * Stores the habitat's adequacys, sorted by their species' key.
    * "positiva (resposta: POS), negativa (resposta: NEG), ou neutra (resposta: NEU)".
    * "adequação é 20 se a influência do habitat for positiva, -20 se for negativa
    * e 0 se a influência for neutra (condição por omissão)".
    */
    private Map<String, String> _speciesAdequacy;
    

    public Habitat(String keyId, String name, int area, Season season) {
        this._keyIdHabitat = keyId;
        this._name = name;
        this._area = area;
        this._currentSeason = season;

        this._animalsonHabitat = new TreeMap<String, Animal>();
        this._trees = new TreeMap<String, Trees>();
        this._canMantain = new TreeMap<String, Zookeeper>();
    }
    

    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/

    public int getArea() { return this._area; }

    public String getKeyId() { return this._keyIdHabitat; }

    public Season getCurrentSeason() { return this._currentSeason; }

    public Collection<Trees> getTreesonHabitat() { return this._trees.values(); }
    
    /**
     * 
     * @return the total of animals in this habitat.
     */     
    public int numberofTotalAnimals() {
        return _animalsonHabitat.size();
    }

    /**
     * 
     * @return the total of animal not of the species(speciesId) in this habitat.
     * @throws UnknownSpeciesKeyException
     */     
    public int numberofDifferentAnimalsbySpecies(String speciesId) {
        return this.numberofTotalAnimals() - this.numberofAnimalsbySpecies(speciesId);
    }
    
    /**
     * 
     * @return the total of animal of the same species(speciesId) in this habitat.
     * @throws UnknownSpeciesKeyException
     */    
    public int numberofAnimalsbySpecies(String speciesId) {
        return 0;
    }


    /**
     * 
     * @return the total of trees in this habitat.
     */    
    public int numberofTotalTrees() {
        return 0;
    }


    /**
     *
     * @return the total of zookeepers responsible for this habitat.
     */   
    public int numberofTotalZookeepers() { return this._canMantain.size(); }


    /**
     * -description
     *
     * @param
     * @throws UnknownSpeciesKeyException
     */    
    public int adequacybySpecies(String speciesId) {
        String adequacy = this._speciesAdequacy.get(speciesId);
        return 0;
    }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


    /**
     * -description
     *
     * @param
     */    
    public void changeAdequacy(String speciesId) {

    }


    /**
     * -description
     *
     * @param
     */    
    public void changeArea(int area) {
        this._area = area;
    }


    /**
     * -description
     *
     * @param
     */    
    public void addTreetoHabitat(Trees tree) {
    
    }


    /**
     * -description
     *
     * @param
     */    
    public void addAnimaltoHabitat(Animal animal) {

    }

    /**
     * -description
     *
     * @param
     */    
    public void removeAnimalfromHabitat(String animalId) {

    }


    /**
     * -description
     *
     * @param
     */    
    public void addZookeepertoHabitat(Zookeeper zookeeper) {

    }

    /**
     * -description
     *
     * @param
     */    
    public void removeZookeeperfromHabitat(String zookeeperId) {

    }


    /**
     * -description
     * trabalho_no_habitat(h) = área(h) + 3 * população(h) + Σ esforço_limpeza(a), a ∈ árvores(h)
     *
     * @param
     */    
    public double calcWorkinHabitat() {

        double treeWork = 0.0;
        for (Trees tree : this.getTreesonHabitat()) {
            treeWork += tree.calcWorkForSeason(this.getCurrentSeason());
        }
        double work = this.getArea() + 3 * this.numberofTotalZookeepers() + treeWork;

        return work;
    }
}
