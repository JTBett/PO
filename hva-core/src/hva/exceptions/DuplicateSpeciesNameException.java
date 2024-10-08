package hva.exceptions;

import java.io.Serial;

public class DuplicateSpeciesNameException extends Exception {

    private final String _name;
    
    //@Serial
    //private static final long serialVersionUID = ;

    public DuplicateSpeciesNameException(String name) {
        this._name = name;
    }

    public String getKey() {
        return this._name;
    }
}
