package monitoringweb.beans;

import monitoringweb.entities.Treshhold;
import monitoringweb.beans.util.JsfUtil;
import monitoringweb.beans.util.JsfUtil.PersistAction;
import monitoringweb.dao.TreshholdFacade;

import java.io.Serializable;
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

@Named("treshholdController")
@SessionScoped
public class TreshholdController implements Serializable {

    @EJB
    private monitoringweb.dao.TreshholdFacade ejbFacade;
    private List<Treshhold> items = null;
    private Treshhold selected;

    public TreshholdController() {
    }

    public Treshhold getSelected() {
        return selected;
    }

    public void setSelected(Treshhold selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getTreshholdPK().setIdPolicy(selected.getPolicy().getIdPolicy());
        selected.getTreshholdPK().setIdMType(selected.getMetricType().getIdMetricType());
    }

    protected void initializeEmbeddableKey() {
        selected.setTreshholdPK(new monitoringweb.entities.TreshholdPK());
    }

    private TreshholdFacade getFacade() {
        return ejbFacade;
    }

    public Treshhold prepareCreate() {
        selected = new Treshhold();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TreshholdCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TreshholdUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TreshholdDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Treshhold> getItems() {
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

    public Treshhold getTreshhold(monitoringweb.entities.TreshholdPK id) {
        return getFacade().find(id);
    }

    public List<Treshhold> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Treshhold> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Treshhold.class)
    public static class TreshholdControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TreshholdController controller = (TreshholdController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "treshholdController");
            return controller.getTreshhold(getKey(value));
        }

        monitoringweb.entities.TreshholdPK getKey(String value) {
            monitoringweb.entities.TreshholdPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new monitoringweb.entities.TreshholdPK();
            key.setIdMType(Integer.parseInt(values[0]));
            key.setIdPolicy(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(monitoringweb.entities.TreshholdPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdMType());
            sb.append(SEPARATOR);
            sb.append(value.getIdPolicy());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Treshhold) {
                Treshhold o = (Treshhold) object;
                return getStringKey(o.getTreshholdPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Treshhold.class.getName()});
                return null;
            }
        }

    }

}
