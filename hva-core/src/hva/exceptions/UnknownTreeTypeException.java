package hva.exceptions;

import java.io.Serial;

public class UnknownTreeTypeException extends Exception {
    private final String _treeType;
    
    //@Serial
    //private static final long serialVersionUID = ;

    public UnknownTreeTypeException(String treeType) {
        this._treeType = treeType;
    }

    public String getKey() {
        return this._treeType;
    }
}
