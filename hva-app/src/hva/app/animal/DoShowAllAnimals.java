package hva.app.animal;

import hva.Hotel;
import hva.app.Stringifier;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoShowAllAnimals extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected void execute() throws CommandException{
        _receiver.getAllAnimals()
            .stream()
            .map(v -> v.accept(stringifier))
            .forEach(_display::popup);
    }
}
