package hva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hva.exceptions.DuplicateSpeciesKeyException;
import hva.exceptions.DuplicateSpeciesNameException;
import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.InvalidEntryException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnrecognizedEntryException;


import hva.habitats.Habitat;
import hva.species.Species;
import hva.animals.Animal;
import hva.employees.*;
import hva.trees.*;
import hva.vaccines.*;
import hva.seasons.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


//FIXME import other Java classes
//FIXME import project classes

public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;


    /**
    * Stores the hotels's species, sorted by their key.
    */
    private Map<String, Species> _species /*= new TreeMap<>()*/;
    
    /**
    * Stores the hotels's animals, sorted by their key.
    */
    private Map<String, Animal> _animals /*= new TreeMap<>()*/;
    
    /**
    * Stores the hotels's habitats, sorted by their key.
    */
    private Map<String, Habitat> _habitats /*= new TreeMap<>()*/;
    
    /**
    * Stores the hotels's trees, sorted by their key.
    */
    private Map<String, Trees> _trees /*= new TreeMap<>()*/;
    
    /**
    * Stores the hotels's employees, sorted by their key.
    */
    private Map<String, Employee> _employees /*= new TreeMap<>()*/;
    
    /**
    * Stores the hotels's vaccines, sorted by their key.
    */
    private Map<String, Vaccine> _vaccines /*= new TreeMap<>()*/;

    /**
    * Stores the hotels's applied vaccines, sorted by their cronological order.
    */
    private List<VaccinationInstance> _vaccinationHistory = new ArrayList<>();



    /**
    * Current season:
    *       0 - Spring
    *       1 - Summer
    *       2 - Autumn
    *       3 - Winter
    */
    private Season currentSeason;


    public Hotel() {
        //TODO: constructor
        this.currentSeason = new Spring();  // Start in Spring
    }

    /*--START-------HANDLING INITIALIZATION VIA TEXT FILE INPUT--------------*/

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    void importFile(String filename) throws ImportFileException {
        try (BufferedReader _buffer = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = _buffer.readLine()) != null) {
                fromFileParser(line.split("\\|"));
            }
        } 
        catch (IOException | UnrecognizedEntryException | InvalidEntryException /* FIXME maybe other exceptions */ e) {
            throw new ImportFileException(filename, e);
        }
    }
    


    /**
     * Parse and import an entry (line) via text file.
     *
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws UnrecognizedEntryException if entry format is not recognized.
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileParser (String[] args) 
            throws UnrecognizedEntryException, InvalidEntryException {
        switch (args[0]) {
            case "ESPÉCIE" -> this.fromFileSpecies(args);
            case "ANIMAL" -> this.fromFileAnimal(args);
            case "HABITAT" -> this.fromFileHabitat(args);
            case "ÁRVORE" -> this.fromFileTree(args);
            case "TRATADOR", "VETERINÁRIO" -> this.fromFileEmployee(args);
            case "VACINA" -> this.fromFileVaccine(args);
            default -> throw new UnrecognizedEntryException(String.join("|", args));
        }
    }


    /**
     * Parse and import a Species entry via text file.
     * {@code ESPÉCIE|id|nome}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileSpecies (String[] args) throws InvalidEntryException {
        if (args.length != 3) {
            throw new InvalidEntryException(args);
        }

        try {
            this.registerSpeciesfromFile(args[1], args[2]);
        }
        catch (DuplicateSpeciesKeyException | DuplicateSpeciesNameException e) {
            throw new InvalidEntryException(args);
        }
    }
  

    /**
     * Parse and import a Animal entry via text file.
     * {@code ANIMAL|id|nome|idEspécie|idHabitat}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileAnimal (String[] args) throws InvalidEntryException {
        if (args.length != 5) {
            throw new InvalidEntryException(args);
        }

        try {
            this.registerAnimalfromFile(args[1], args[2], args[3], args[4]);        
        }
        catch (DuplicateAnimalKeyException | UnknownSpeciesKeyException |
             UnknownHabitatKeyException e) {
            throw new InvalidEntryException(args);
        }
    }
  

    /**
     * Parse and import a Habitat entry via text file.
     * {@code HABITAT|id|nome|área|idÁrvore1,...,idÁrvoreN}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */ 
    private void fromFileHabitat (String[] args) {
        registerHabitat(null, null, 0);
    }
  

    /**
     * Parse and import a Tree entry via text file.
     * {@code ÁRVORE|id|nome|idade|dificuldade|tipo}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileTree (String[] args) {
        registerTree(null, null, null, 0, 0, null);
    }
  

    /**
     * Parse and import a Employee entry via text file.
     * {@code TRATADOR|id|nome|idHabitat1,...,idHabitatN}
     * {@code VETERINÁRIO|id|nome|idEspécie1,...,idEspécieN}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */      
    private void fromFileEmployee (String[] args) {
        registerEmployee(null, null, null);
    }
  

    /**
     * Parse and import a Vaccine entry via text file.
     * {@code VACINA|id|nome|idEspécie1,…,idEspécieN}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileVaccine (String[] args) {
        registerVaccine(null, null, null);
    }

    /*--------------HANDLING INITIALIZATION VIA TEXT FILE INPUT---------END--*/


    /*--START-----------------REGISTRATION FUNCTIONS-------------------------*/

    /**
    * Register a new Species to this Hotel.
    *
    * @param keyId The Id of the species.
    * @param name The name of the species.
    * @return The created {@link Species} instance.
    *
    * @throws DuplicateSpeciesKeyException
    * @throws DuplicateSpeciesNameException 
    */
    public Species registerSpeciesfromFile(String keyId, String name)
                throws DuplicateSpeciesKeyException, DuplicateSpeciesNameException {
        
        if (this.getSpeciesbyId(keyId) != null) {
            throw new DuplicateSpeciesKeyException(keyId);
        }
        /*-START-can only occur via file initialization */
        for (Species s : this.getAllSpecies()) {
            if (s.getName().equals(name)) {
                throw new DuplicateSpeciesNameException(name);
            }
        }
        /*can only occur via file initialization-END-*/

        Species s0 = new Species(keyId, name);
        if (s0 != null) {
            this._species.put(keyId, s0);
        }
        return s0;
    }


    /**
    * Register a new Animal to this Hotel.
    *
    * @param keyId The Id of the animal.
    * @param name The name of the animal.
    * @return The created {@link Animal} instance.
    *
    * @throws DuplicateAnimalKeyException
    * @throws UnknownSpeciesKeyException 
    * @throws UnknownHabitatKeyException
    */
    public Animal registerAnimalfromFile(String keyId, String name,
                                    String speciesId, String habitatId) 
                throws DuplicateAnimalKeyException, UnknownSpeciesKeyException, 
                    UnknownHabitatKeyException {

        if (this.getAnimalbyId(keyId) != null) {
            throw new DuplicateAnimalKeyException(keyId);
        }
        if (this.getSpeciesbyId(speciesId) == null) {
            throw new UnknownSpeciesKeyException(speciesId);
        }
        if (this.getHabitatbyId(habitatId) == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        
        Animal a0 = new Animal(keyId, name, speciesId, getHabitatbyId(habitatId));
        if (a0 != null) {
            getSpeciesbyId(speciesId).addAnimaltoSpecies(a0);
            this._animals.put(keyId, a0);
        }
        return a0;
    }    


    /**
    * -description
    *
    * @param
    * @throws DuplicateEmployeeKeyException
    */
    public void registerEmployee(String keyId, String name, String specialty) {
        //TODO: verify fields
        if(specialty == "VET")
            registerVet(keyId, name);
        if(specialty == "TRT")
            registerZookeeper(keyId, name);
    }   
    
    /**
    * -description
    *
    * @param
    */
    public void registerVet(String keyId, String name) {
        //fields have already been verified in registerEmployee
        //TODO: return new Vet();
    }

    /**
    * -description
    *
    * @param
    */
    public void registerZookeeper(String keyId, String name) {
        //fields have already been verified in registerEmployee
        //TODO: return new Zookeeper();
    }


    /**
    * -description
    *
    * @param
    * @throws DuplicateHabitatKeyException
    */
    public void registerHabitat(String keyId, String name, int area) {
        //TODO: verify fields
        //TODO: return new Habitat();
    }


    /**
    * -description
    *
    * @param
    * @throws DuplicateTreeKeyException
    * @throws UnknownHabitatKeyException
    */
    public void registerTree(String habitatId, String keyId, String name,
                                 int age, int baseDiff, String treeType) {
        //TODO: verify fields
        if(treeType == "CADUCA")
            registerPerene(habitatId, keyId, name, age, baseDiff);
        if(treeType == "PERENE")
            registerCaduca(habitatId, keyId, name, age, baseDiff);
    }   
    
    /**
    * -description
    *
    * @param
    */
    public void registerPerene(String habitatId, String keyId, String name,
                                 int age, int baseDiff) {
        //fields have already been verified in registerTree
        //TODO: return new Perene();
    }

    /**
    * -description
    *
    * @param
    */
    public void registerCaduca(String habitatId, String keyId, String name,
                                 int age, int baseDiff) {
        //fields have already been verified in registerTree
        //TODO: return new Caduca();
    }


    /**
    * -description
    *
    * @param
    * @throws DuplicateVAccineKeyException
    * @throws UnknownSpeciesKeyException
    */
    public void registerVaccine(String keyId, String name, String[] speciesId) {
        //TODO: verify fields
        //TODO: return new Vaccine();
    }
    /*------------------------REGISTRATION FUNCTIONS--------------------END--*/

        
    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/

    public Species getSpeciesbyId(String speciesId) {
        return this._species.get(speciesId);
    }

    public Animal getAnimalbyId(String animalId) {
        return this._animals.get(animalId);
    }

    public Habitat getHabitatbyId(String habitatId) {
        return this._habitats.get(habitatId);
    }


    /**
    * -description
    *
    * @param
    * @throws UnknownHabitatKeyException
    */
    public void /*Collection<Animal>*/ lookupAnimalsbyHabitat(String habitatId) {

    }


    /**
    * -description
    *
    * @param
    * @throws UnknownAnimalKeyException
    */
    public void /*Collection<Vaccine>*/ lookupVaccinesbyAnimal(String animalId) {

    }


    /**
    * -description
    *
    * @param
    * @throws UnknownEmployeeKeyException
    */
    public void /*Collection<Animal>*/ lookupVeterinaryVaccination(String vetId) {

    }


    /**
    * -description
    *
    * @throws 
    */
    public void /*Collection<Animal>*/ lookupMisuseVaccines() {

    }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


    /*--START--------------SPECIES MANAGEMENT FUNCTIONS----------------------*/

    /**
    * -description
    *
    * @throws
    */
    public Collection<Species> getAllSpecies() {
        return this._species.values();
    }
    /*---------------------SPECIES MANAGEMENT FUNCTIONS-----------------END--*/


    /*--START--------------ANIMAL MANAGEMENT FUNCTIONS-----------------------*/

    /**
    * -description
    *
    * @throws
    */
    public void /*Collection<Animal>*/ getAllAnimals() {

    }


    /**
    * -description
    *
    * @param
    * @throws UnknownAnimalKeyException
    * @throws UnknownHabitatKeyException
    */
    public void transferAnimaltoHabitat(String animalId, String habitatId) {

    }
 
    
    /**
    * -description
    *
    * @param
    * @throws UnknownAnimalKeyException
    */
    public int satisfactionbyAnimal(String animalId) {
        return 0;
    }
    /*---------------------ANIMAL MANAGEMENT FUNCTIONS------------------END--*/


    /*--START-------------EMPLOYEE MANAGEMENT FUNCTIONS----------------------*/

    /**
    * -description
    *
    * @param
    * @throws
    */
    public void /*Collection<Employee>*/ getAllEmployees() {

    }
    

    /**
    * -description
    *
    * @param
    * @throws UnknownEmployeeKeyException
    * @throws NoResponsabilityException Ex: responsabilityId not species or habitat
    */
    public void addResponsability(String employeeId, String responsabilityId) {
        //TODO: if vet, should add species to vet and vet to species
    }

    
    /**
    * -description
    *
    * @param
    * @throws UnknownEmployeeKeyException 
    * @throws NoResponsabilityException Ex: responsabilityId not species or habitat
    */
    public void removeResponsability(String employeeId, String responsabilityId) {
        //TODO: if vet, should remove species from vet and vet from species
    }


    /**
    * -description
    *
    * @param
    * @throws UnknownEmployeeKeyException
    */
    public int satisfactionbyEmployee(String employeeId) {
        return 0;
    }
    /*--------------------EMPLOYEE MANAGEMENT FUNCTIONS-----------------END--*/


    /*--START--------------HABITAT MANAGEMENT FUNCTIONS----------------------*/

    /**
    * -description
    *
    * @throws
    */
    public void /*Collection<Habitat>*/ getAllHabitats() {

    }
    

    /**
    * -description
    *
    * @param
    * @throws UnknownHabitatKeyException
    */
    public void changeHabitatArea(String habitatId, int newArea) {

    }

    
    /**
    * -description
    *
    * @param
    * @throws UnknownHabitatKeyException
    * @throws UnknownSpeciesKeyException
    */
    public void changeHabitatInfluence(String habitatId, String speciesId, 
                                       String influence) {
        
    }


    /**
    * @param
    * @throws DuplicateTreeKeyException
    * @throws UnknownHabitatKeyException
    */
    public void addTreetoHabitat(String habitatId, String treeId, String name,
                                 int age, int baseDiff, String treeType) {

    }


    /**
    * -description
    *
    * @param
    * @throws UnknownHabitatKeyException
    */
    public void /*Collection<Tree>*/ getTreesbyHabitat(String habitatId) {

    }
    /*---------------------HABITAT MANAGEMENT FUNCTIONS-----------------END--*/


    /*--START--------------VACCINE MANAGEMENT FUNCTIONS----------------------*/
    
    /**
    * -description
    *
    * @throws
    */
    public void /*Collection<Vaccine>*/ getAllVaccines() {

    }


    /**
    * -description
    *
    * @param
    * @throws UnknownVaccineKeyException
    * @throws UnknownEmployeeKeyException
    * @throws VeterinarianNotAuthorizedException
    * @throws UnknownAnimalKeyException
    */
    public void vaccinateAnimal(String vaccineId, String vetId, String animalId) {
        
    }

    
    /**
    * -description
    *
    */
    public void /*List<VaccinationInstance>*/ getApliedVaccines() {

    }
    /*---------------------VACCINE MANAGEMENT FUNCTIONS-----------------END--*/


    /**
    * -description
    *
    * @return
    */
    public int advanceSeason() {
        currentSeason = currentSeason.nextSeason();
        
        return 0;
    }

}
