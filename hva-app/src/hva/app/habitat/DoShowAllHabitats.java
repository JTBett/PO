package hva.app.habitat;

import hva.Hotel;
import hva.app.Stringifier;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllHabitats extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllHabitats(Hotel receiver) {
        super(Label.SHOW_ALL_HABITATS, receiver);
    }

    @Override
    protected void execute() throws CommandException{
        _receiver.getAllHabitats()
            .stream()
            .map(v -> v.accept(stringifier))
            .forEach(_display::popup);
    }
}
