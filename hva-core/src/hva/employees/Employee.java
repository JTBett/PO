package hva.employees;

import hva.satisfactionStrategies.SatisfactionStrategy;
import hva.satisfactionStrategies.VetSatisfactionStrategy;

public class Employee {

    private String _keyIdEmployee;

    private String _name;

    private SatisfactionStrategy _satisfactionStrategy;


    public void setSatisfactionStrategy(SatisfactionStrategy strategy) {
        this._satisfactionStrategy = strategy;
    }

    public SatisfactionStrategy getSatisfactionStrategy() {
        return this._satisfactionStrategy;
    }


    /**
     * @see VetSatisfactionStrategy#calcSatisfaction()
     * @see ZookeeperSatisfactionStrategy#calcSatisfaction()
     */
    public int calcSatisfaction() {
        return _satisfactionStrategy.calcSatisfaction();
    }

    //TODO: abstract conversion
    /**
    * -description
    *
    * @param
    */
    public void addResponsability(Object responsability) {

    }

    //TODO: abstract conversion
    /**
    * -description
    *
    * @param
    */
    public void removeResponsability(String responsabilityId) {
        
    }
}
