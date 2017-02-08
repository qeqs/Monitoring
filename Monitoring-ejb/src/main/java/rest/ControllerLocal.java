
package rest;

import entities.Measure;
import javax.ejb.Local;
import logic.events.Event;

@Local(Controller.class)
public interface ControllerLocal {
    void storeMeasure(Measure measure);
    void storeEvent(Event event);
    void addListener(Listener listener);
} 
