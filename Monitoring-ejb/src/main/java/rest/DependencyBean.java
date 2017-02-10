
package rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import logic.events.Event;

@Stateless
public class DependencyBean {
    @EJB
    Controller controller;
    
    public void storeEvent(Event event){
        controller.storeEvent(event);
    }
}
