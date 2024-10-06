package hva.satisfactionStrategies;

import hva.employees.Zookeeper;
import hva.habitats.Habitat;

public class ZookeperSatisfactionStrategy implements SatisfactionStrategy{
    
    private Zookeeper _zookeeper;

    public ZookeperSatisfactionStrategy(Zookeeper zookeeper) {
        this._zookeeper = zookeeper;
    }

    @Override
    public int calcSatisfaction() {
        
        double work = 0.0;
        for (Habitat habtitats : _zookeeper.getHabitatsunderResponsibility()) {
            /* 
            *    We know numberofTotalZookeepers() can never be zero in the context, the
            * habitat needs to be present on Zookeeper => it will always return >= 1.
            */
            work += habtitats.calcWorkinHabitat() / habtitats.numberofTotalZookeepers(); 
        }
        /* 
        * Cast to (float) since Math.round((double)) is wrong
        */
        return Math.round((float) (300 - work));
    }
}
