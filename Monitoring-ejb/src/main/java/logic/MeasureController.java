package logic;

import dao.MeasureFacade;
import entities.Measure;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.PolicySolver;
import logic.events.Event;
import java.util.ArrayList;

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

    public void storeEvent(Event event) {
        for (Listener listener : listeners) {
            listener.onStoreEvent(event);
        }
        //todo: change smth on vnf side or send event somewhere
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }


}
