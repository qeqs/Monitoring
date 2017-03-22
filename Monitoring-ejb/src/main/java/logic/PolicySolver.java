package logic;

import dao.PolicyFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Policy;
import controllers.rmi.entities.Profile;
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

    public void solve(Measure measure, Profile profile) {
        if (profile.getIdPolicyList() == null || profile.getIdPolicyList().getPolicyList() == null) {
            return;
        }
        List<Policy> policies = profile.getIdPolicyList().getPolicyList();
        for (Policy policy : policies) {
            if (policy.getEnabled() && policy.getIdPolicylist().getEnabled() && policy.getIdMeter().equals(measure.getIdMeter())) {
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
