package hva.satisfactionStrategies;


public interface SatisfactionStrategy {

    /**
     * @return the satisfaction value rounded up 
     *         to the nearest integer via Math.round
     */
    int calcSatisfaction();
    
}
