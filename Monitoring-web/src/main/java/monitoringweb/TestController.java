package monitoringweb;

import entities.Measure;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import logic.EventController;
import logic.Listener;


@Named("testController")
@SessionScoped
public class TestController implements Serializable{

    
    @EJB
    private EventController controller;
    
    private List<logic.events.Event> events = new ArrayList<>();

    public List<logic.events.Event> getEvents() {
        return events;
    }
    @PostConstruct
    public void initialize(){
        controller.addListener(new Listener() {
            @Override
            public void onStoreEvent(logic.events.Event event) {
                events.add(event);
            }

            @Override
            public void onStoreMeasure(Measure measure) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
}
