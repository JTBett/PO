package hva.vaccines;

public class VaccinationInstance {
    
    private String _vaccineId;

    private String _vetId;

    private String _animalId;

    private int _damage;

    public VaccinationInstance(String vaccineId, String vetId, String animalId, int damage) {
        this._vaccineId = vaccineId;
        this._vetId = vetId;
        this._animalId = animalId;
        this._damage = damage;
    }
}
