package hva.trees;

//import java.io.Serial;
import java.io.Serializable;

import hva.seasons.Season;

/* TREE causes inconvenience due to being already existent in java */
public abstract class Trees implements Serializable {

    //@Serial
    //private static final long serialVersionUID = ;
    
    private String _keyIdTree;

    private String _name;

    private double _age;
    
    private int _baseDiff;


    public Trees(String keyId, String name, int age, int baseDiff) {
        this._keyIdTree = keyId;
        this._name = name;
        this._age = age;
        this._baseDiff = baseDiff;
    }


    /*--START--------------------LOOKUP FUNCTIONS----------------------------*/

    public String getKeyId() { return this._keyIdTree; }
    
    public String getName() { return this._name; }
    
    public int getAge() { return (int)this._age; }
    
    public int getBaseDifficulty() { return this._baseDiff; }
    /*---------------------------LOOKUP FUNCTIONS-----------------------END--*/


    
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
