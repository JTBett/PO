package hva.employees;

import hva.satisfactionStrategies.SatisfactionStrategy;

public abstract class Employee {

    private String _keyIdEmployee;

    private String _name;

    private SatisfactionStrategy _satisfactionStrategy;


    public Employee(String keyId, String name) {
        this._keyIdEmployee = keyId;
        this._name = name;
    }


    public String getKeyId() { return this._keyIdEmployee; }

    public String getName() { return this._name; }


    public void setSatisfactionStrategy(SatisfactionStrategy strategy) {
        this._satisfactionStrategy = strategy;
    }

    public SatisfactionStrategy getSatisfactionStrategy() {
        return this._satisfactionStrategy;
    }

    public abstract int calcSatisfaction();

    public abstract void addResponsability(Object responsability);

    public abstract void removeResponsability(String responsabilityId);
}
