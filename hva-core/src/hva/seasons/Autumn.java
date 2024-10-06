package hva.seasons;


public class Autumn extends Season {

    public Autumn() {
        this.setSeasonal(5, 1);
    }

    @Override
    public Season nextSeason() {
        return new Winter();
    }

}

