package hva.exceptions;

import java.io.Serial;

public class InvalidEntryException extends Exception {
    
    //@Serial
    //private static final long serialVersionUID = ;

    /**
     * Invalid input arguments.
     */
    private String[] _inpArgs;


    public InvalidEntryException(String[] inputArgs) {
        this._inpArgs = inputArgs;
    }


    public InvalidEntryException(String[] inputArgs, Exception cause) {
        super(cause);
        this._inpArgs = inputArgs;
    }


    public String[] getEntrySpecification() {
        return this._inpArgs;
    }

}
