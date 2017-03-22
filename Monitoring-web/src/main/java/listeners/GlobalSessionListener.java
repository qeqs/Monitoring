/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 */
@WebListener
public class GlobalSessionListener implements  HttpSessionListener {
    
    
    @Inject 
    EjbListener ejbListener;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ejbListener.addSession(se.getSession());
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ejbListener.removeSession(se.getSession());
    }

}
