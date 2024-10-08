package hva.trees;

import hva.seasons.Season;

public class Perene extends Trees {

    //@Serial
    //private static final long serialVersionUID = ;

    public Perene(String keyId, String name, int age, int baseDiff) {
        super(keyId, name, age, baseDiff);
    }

    /**
    * -description
    *
    * @param
    */
    @Override
    public double calcWorkForSeason(Season season) {

        return this.getBaseDifficulty() * season.getSeasonalPerene() * Math.log(this.getAge() + 1);
    }
}
