package hva.exceptions;

import java.io.Serial;

public class UnknownEmployeeSpecialtyException  extends Exception {
    private final String _specialty;
    
    //@Serial
    //private static final long serialVersionUID = ;

    public UnknownEmployeeSpecialtyException(String specialty) {
        this._specialty = specialty;
    }

    public String getKey() {
        return this._specialty;
    }
}
