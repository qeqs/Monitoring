/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.beans.authentication;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.parser.ParseException;
import social_network_authentication.GoogleAuthorization;

@ManagedBean(name = "googleAuth")
@ViewScoped
public class GoogleAuthController implements Serializable {

    @EJB
    GoogleAuthorization googleBean;

    public void authorize() {
        googleBean.authorize();
    }

    protected String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        
        try {
            HashMap<String,String> map = googleBean.getUser(authCode);
          loginGoogle(map.get("nickname"),map.get("password"));
//  loginRoot();
        } catch (IOException ex) {
            Logger.getLogger(GoogleAuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GoogleAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 public void loginGoogle(String username, String password) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
            context.getExternalContext().redirect("../ConfirmedArea/instance.xhtml");
        } catch (ServletException ex) {
            Logger.getLogger(GoogleAuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
