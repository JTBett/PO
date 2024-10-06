package hva.satisfactionStrategies;

import hva.employees.Vet;
import hva.species.Species;

public class VetSatisfactionStrategy implements SatisfactionStrategy{
    
    private Vet _vet;

    public VetSatisfactionStrategy(Vet vet) {
        this._vet = vet;
    }

    @Override
    public int calcSatisfaction() {
        
        double work = 0.0;
        for (Species species : _vet.getSpeciesunderResponsibility()) {
            /* 
            *    We know numberofTotalVets() can never be zero in the context, the
            * species needs to be present on Vet => it will always return >= 1.
            */
            work += species.numberofTotalAnimals() / species.numberofTotalVets(); 
        }
        /* 
        * Cast to (float) since Math.round((double)) is wrong
        */
        return Math.round((float) (20 - work));
    }
}
