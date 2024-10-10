package hva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.exceptions.DuplicateSpeciesKeyException;
import hva.exceptions.DuplicateSpeciesNameException;
import hva.exceptions.DuplicateTreeKeyException;
import hva.exceptions.DuplicateVaccineKeyException;
import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.InvalidEntryException;
import hva.exceptions.NoResponsibilityException;
import hva.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownEmployeeSpecialtyException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnknownTreeKeyException;
import hva.exceptions.UnknownTreeTypeException;
import hva.exceptions.UnknownVaccineKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VeterinarianNotAuthorizedException;

import hva.species.Species;
import hva.animals.Animal;
import hva.habitats.Habitat;
import hva.satisfactionStrategies.VetSatisfactionStrategy;
import hva.trees.Trees;
import hva.trees.Perene;
import hva.trees.Caduca;

import hva.vaccines.Vaccine;
import hva.vaccines.VaccinationInstance;

import hva.employees.Employee;
import hva.employees.Zookeeper;
import hva.employees.Vet;

import hva.seasons.Season;
import hva.seasons.Spring;
import hva.seasons.Summer;
import hva.seasons.Autumn;
import hva.seasons.Winter;


public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;


    /**
    * Stores the hotels's species.
    */
    private Map<String, Species> _species = new TreeMap<>();
    
    /**
    * Stores the hotels's animals.
    */
    private Map<String, Animal> _animals = new TreeMap<>();
    
    /**
    * Stores the hotels's habitats.
    */
    private Map<String, Habitat> _habitats = new TreeMap<>();
    
    /**
    * Stores the hotels's trees.
    */
    private Map<String, Trees> _trees = new TreeMap<>();
    
    /**
    * Stores the hotels's employees.
    */
    private Map<String, Employee> _employees = new TreeMap<>();
    
    /**
    * Stores the hotels's vaccines.
    */
    private Map<String, Vaccine> _vaccines = new TreeMap<>();

    /**
    * Stores the hotels's applied vaccines, sorted by their cronological order.
    */
    private List<VaccinationInstance> _vaccinationHistory = new ArrayList<>();

    /**
    * Hotel's current season, spring by omission.
    */
    private Season _currentSeason = new Spring();

    /**
    * 'dirty bit/flag' true if hotel has been modified since the last save.
    */
    private boolean _dirty = false;


    /**
    * Get whether the hotel has been modified. Hotel is not dirty
    * when it is saved to disk.
    *
    * @return the logic value of the dirty flag
    */
    public boolean getDirty() { return this._dirty; }

    /**
    * Turn the dirty flag off to indicate the hotel state has been saved.
    */
    public void notDirty() { this._dirty = false; }

    /**
    * Hotel modification has occurred since last save.
    */
    private void isDirty() { this._dirty = true; }
    

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
        catch (IOException | UnrecognizedEntryException | InvalidEntryException e) {
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
            this.registerSpecies(args[1], args[2]);
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
            this.registerAnimal(args[1], args[2], args[3], args[4]);        
        }
        catch (DuplicateAnimalKeyException | UnknownSpeciesKeyException |
            UnknownHabitatKeyException e) {
            throw new InvalidEntryException(args);
        }
    }
  

    /**
     * Parse and import a Habitat entry via text file.
     * {@code HABITAT|id|nome|área|[idÁrvore1,...,idÁrvoreN]}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */ 
    private void fromFileHabitat (String[] args) throws InvalidEntryException {

        if (args.length != 4 && args.length != 5) {
            throw new InvalidEntryException(args);
        }

        try {
            int area = Integer.parseInt(args[3]);

            registerHabitat(args[1], args[2], area);

            if (args.length == 5) {
                String[] treeArray = args[4].split(",");
                for (String treeId : treeArray) {
                    registerTreetoHabitat(treeId.trim(), args[1]);
                }
            }
        }
        catch (NumberFormatException | DuplicateHabitatKeyException |
                UnknownTreeKeyException | UnknownHabitatKeyException e) {
            throw new InvalidEntryException(args);
        }
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
    private void fromFileTree (String[] args) throws InvalidEntryException {
        if (args.length != 6) {
            throw new InvalidEntryException(args);
        }

        try {
            int age = Integer.parseInt(args[3]);
            int baseDiff = Integer.parseInt(args[4]);

            this.registerTree(args[1], args[2], age, baseDiff, args[5]);        
        }
        catch (NumberFormatException | DuplicateTreeKeyException |
            UnknownTreeTypeException e) {
            throw new InvalidEntryException(args);
        }
    }
  

    /**
     * Parse and import a Employee entry via text file.
     * {@code TRATADOR|id|nome|[idHabitat1,...,idHabitatN]}
     * {@code VETERINÁRIO|id|nome|[idEspécie1,...,idEspécieN]}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */      
    private void fromFileEmployee (String[] args) throws InvalidEntryException {
        if (args.length != 3 && args.length != 4) {
            throw new InvalidEntryException(args);
        }

        try {
            registerEmployee(args[1], args[2], args[0]);

            if (args.length == 4) {
                String[] responsabilityArray = args[3].split(",");
                for (String responsabilityId : responsabilityArray) {
                    addResponsability(responsabilityId.trim(), args[1]);
                }
            }
        }
        catch (DuplicateEmployeeKeyException | UnknownEmployeeSpecialtyException
            |   UnknownEmployeeKeyException |NoResponsibilityException e) {
            throw new InvalidEntryException(args);
        }
    }
  

    /**
     * Parse and import a Vaccine entry via text file.
     * {@code VACINA|id|nome|[idEspécie1,…,idEspécieN]}
     * 
     * @param args The parsed arguments of the entry to import, split by "|".
     * 
     * @throws InvalidEntryException if the entry doesn't correspond
     *                               to the class' required fields
     */    
    private void fromFileVaccine (String[] args) throws InvalidEntryException {

        if (args.length != 3 && args.length != 4) {
            throw new InvalidEntryException(args);
        }

        try {
            registerVaccine(args[1], args[2]);
            if (args.length == 4) {
                String[] speciesArray = args[3].split(",");
                for (String species : speciesArray) {
                    addSafeSpecies(species.trim(), args[1]);
                }
            }
        }
        catch (DuplicateVaccineKeyException | UnknownSpeciesKeyException |
                UnknownVaccineKeyException e) {
            throw new InvalidEntryException(args);
        }
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
    public Species registerSpecies(String keyId, String name)
                throws DuplicateSpeciesKeyException, DuplicateSpeciesNameException {
        
        if (this.lookupSpeciesbyId(keyId) != null) {
            throw new DuplicateSpeciesKeyException(keyId);
        }
        if (this.lookupspeciesbyName(name) != null) {
            throw new DuplicateSpeciesNameException(name);
        }

        Species s0 = new Species(keyId, name);
        if (s0 != null) {
            this._species.put(keyId, s0);
        }
        this.isDirty();
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
    public Animal registerAnimal(String keyId, String name,
                                    String speciesId, String habitatId) 
                throws DuplicateAnimalKeyException, UnknownSpeciesKeyException, 
                    UnknownHabitatKeyException {

        if (this.lookupAnimalbyId(keyId) != null) {
            throw new DuplicateAnimalKeyException(keyId);
        }
        if (this.lookupSpeciesbyId(speciesId) == null) {
            throw new UnknownSpeciesKeyException(speciesId);
        }
        if (this.lookupHabitatbyId(habitatId) == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        
        Animal a0 = new Animal(keyId, name, speciesId, lookupHabitatbyId(habitatId));
        if (a0 != null) {
            lookupSpeciesbyId(speciesId).addAnimaltoSpecies(a0);
            lookupHabitatbyId(habitatId).addAnimaltoHabitat(a0);
            this._animals.put(keyId, a0);
        }
        this.isDirty();
        return a0;
    }


    /**
    * Register a new Employee to this Hotel.
    *
    * @param keyId The Id of the employee.
    * @param name The name of the employee.
    * @param specialty The employee's specialty.
    * @return The created {@link Employee} instance.
    *    
    * @throws DuplicateEmployeeKeyException
    * @throws UnknownEmployeeSpecialtyException 
    */
    public Employee registerEmployee(String keyId, String name,
                                        String specialty) 
            throws DuplicateEmployeeKeyException, UnknownEmployeeSpecialtyException {
        
        if (this.lookupEmployeebyId(keyId) != null) {
            throw new DuplicateEmployeeKeyException(keyId);
        }

        Employee e0 = null;
        switch (specialty) {
            case "TRATADOR" -> e0 = this.registerZookeeper(keyId, name);
            case "VETERINÁRIO" -> e0 = this.registerVet(keyId, name);
            default -> throw new UnknownEmployeeSpecialtyException(specialty);
        }
        this.isDirty();
        return e0;
    }    
    
    /**
    * Register a new Vet to this Hotel.
    *
    * @param keyId The Id of the vet.
    * @param name The name of the vet.
    * @return The created {@link Vet} instance.
    */
    public Vet registerVet(String keyId, String name) {
        Vet v0 = new Vet(keyId, name);
        if (v0 != null) {
            this._employees.put(keyId, v0);
        }
        return v0;
    }

    /**
    * Register a new Zookeeper to this Hotel.
    *
    * @param keyId The Id of the zookeeper.
    * @param name The name of the zookeeper.
    * @return The created {@link Zookeeper} instance.
    */
    public Zookeeper registerZookeeper(String keyId, String name) {
        Zookeeper z0 = new Zookeeper(keyId, name);
        if (z0 != null) {
            this._employees.put(keyId, z0);
        }
        return z0;
    }


    /**
    * Register a new Habitat to this Hotel.
    *
    * @param keyId The Id of the habitat.
    * @param name The name of the habitat.
    * @param area The area of the habitat.
    * @return The created {@link Habitat} instance.
    *
    * @throws DuplicateHabitatKeyException
    */
    public Habitat registerHabitat(String keyId, String name, int area) 
                throws DuplicateHabitatKeyException {

        if (this.lookupHabitatbyId(keyId) != null) {
            throw new DuplicateHabitatKeyException(keyId);
        }

        Habitat h0 = new Habitat(keyId, name, area, this.getCurrentSeason());
        if (h0 != null) {
            this._habitats.put(keyId, h0);
        }
        this.isDirty();
        return h0;        
    }
    

    /**
    * Register a new Tree to this Hotel.
    *
    * @param keyId The Id of the tree.
    * @param name The name of the tree.
    * @param age The age of the tree.
    * @param baseDiff The base difficulty of the tree.
    * @param treeType The type of the tree.
    * @return The created {@link Trees} instance.
    *    
    * @throws DuplicateTreeKeyException
    * @throws UnknownTreeTypeException 
    */
    public Trees registerTree(String keyId, String name,
                                 int age, int baseDiff, String treeType)
                throws DuplicateTreeKeyException, UnknownTreeTypeException {

        if (this.lookupTreebyId(keyId) != null) {
            throw new DuplicateTreeKeyException(keyId);
        }

        Trees t0 = null;
        switch (treeType) {
            case "CADUCA" -> t0 = this.registerCaduca(keyId, name, age, baseDiff);
            case "PERENE" -> t0 = this.registerPerene(keyId, name, age, baseDiff);
            default -> throw new UnknownTreeTypeException(treeType);
        }
        this.isDirty();
        return t0;
    }   
    
    /**
    * Register a new Perene tree to this Hotel.
    *
    * @param keyId The Id of the perene type tree.
    * @param name The name of the perene type tree.
    * @param age The age of the perene type tree.
    * @param baseDiff The base difficulty of the perene type tree.
    * @return The created {@link Perene} instance.
    */
    public Perene registerPerene(String keyId, String name,
                                 int age, int baseDiff) {

        Perene p0 = new Perene(keyId, name, age, baseDiff);
        if (p0 != null) {
            this._trees.put(keyId, p0);
        }
        return p0;
    }

    /**
    * Register a new Caduca tree to this Hotel.
    *
    * @param keyId The Id of the caduca type tree.
    * @param name The name of the caduca type tree.
    * @param age The age of the caduca type tree.
    * @param baseDiff The base difficulty of the caduca type tree.
    * @return The created {@link Caduca} instance.
    */
    public Caduca registerCaduca(String keyId, String name,
                                 int age, int baseDiff) {

        Caduca c0 = new Caduca(keyId, name, age, baseDiff);
        if (c0 != null) {
            this._trees.put(keyId, c0);
        }
        return c0;
    }


    /**
    * Register a new Vaccine to this Hotel.
    *
    * @param keyId The Id of the vaccine.
    * @param name The name of the vaccine.
    * @return The created {@link Vaccine} instance.
    *
    * @throws DuplicateVaccineKeyException
    */
    public Vaccine registerVaccine(String keyId, String name) 
                throws DuplicateVaccineKeyException {

        if (this.lookupVaccinebyId(keyId) != null) {
            throw new DuplicateVaccineKeyException(keyId);
        }

        Vaccine v0 = new Vaccine(keyId, name);
        if (v0 != null) {
            this._vaccines.put(keyId, v0);
        }
        this.isDirty();
        return v0;  
    }
    /*------------------------REGISTRATION FUNCTIONS--------------------END--*/

    /**
    * Return all the species as an unmodifiable collection.
    * 
    * @return a collection with all the species.
    */
    public Collection<Species> species() {
        return Collections.unmodifiableCollection(_species.values());
    }

    /**
    * Return all the animals as an unmodifiable collection.
    * 
    * @return a collection with all the animals.
    */
    public Collection<Animal> animals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    /**
    * Return all the employees as an unmodifiable collection.
    * 
    * @return a collection with all the employees.
    */
    public Collection<Employee> employees() {
        return Collections.unmodifiableCollection(_employees.values());
    }

    /**
    * Return all the habitats as an unmodifiable collection.
    * 
    * @return a collection with all the habitats.
    */
    public Collection<Habitat> habitats() {
        return Collections.unmodifiableCollection(_habitats.values());
    }
    
    /**
    * Return all the vaccines as an unmodifiable collection.
    * 
    * @return a collection with all the vaccines.
    */
    public Collection<Vaccine> vaccines() {
        return Collections.unmodifiableCollection(_vaccines.values());
    }
        
    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/
    
    /**
    * Get the hotel's current season.
    *
    * @return The current {@link Season}.
    */
    public Season getCurrentSeason() { 
        return this._currentSeason; 
    }
    
    
    /**
    * Lookup a hotel's species by it's keyId.
    *
    * @param speciesId The Id of the species.
    * @return The found {@link Species} or
    *         null, if not found.
    */
    public Species lookupSpeciesbyId(String speciesId) {
        return this._species.get(speciesId);
    }
    
    /**
    * Lookup a hotel's species by it's name.
    *
    * @param name The name of the species.
    * @return The found {@link Species} or
    *         null, if not found.
    */
    public Species lookupspeciesbyName(String name) {
        for (Species s : this.getAllSpecies()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }
    

    /**
    * Lookup a hotel's animal by it's keyId.
    *
    * @param animalId The Id of the animal.
    * @return The found {@link Aninal} or
    *         null, if not found.
    */
    public Animal lookupAnimalbyId(String animalId) {
        return this._animals.get(animalId);
    }
    
    
    /**
    * Lookup a hotel's habitat by it's keyId.
    *
    * @param habitatId The Id of the habitat.
    * @return The found {@link Habitat} or
    *         null, if not found.
    */
    public Habitat lookupHabitatbyId(String habitatId) {
        return this._habitats.get(habitatId);
    }
    
    
    /**
    * Lookup a hotel's tree by it's keyId.
    *
    * @param treeId The Id of the tree.
    * @return The found {@link Trees} or
    *         null, if not found.
    */
    public Trees lookupTreebyId(String treeId) {
        return this._trees.get(treeId);
    }
    
    
    /**
    * Lookup a hotel's employee by it's keyId.
    *
    * @param employeeId The Id of the employee.
    * @return The found {@link Employee} or
    *         null, if not found.
    */
    public Employee lookupEmployeebyId(String employeeId) {
        return this._employees.get(employeeId);
    }


    /**
    * Lookup a hotel's vaccine by it's keyId.
    *
    * @param vaccineId The Id of the vaccine.
    * @return The found {@link Vaccine} or
    *         null, if not found.
    */
    public Vaccine lookupVaccinebyId(String vaccineId) {
        return this._vaccines.get(vaccineId);
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
    * Get a collection of all the hotel's species
    * sorted by keyId.
    *
    * @return Collection<{@link Species}> containing
    *         the hotel's species.
    */
    public Collection<Species> getAllSpecies() {
        return this._species.values();
    }
    /*---------------------SPECIES MANAGEMENT FUNCTIONS-----------------END--*/


    /*--START--------------ANIMAL MANAGEMENT FUNCTIONS-----------------------*/

    /**
    * Get a collection of all the hotel's animals
    * sorted by keyId.
    *
    * @return Collection<{@link Species}> containing
    *         the hotel's animals.
    */
    public Collection<Animal> getAllAnimals() {
        return this._animals.values();
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
    * Get a collection of all the hotel's employees
    * sorted by keyId.
    *
    * @return Collection<{@link Employee}> containing
    *         the hotel's employees.
    */
    public Collection<Employee> getAllEmployees() {
        return this._employees.values();
    }

    /**
    * Get a collection of all the hotel's zookepers.
    *
    * @return Collection<{@link Zookeeper}> containing
    *         the hotel's zookepers.
    */
    //public Collection<Zookeeper> getAllZookeepers() {}

    /**
    * Get a collection of all the hotel's vets.
    *
    * @return Collection<{@link Vet}> containing
    *         the hotel's vets.
    */
    //public Collection<Vet> getAllVets() {}
    

    /**
    * Assign responsability to employee.
    *
    * @param responsabilityId The Id of the responsability to assing to employee.
    * @param employeeId The Id of the employee to assign the responsability to.
    *
    * @throws UnknownEmployeeKeyException 
    * @throws NoResponsibilityException 
    */
    public void addResponsability(String responsabilityId, String employeeId)
            throws UnknownEmployeeKeyException, NoResponsibilityException {

        Employee e = lookupEmployeebyId(employeeId);
        if (e == null) {
            throw new UnknownEmployeeKeyException(employeeId);
        }
        Species s = lookupSpeciesbyId(responsabilityId);
        Habitat h = lookupHabitatbyId(responsabilityId);
        if (s == null && h == null) {
            throw new NoResponsibilityException(responsabilityId); 
        }
        
        if (s != null) {
            Vet v = (Vet) e;
            s.addVettoAuthorized(v);
        }
        else if (h != null) {
            Zookeeper z = (Zookeeper) e;
            h.addZookeepertoHabitat(z);
        }
    } 
    
    /**
    * Remove responsability from employee.
    *
    * @param responsabilityId The Id of the responsability to remove from employee.
    * @param employeeId The Id of the employee to remove the responsability from.
    *
    * @throws UnknownEmployeeKeyException 
    * @throws NoResponsibilityException 
    */
    public void removeResponsability(String employeeId, String responsabilityId) {
        //TODO: if vet, should remove species from vet and vet from species
    }


    /**
    * Calculate an employee's current satisfaction value.
    * @see VetSatisfactionStrategy#calcSatisfaction()
    * @see ZookeeperSatisfactionStrategy#calcSatisfaction()
    *
    * @param employeeId the {@link Employee} to modify.
    * @return Math.round(satisfaction).
    *
    * @throws UnknownEmployeeKeyException
    */
    public int satisfactionbyEmployee(String employeeId) 
            throws UnknownEmployeeKeyException {

        if (lookupEmployeebyId(employeeId) == null) {
            throw new UnknownEmployeeKeyException(employeeId);
        }

        return lookupEmployeebyId(employeeId).calcSatisfaction();
    }
    /*--------------------EMPLOYEE MANAGEMENT FUNCTIONS-----------------END--*/


    /*--START--------------HABITAT MANAGEMENT FUNCTIONS----------------------*/

    /**
    * Get a collection of all the hotel's habitats
    * sorted by keyId.
    *
    * @return Collection<{@link Habitat}> containing
    *         the hotel's habitats.
    */
    public Collection<Habitat> getAllHabitats() {
        return this._habitats.values();
    }
    

    /**
    * Change an habitat's area value.
    *
    * @param habitatId the {@link Habitat} to modify.
    * @param newArea the new area value to set.
    *
    * @throws UnknownHabitatKeyException
    */
    public void changeHabitatArea(String habitatId, int newArea) 
            throws UnknownHabitatKeyException {

        if (lookupHabitatbyId(habitatId) == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }
        lookupHabitatbyId(habitatId).changeArea(newArea);
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
    * Register tree to habitat.
    *
    * @param treeId The Id of the tree to register to habitat.
    * @param habitatId The Id of the habitat to register the tree to.
    *
    * @throws UnknownTreeKeyException
    * @throws UnknownHabitatKeyException 
    */
    public void registerTreetoHabitat(String treeId, String habitatId) 
            throws UnknownTreeKeyException, UnknownHabitatKeyException {

        if(lookupTreebyId(treeId) == null) {
            throw new UnknownTreeKeyException(treeId);
        }
        if(lookupHabitatbyId(habitatId) == null) {
            throw new UnknownHabitatKeyException(habitatId);
        }

        lookupHabitatbyId(habitatId).addTreetoHabitat(lookupTreebyId(treeId));
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
    * Get a collection of all the hotel's vaccines
    * sorted by keyId.
    *
    * @return Collection<{@link Vaccine}> containing
    *         the hotel's vaccines.
    */
    public Collection<Vaccine> getAllVaccines() {
        return this._vaccines.values();
    }


    /**
    * Assign vaccine as adequate to species.
    *
    * @param speciesId The Id of the species to assing as safe.
    * @param vaccineId The Id of the vaccine to assign the species to.
    *
    * @throws UnknownSpeciesKeyException 
    * @throws UnknownVaccineKeyException 
    */
    public void addSafeSpecies(String speciesId, String vaccineId) 
            throws UnknownSpeciesKeyException, UnknownVaccineKeyException {

        if (lookupSpeciesbyId(speciesId) == null) {
            throw new UnknownSpeciesKeyException(speciesId);
        }
        if (lookupVaccinebyId(vaccineId) == null) {
            throw new UnknownVaccineKeyException(vaccineId);
        }
        
        lookupVaccinebyId(vaccineId).savetoVaccinate(lookupSpeciesbyId(speciesId));
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
    * Get a collection of all the hotel's applied 
    * vaccines sorted by cronological order.
    *
    * @return List<{@link VaccinationInstance}> containing
    *         the hotel's applied vaccines.
    */
    public List<VaccinationInstance> getApliedVaccines() {
        return this._vaccinationHistory;
    }
    /*---------------------VACCINE MANAGEMENT FUNCTIONS-----------------END--*/



    /**
    * Advances the hotel's season and returns
    * the value respective to the current season.
    *
    * @return 0 - Spring
    *       1 - Summer
    *       2 - Autumn
    *       3 - Winter
    */
    public int advanceSeason() {
        this._currentSeason = this._currentSeason.nextSeason();

        for (Habitat h : getAllHabitats()) {
            h.getCurrentSeason().nextSeason();
        }
        this.isDirty();
        return this._currentSeason.getSeasonalValue();
    }

}
