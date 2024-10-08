package hva.employees;

import hva.satisfactionStrategies.SatisfactionStrategy;
import hva.satisfactionStrategies.VetSatisfactionStrategy;

public abstract class Employee {

    private String _keyIdEmployee;

    private String _name;

    private SatisfactionStrategy _satisfactionStrategy;


    public Employee(String keyId, String name) {
        this._keyIdEmployee = keyId;
        this._name = name;
    }


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
    public abstract int calcSatisfaction();


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
