/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Stateless
@LocalBean
public class SmtpAuthenticator extends Authenticator {

    public SmtpAuthenticator() {
        super();
    }
    
    public PasswordAuthentication getPasswordAuthentication() {

        String username = "openstackmonitor@inbox.ru";
        String password = "nc2017";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }
        return null;
    }
}
