package listeners;

import controllers.rmi.entities.Measure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import logic.EventController;
import logic.Listener;
import logic.events.Event;

@ApplicationScoped
public class EjbListener {
    
    public static String ATTRIBUTE = "globalEvent";
    
    List<HttpSession> sessions = new ArrayList<>();
    @EJB
    EventController eventController;

    Listener listener = new Listener() {
        @Override
        public void onStoreEvent(Event event) {
            for (HttpSession session : sessions) {
                session.setAttribute(ATTRIBUTE, event);
            }
        }

        @Override
        public void onStoreMeasure(Measure measure) {
        }
    };

    @PostConstruct
    public void postConstract() {
        eventController.addListener(listener);
    }

    public void addSession(HttpSession session) {
        sessions.add(session);
    }

    public void removeSession(HttpSession session) {
        sessions.remove(session);
    }

}
