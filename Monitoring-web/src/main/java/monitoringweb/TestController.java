package monitoringweb;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import rest.ControllerLocal;
import rest.Listener;
import scheduler.JobSchedulerLocal;


@Named("testController")
@SessionScoped
public class TestController implements Serializable{

    @EJB
    private JobSchedulerLocal scheduler;
    @EJB
    private ControllerLocal controller;
    private List<logic.events.Event> events = new ArrayList<>();

    public List<logic.events.Event> getEvents() {
        return events;
    }
    @PostConstruct
    public void initialize(){
        
        
        
        scheduler.start();
        controller.addListener(new Listener() {
            @Override
            public void onStoreEvent(logic.events.Event event) {
                events.add(event);
            }
        });
    }
    
}
