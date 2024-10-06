package hva.seasons;


public class Spring extends Season {

    public Spring() {
        this.setSeasonal(1, 1);
    }


    @Override
    public Season nextSeason() {
        return new Summer();  // Moves to the next season
    }

}