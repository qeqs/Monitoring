
package logic;

import java.util.ArrayList;
import javax.ejb.Stateless;
import logic.events.Event;

@Stateless
public class EventController {
    
    
    ArrayList<Listener> listeners = new ArrayList<>();
    
    public void storeEvent(Event event) {
        for (Listener listener : listeners) {
            listener.onStoreEvent(event);
        }
        //todo: change smth on vnf side or send event somewhere
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

}
