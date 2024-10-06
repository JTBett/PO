package hva.trees;

import hva.seasons.Season;

/* TREE causes inconvenience due to being already existent in java */
public abstract class TreeonHabitat {
    
    private String _keyIdTree;

    private String _name;

    private double _age;
    
    private int _baseDiff;


    public int getAge() {
        return (int)this._age; 
    }

    public int getBaseDifficulty() {
        return this._baseDiff; 
    }


    /**
    * -description
    *
    * @param
    */
    public void advanceSeasonTree() {
        this._age += 0.25;
    }


    /**
    * -description
    *
    * @param
    */
    public abstract double calcWorkForSeason(Season season);
    
}
