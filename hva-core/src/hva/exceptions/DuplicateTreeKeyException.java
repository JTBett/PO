package hva.exceptions;

import java.io.Serial;

public class DuplicateTreeKeyException  extends Exception {
        
    //@Serial
    //private static final long serialVersionUID = ;
    
    private final String _keyId;

    
    public DuplicateTreeKeyException(String keyId) {
        this._keyId = keyId;
    }

    public String getKey() {
        return this._keyId;
    }
}
