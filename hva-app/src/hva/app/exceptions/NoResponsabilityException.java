package hva.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

import java.io.Serial;

public class NoResponsabilityException extends CommandException {
    @Serial
    private static final long serialVersionUID = 202407081733L;

    public NoResponsabilityException(String employeeKey, String responsibilityKey) {
        super(Message.noResponsibility(employeeKey, responsibilityKey));
    }
}
