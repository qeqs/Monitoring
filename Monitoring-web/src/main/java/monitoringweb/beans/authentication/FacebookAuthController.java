/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.beans.authentication;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.ParseException;
import social_network_authentication.FacebookAuthorization;

@ManagedBean(name = "fbAuth")
@ViewScoped
public class FacebookAuthController implements Serializable {

    @EJB
    FacebookAuthorization fbBean;

    public void authorize() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    
        String redirectUri = fbBean.authorize();
        try {
            ec.redirect(redirectUri);
        } catch (IOException ex) {
            Logger.getLogger(GoogleAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String authCode;
    protected String error;
    protected String error_reason;
    protected String error_description;
    protected String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        try {
            HashMap<String, String> map = fbBean.getUser(authCode);
            loginFb(map.get("nickname"), map.get("password"));
        } catch (IOException ex) {
            Logger.getLogger(FacebookAuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FacebookAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getError() {
        return error;
    }

    public String getError_reason() {
        return error_reason;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setError_reason(String error_reason) {
        this.error_reason = error_reason;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public void loginFb(String username, String password) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
            context.getExternalContext().redirect("../menupage.xhtml");
        } catch (ServletException ex) {
            Logger.getLogger(FacebookAuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacebookAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void takeCode() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String code = params.get("code");
        setAuthCode(code);
    }
}
