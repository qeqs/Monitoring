package monitoringweb.beans.generatedControllers.classes;

import controllers.rmi.entities.Event;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil.PersistAction;
import dao.EventFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.events.Severity;


@Named("eventController")
@SessionScoped
public class EventController implements Serializable {

    @EJB
    private EventFacade ejbFacade;
    private List<Event> items = null;
    private Event selected;
    private List<String> listActions;
    private List<Severity> priorities;

    public EventController() {
      createListActions();
      createPiorityList();
    }

    private void createListActions(){
        listActions = new ArrayList();
        listActions.add("None");
        listActions.add("Create Instance");
        listActions.add("Delete Instance");
        
    }
    private void createPiorityList(){
       priorities = new ArrayList();
       priorities.add(Severity.CLEAR);
       priorities.add(Severity.INFO);
       priorities.add(Severity.MINOR);
       priorities.add(Severity.UNKNOWN);
       priorities.add(Severity.MEDIUM);
       priorities.add(Severity.WARNING);
       priorities.add(Severity.MAJOR);
       priorities.add(Severity.CRITICAL);
    }
    
    public Event getSelected() {
        return selected;
    }

    public void setSelected(Event selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EventFacade getFacade() {
        return ejbFacade;
    }

    public Event prepareCreate() {
        selected = new Event();
        initializeEmbeddableKey();
        return selected;
    }
    
    public List<String> getActions(){
     
        return listActions;
    }
    
     public void setActions( List<String> list){
         this.listActions = list;
    }
     
      public List<Severity> getPriorities(){       
        return priorities;
    }
    
     public void setPriorities( List<Severity> list){
         this.priorities = list;
    }

    public List<String> getListActions() {
        return listActions;
    }

    public void setListActions(List<String> listActions) {
        this.listActions = listActions;
    }

    public void create() {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EventCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EventUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EventDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Event> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Event getEvent(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Event> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Event> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Event.class)
    public static class EventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventController controller = (EventController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventController");
            return controller.getEvent(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Event) {
                Event o = (Event) object;
                return getStringKey(o.getIdEvent());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Event.class.getName()});
                return null;
            }
        }

    }

}
