package hva.exceptions;

import java.io.Serial;

public class NoResponsibilityException extends Exception {

    private final String _responsabilityID;
    
    //@Serial
    //private static final long serialVersionUID = ;

    public NoResponsibilityException(String responsability) {
        this._responsabilityID = responsability;
    }

    public String getKey() {
        return this._responsabilityID;
    }
}
