package hva.trees;

import hva.seasons.Season;

public class Caduca extends Trees{

    //@Serial
    //private static final long serialVersionUID = ;
    
    public Caduca(String keyId, String name, int age, int baseDiff) {
        super(keyId, name, age, baseDiff);
    }

    /**
    * -description
    *
    * @param
    */
    @Override
    public double calcWorkForSeason(Season season) {

        return this.getBaseDifficulty() * season.getSeasonalCaduca() * Math.log(this.getAge() + 1);
    }
}
