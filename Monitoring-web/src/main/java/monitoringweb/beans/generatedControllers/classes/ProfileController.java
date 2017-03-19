package monitoringweb.beans.generatedControllers.classes;

import controllers.rmi.entities.Profile;
import controllers.rmi.entities.User;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil;
import monitoringweb.beans.generatedControllers.classes.util.JsfUtil.PersistAction;
import dao.ProfileFacade;
import dao.UsersFacade;

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
import javax.servlet.http.HttpServletRequest;
import scheduler.SchedulerController;

@Named("profileController")
@SessionScoped
public class ProfileController implements Serializable {

    @EJB
    private ProfileFacade ejbFacade;
    @EJB
    private UsersFacade userdFacade;
    @EJB
    private SchedulerController schedulerController;
    private List<Profile> items = null;
    private Profile selected;
    private Profile profileForView;

    public void setProfileForView(Profile profile) {
        this.profileForView = profile;
    }

    public Profile getProfileForView() {
        return profileForView;
    }

    public ProfileController() {
    }

    public Profile getSelected() {
        return selected;
    }

    public void setSelected(Profile selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProfileFacade getFacade() {
        return ejbFacade;
    }

    public Profile prepareCreate() {
        selected = new Profile();
        initializeEmbeddableKey();
        return selected;
    }

    private List<User> prepareUserlist() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getRemoteUser();
        User user = userdFacade.getUserByUsername(username);
        List<User> list = new ArrayList();
        list.add(user);
        return list;
    }

    public void create() {
        selected.setUsersList(prepareUserlist());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProfileCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setUsersList(prepareUserlist());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProfileUpdated"));

    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProfileDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Profile> getItems() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getRemoteUser();

        List<Profile> profiles = getFacade().findAll();
        List<User> users;
        items = new ArrayList<>();
        for (Profile pr : profiles) {
            users = pr.getUsersList();
            if (users != null) {
                for (User usr : users) {
                    if (usr.getUsername().equals(username)) {
                        items.add(pr);
                    }
                }
            }
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                    //если соберешься делать изменение расписания для сбора метрик, 
                    //не забудь что этот метод ставит дефолт значения при сборе
                    schedulerController.createMonitor(selected);
                } else {
                    getFacade().remove(selected);
                    schedulerController.delete(selected);
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

    public Profile getProfile(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Profile> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Profile> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Profile.class)
    public static class ProfileControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfileController controller = (ProfileController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "profileController");
            return controller.getProfile(getKey(value));
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
            if (object instanceof Profile) {
                Profile o = (Profile) object;
                return getStringKey(o.getIdProfile());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Profile.class.getName()});
                return null;
            }
        }

    }

}
