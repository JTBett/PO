package hva.seasons;


public class Summer extends Season {

    public Summer() {
        this.setSeasonal(2, 1);
    }


    @Override
    public Season nextSeason() {
        return new Autumn();  // Moves to the next season
    }

}