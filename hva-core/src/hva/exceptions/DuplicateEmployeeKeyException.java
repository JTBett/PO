package hva.exceptions;

import java.io.Serial;

public class DuplicateEmployeeKeyException  extends Exception {


    private final String _keyId;
    
    //@Serial
    //private static final long serialVersionUID = ;

    public DuplicateEmployeeKeyException(String keyId) {
        this._keyId = keyId;
    }

    public String getKey() {
        return this._keyId;
    }
}
