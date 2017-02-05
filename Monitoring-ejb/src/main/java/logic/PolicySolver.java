package logic;

import dao.PolicyFacade;
import entities.Measure;
import entities.Policy;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
<<<<<<< HEAD
=======
import logic.events.EventBuilder;
import rest.Controller;
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1

@Stateless
public class PolicySolver {

    @EJB
    PolicyFacade policyFacade;
<<<<<<< HEAD
=======
    @EJB
    Controller controller;
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1

    public void solve(Measure measure) {
        List<Policy> policies = policyFacade.findAllByUserAndMeter(measure.getIdMeter().getIdMeters(), measure.getUserId());
        for (Policy policy : policies) {
            if (policy.getEnabled() && policy.getIdPolicylist().getEnabled()) {
                double treshhold = policy.getTreshhold();
<<<<<<< HEAD
                switch (policy.getSign()) {
                    case ">":
                        if (measure.getValue() > treshhold) {
                           //todo: controller.storeEvent
=======
                EventBuilder eventBuilder = new EventBuilder();
                eventBuilder.createNewEvent()
                        .setMeasure(measure)
                        .setMeter(measure.getIdMeter())
                        .setPolicy(policy);
                switch (policy.getSign()) {
                    case ">":
                        if (measure.getValue() > treshhold) {
                            controller.storeEvent(eventBuilder.getEvent());
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
                        }
                        break;
                    case "<":
                        if (measure.getValue() < treshhold) {
<<<<<<< HEAD

=======
                            controller.storeEvent(eventBuilder.getEvent());
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
                        }
                        break;
                    case "=":
                        if (measure.getValue().equals(treshhold)) {
<<<<<<< HEAD

=======
                            controller.storeEvent(eventBuilder.getEvent());
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
                        }
                        break;
                }
            }
        }
    }
}
