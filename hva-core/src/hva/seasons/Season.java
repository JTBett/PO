package hva.seasons;


public abstract class Season {

    private int _seasonalCaduca;

    private int _seasonalPerene;

    private int _seasonalValue;

    public int getSeasonalCaduca() { return this._seasonalCaduca; }

    public int getSeasonalPerene() { return this._seasonalPerene; }

    public int getSeasonalValue() { return this._seasonalValue; }

    public void setSeasonalValue(int seasonVal) { this._seasonalValue = seasonVal; }

    public  void setSeasonal(int effortCaduca, int effortPerene) { 
        this._seasonalCaduca = effortCaduca;
        this._seasonalPerene = effortPerene;
    }


    // Advance the season to the next one
    public abstract Season nextSeason();

}