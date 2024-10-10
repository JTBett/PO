package hva.app.vaccine;

import hva.Hotel;
import hva.app.Stringifier;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllVaccines extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllVaccines(Hotel receiver) {
        super(Label.SHOW_ALL_VACCINES, receiver);
    }

    @Override
    protected void execute() throws CommandException{
        _receiver.getAllEmployees()
            .stream()
            .map(v -> v.accept(stringifier))
            .forEach(_display::popup);
    }
}
