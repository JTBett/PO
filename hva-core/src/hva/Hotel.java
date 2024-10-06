package hva;

import hva.exceptions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
    private Map<String, TreeonHabitat> _trees /*= new TreeMap<>()*/;
    
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
        catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
            throw new ImportFileException(filename, e);
        }
    }
    


    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException if input format not recognized.
     */    
    private void fromFileParser (String[] args) 
            throws UnrecognizedEntryException {
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
     * -description
     *
     * @param
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileSpecies (String[] args) {
        if (args.length != 3) {
            throw new InvalidInputException(args);
        }
        try {
            this.registerSpecies(args[1], args[2]);
        }
        catch (DuplicateSpeciesKeyException | DuplicateSpeciesNameException e) {
            throw new InvalidInputException(args);
        }
    }
  

    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException Ex: wrong number of fields
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileAnimal (String[] args) {
        registerAnimal(null, null, null, null);
    }
  

    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException Ex: wrong number of fields
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileHabitat (String[] args) {
        registerHabitat(null, null, 0);
    }
  

    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException Ex: wrong number of fields
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileTree (String[] args) {
        registerTree(null, null, null, 0, 0, null);
    }
  

    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException Ex: wrong number of fields
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileEmployee (String[] args) {
        registerEmployee(null, null, null);
    }
  

    /**
     * -description
     *
     * @param
     * @throws UnrecognizedEntryException Ex: wrong number of fields
     * @throws InvalidInputException Ex: wrong instanceof for arg
     */    
    private void fromFileVaccine (String[] args) {
        registerVaccine(null, null, null);
    }

    /*--------------HANDLING INITIALIZATION VIA TEXT FILE INPUT---------END--*/


    /*--START-----------------REGISTRATION FUNCTIONS-------------------------*/

    /**
    * -description
    *
    * @param
    */
    public void registerSpecies(String keyId, String name) {
        //TODO: verify fields
        //TODO: return new Vet();
    }


    /**
    * -description
    *
    * @param
    * @throws DuplicateAnimalKeyException
    * @throws UnknownHabitatKeyException
    */
    public void registerAnimal(String keyId, String name, String speciesId,
                                 String habitatId) {
        //TODO: verify fields
        //TODO: create new species if speciesId not found
        //TODO: return new Animal();
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
