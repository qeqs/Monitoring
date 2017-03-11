package dao;

import controllers.rmi.entities.Policy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PolicyFacade extends AbstractFacade<Policy> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolicyFacade() {
        super(Policy.class);
    }
    

}
