package monitoringweb;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import logic.events.Event;
import rest.Controller;
import rest.Listener;
import scheduler.JobScheduler;


@Named("testController")
@SessionScoped
public class TestController implements Serializable{

    @EJB
    private JobScheduler scheduler;
    @EJB
    private Controller controller;
    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }
    @PostConstruct
    public void initialize(){
        scheduler.start();
        controller.addListener(new Listener() {
            @Override
            public void onStoreEvent(Event event) {
                events.add(event);
            }
        });
    }
    
}
