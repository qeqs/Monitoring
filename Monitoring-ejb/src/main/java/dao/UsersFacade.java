package dao;

import controllers.rmi.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsersFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(User.class);
    }
    
     public User getUserByUsername(String username) {
         return (User)em.createNamedQuery("User.findByUsername").setParameter("username", username).getResultList().get(0);   
    }

}
