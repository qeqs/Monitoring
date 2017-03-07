package logic;

import dao.MeasureFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.User;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Date;

@Singleton
public class MeasureController {

    @EJB
    private MeasureFacade measureFacade;
    @EJB
    private PolicySolver solver;

    ArrayList<Listener> listeners = new ArrayList<>();

    public void storeMeasure(Measure measure) {

        for (Listener listener : listeners) {
            listener.onStoreMeasure(measure);
        }
        measureFacade.create(measure);
        solver.solve(measure);
    }
    public void clearMeasures(){
        measureFacade.deleteAll();
    }
    public void clearMeasures(User user){
        measureFacade.deleteAllByUser(user);
    }
    public void clearMeasuresBefore(Date date){
        measureFacade.deleteAllBeforeDate(date);
    }


    public void addListener(Listener listener) {
        listeners.add(listener);
    }


}
