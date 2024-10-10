package hva.app.habitat;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllHabitats extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllHabitats(Hotel receiver) {
        super(Label.SHOW_ALL_HABITATS, receiver);
    }

    /** @see pt.tecnico.uilib.menu.Command#execute() */
    @Override
    protected final void execute() {
        _display.popup(_receiver.habitats());
    }
}
