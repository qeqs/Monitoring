package logic;

import java.util.ArrayList;
import javax.ejb.Stateless;
import logic.events.Event;
import rmi.Action;

@Stateless
public class EventController {

    ArrayList<Listener> listeners = new ArrayList<>();

    public void storeEvent(Event event) {
        for (Listener listener : listeners) {
            listener.onStoreEvent(event);
        }
        if (event.getActions() != null) {
            for (Action act : event.getActions()) {
                act.execute(event.getMeasure().getIdProfile());
            }
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

}
