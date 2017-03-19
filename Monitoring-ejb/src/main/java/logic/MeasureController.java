package logic;

import dao.MeasureFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Profile;
import controllers.rmi.entities.User;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AccessTimeout;
import javax.ejb.Timeout;

@Singleton
public class MeasureController {

    @EJB
    private MeasureFacade measureFacade;
    @EJB
    private PolicySolver solver;

    ArrayList<Listener> listeners = new ArrayList<>();

    public void storeMeasure(Measure measure, Profile profile) {

        for (Listener listener : listeners) {
            listener.onStoreMeasure(measure);
        }
        measureFacade.create(measure);
        try {
            solver.solve(measure, profile);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearMeasures() {
        measureFacade.deleteAll();
    }

    @Deprecated
    public void clearMeasures(User user) {
        measureFacade.deleteAllByUser(user);
    }

    public void clearMeasuresBefore(Date date) {
        measureFacade.deleteAllBeforeDate(date);
    }

    @Timeout
    @AccessTimeout(value = 2, unit = TimeUnit.MINUTES)
    public void clearMeasuresByProfile(Profile profile, Date date) {
        measureFacade.deleteAllByProfileAndDate(profile, date);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

}
