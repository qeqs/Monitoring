/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.beans.authentication;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import registration.AuthenticationBean;

@ManagedBean(name = "authController")
@ViewScoped
public class AuthController implements Serializable {

    @EJB
    AuthenticationBean authBean;
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/index.xhtml");
    }

    public String getUserName() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String userLogin = request.getRemoteUser();
        String userName = authBean.getUserName(userLogin);
        if(userName!=null){
            return userName;
        } else {
            return userLogin;
        }
    }

}
