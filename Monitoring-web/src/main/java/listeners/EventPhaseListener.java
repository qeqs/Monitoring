package listeners;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import logic.events.Event;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;

public class EventPhaseListener implements PhaseListener{

    FacesContext context;
    @Override
    public void afterPhase(PhaseEvent event) {
        context = event.getFacesContext();
        Event globEvent = (Event) JsfUtil.getHttpSession(context).getAttribute(EjbListener.ATTRIBUTE);
        if(globEvent!=null)
        addMessage(globEvent);
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
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
    }

}
