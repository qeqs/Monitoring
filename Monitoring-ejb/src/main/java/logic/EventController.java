package logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import logic.events.Event;
import controllers.rmi.entities.Action;

@Singleton
@Startup
public class EventController {

    ArrayList<Listener> listeners = new ArrayList<>();
   
    public void storeEvent(Event event) {
        try {
            for (Listener listener : listeners) {
                listener.onStoreEvent(event);
            }
            if (event.getActions() != null) {
                for (Action act : event.getActions()) {
                    act.execute(event.getMeasure().getIdProfile());
                }
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

}
