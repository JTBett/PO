package hva.exceptions;

import java.io.Serial;

public class DuplicateHabitatKeyException  extends Exception {

    //@Serial
    //private static final long serialVersionUID = ;
    
    private final String _keyId;

    
    public DuplicateHabitatKeyException(String keyId) {
        this._keyId = keyId;
    }

    public String getKey() {
        return this._keyId;
    }
}
