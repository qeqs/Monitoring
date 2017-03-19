package monitoringweb.beans.generatedControllers.classes;

import controllers.rmi.entities.Measure;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import logic.Listener;
import logic.events.Event;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;
import org.primefaces.context.RequestContext;

@ManagedBean
public class EventListener implements Serializable {
    
    
    private Listener listener = new Listener() {
        @Override
        public void onStoreEvent(Event event) {
            System.out.println(".onStoreEvent() EJJJJHG!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            JsfUtil.addMessage(event);
            RequestContext.getCurrentInstance().update("growlEvents");
        }

        @Override
        public void onStoreMeasure(Measure measure) {
        }
    };
    public void link(logic.EventController eventController){
        eventController.addListener(listener);
    }
    public void unlink(logic.EventController eventController){
        eventController.removeListener(listener);
    }

    @EJB
    private logic.EventController eventController;
        
    @PostConstruct
    public void postLoad() {        
        link(eventController);
    }

    @PreDestroy
    public void preDestroy() {
        unlink(eventController);
    }
}
