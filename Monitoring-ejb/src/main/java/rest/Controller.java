package rest;

import dao.MeasureFacade;
import entities.Measure;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.PolicySolver;
import logic.events.Event;

@Stateful
public class Controller {

    @EJB
    private MeasureFacade measureFacade;
    @EJB
    private PolicySolver solver;

    
    
    public void storeMeasure(Measure measure) {

        measureFacade.create(measure);
        solver.solve(measure);
    }
    public void storeEvent(Event event){
        
    }

}
