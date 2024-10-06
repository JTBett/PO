package hva.satisfactionStrategies;

import hva.habitats.Habitat;
import hva.animals.Animal;


public class AnimalSatisfactionStrategy implements SatisfactionStrategy {
    private Animal _animal;

    public AnimalSatisfactionStrategy(Animal animal) {
        this._animal = animal;
    }

    public Animal getAnimal() { return this._animal; }

    @Override
    public int calcSatisfaction() {
        
        int _sameSpecies = this.getAnimal().getHabitat().numberofAnimalsbySpecies(this._animal.getSpeciesId());
        int _diffSpecies = this.getAnimal().getHabitat().numberofDifferentAnimalsbySpecies(this._animal.getSpeciesId());
        int _area = this.getAnimal().getHabitat().getArea();
        int _totalAnimals = this.getAnimal().getHabitat().numberofTotalAnimals();
        int _adequacy = this.getAnimal().getHabitat().adequacybySpecies(this._animal.getSpeciesId());

        return Math.round(20 + 3 * _sameSpecies - 2 * _diffSpecies + _area / _totalAnimals + _adequacy);   
    }
}