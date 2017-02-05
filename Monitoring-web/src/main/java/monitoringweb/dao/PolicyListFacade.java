package monitoringweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import monitoringweb.entities.PolicyList;

@Stateless
public class PolicyListFacade extends AbstractFacade<PolicyList> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolicyListFacade() {
        super(PolicyList.class);
    }

}
