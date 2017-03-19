package monitoringweb.beans.generatedControllers.classes;

import controllers.rmi.entities.Measure;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.PostLoad;
import logic.Listener;
import logic.events.Event;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;


@Singleton
@Startup
public class EventListener implements Serializable{
    @EJB
    private logic.EventController eventController;
    private Listener listener = new Listener() {
            @Override
            public void onStoreEvent(Event event) {
                JsfUtil.addErrorMessage(event);
            }

            @Override
            public void onStoreMeasure(Measure measure) {
            }
        };
    
    @PostLoad
    public void postLoad(){
        eventController.addListener(listener);
    }
    @PreDestroy
    public void preDestroy(){
        eventController.removeListener(listener);
    }
}
