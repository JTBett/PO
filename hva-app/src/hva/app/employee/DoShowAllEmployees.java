package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllEmployees extends Command<Hotel> {

    private final Stringifier stringifier = new Stringifier();

    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    /** @see pt.tecnico.uilib.menu.Command#execute() */
    @Override
    protected final void execute() {
        // Iterate through the employees and display each one using their toString() method
        for (Employee e : _receiver.employees()) {
            _display.addLine(e.toString()); // This will call Vet, Zookeeper toString()
        }
        _display.display();
    }  

}
