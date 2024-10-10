package hva.app.employee;

import hva.Hotel;
import hva.app.Stringifier;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllEmployees extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    @Override
    protected void execute() throws CommandException{
        _receiver.getAllEmployees()
            .stream()
            .map(v -> v.accept(stringifier))
            .forEach(_display::popup);
    }

}
