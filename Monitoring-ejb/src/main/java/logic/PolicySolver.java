package logic;

import dao.PolicyFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Policy;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import logic.events.EventBuilder;

@Stateless
public class PolicySolver {

    @EJB
    PolicyFacade policyFacade;
    @EJB
    EventController controller;

    public void solve(Measure measure) {
        List<Policy> policies = policyFacade.findAllByUserAndMeter(measure.getIdMeter(), measure.getUserId());
        for (Policy policy : policies) {
            if (policy.getEnabled() && policy.getIdPolicylist().getEnabled()) {
                double treshhold = policy.getTreshhold();
                EventBuilder eventBuilder = new EventBuilder();
                eventBuilder.createNewEvent()
                        .setMeasure(measure)
                        .setMeter(measure.getIdMeter())
                        .setPolicy(policy);
                switch (policy.getSign()) {
                    case ">":
                        if (measure.getValue() > treshhold) {
                            controller.storeEvent(eventBuilder.getEvent());
                        }
                        break;
                    case "<":
                        if (measure.getValue() < treshhold) {
                            controller.storeEvent(eventBuilder.getEvent());
                        }
                        break;
                    case "=":
                        if (measure.getValue().equals(treshhold)) {
                            controller.storeEvent(eventBuilder.getEvent());
                        }
                        break;
                }
            }
        }
    }
}
