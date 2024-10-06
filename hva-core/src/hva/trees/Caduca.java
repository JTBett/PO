package hva.trees;

import hva.seasons.Season;

public class Caduca extends TreeonHabitat{
    
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
