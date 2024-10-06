package hva.trees;

import hva.seasons.Season;

public class Perene extends TreeonHabitat{

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
