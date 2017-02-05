package dao;

import entities.Policy;
import java.util.List;
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
    
    public List<Policy> findAllByUserAndMeter(String idMeter,String uid){
        return em.createNamedQuery("Policy.findByUsersAndMeter").getResultList();
    } 

}
