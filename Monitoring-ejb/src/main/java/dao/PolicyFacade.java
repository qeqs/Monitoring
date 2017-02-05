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
<<<<<<< HEAD
        return em.createNamedQuery("Policy.findByUsersAndMeter").getResultList();
=======
        return em.createNamedQuery("Policy.findByUsersAndMeter")
                .setParameter("idMeter", idMeter)
                .setParameter("uid", uid)
                .getResultList();
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
    } 

}
