package logic;

import dao.PolicyFacade;
import entities.Measure;
import entities.Policy;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PolicySolver {

    @EJB
    PolicyFacade policyFacade;

    public void solve(Measure measure) {
        List<Policy> policies = policyFacade.findAllByUserAndMeter(measure.getIdMeter().getIdMeters(), measure.getUserId());
        for (Policy policy : policies) {
            if (policy.getEnabled() && policy.getIdPolicylist().getEnabled()) {
                double treshhold = policy.getTreshhold();
                switch (policy.getSign()) {
                    case ">":
                        if (measure.getValue() > treshhold) {
                           //todo: controller.storeEvent
                        }
                        break;
                    case "<":
                        if (measure.getValue() < treshhold) {

                        }
                        break;
                    case "=":
                        if (measure.getValue().equals(treshhold)) {

                        }
                        break;
                }
            }
        }
    }
}
