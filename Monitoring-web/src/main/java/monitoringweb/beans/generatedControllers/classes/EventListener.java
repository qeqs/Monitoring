package monitoringweb.beans.generatedControllers.classes;

import controllers.rmi.entities.Measure;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import logic.Listener;
import logic.events.Event;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;
import org.primefaces.context.RequestContext;

@Named("eventListener")
@ApplicationScoped
public class EventListener implements Serializable {

    @EJB
    private logic.EventController eventController;

    @Schedules(value = @Schedule(second = "1"))
    public void timer() {
        System.out.println("monitoringweb.beans.generatedControllers.classes.EventListener.timer()");
        for (Event event : eventController.getEvents()) {
            JsfUtil.addMessage(event);
        }
        RequestContext.getCurrentInstance().update("growlEvents");

    }

    FacesContext context;
    RequestContext requestContext;
    private Listener listener = new Listener() {
        @Override
        public void onStoreEvent(Event event) {
            //JsfUtil.addMessage(event);

          //  RequestContext.getCurrentInstance().update("GraphicsForm:graphPanel");
        }

        @Override
        public void onStoreMeasure(Measure measure) {
        }
    };

    public void link(logic.EventController eventController) {
        eventController.addListener(listener);
    }

    public void unlink(logic.EventController eventController) {
        eventController.removeListener(listener);
    }

    @PostConstruct
    public void postLoad() {
        context = FacesContext.getCurrentInstance();
        requestContext = RequestContext.getCurrentInstance();
        link(eventController);
    }

    @PreDestroy
    public void preDestroy() {
        unlink(eventController);
    }

    public void addMessage(Event event) {
        FacesMessage.Severity severity;
        switch (event.getSeverity()) {
            case CLEAR:
                return;
            case INFO:
            case MINOR:
                severity = FacesMessage.SEVERITY_INFO;
                break;
            case WARNING:
            case MAJOR:
            case UNKNOWN:
                severity = FacesMessage.SEVERITY_WARN;
                break;
            case CRITICAL:
                severity = FacesMessage.SEVERITY_FATAL;
                break;
            default:
                severity = FacesMessage.SEVERITY_ERROR;
                break;
        }
        FacesMessage facesMsg = new FacesMessage(severity, event.toString(), event.description());
        context.addMessage("Event!", facesMsg);
        requestContext.update("growlEvents");
    }
}
