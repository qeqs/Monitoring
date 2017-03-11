package dao;

import controllers.rmi.entities.Profile;
import controllers.rmi.entities.Vnf;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProfileFacade extends AbstractFacade<Profile> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfileFacade() {
        super(Profile.class);
    }

    public boolean isProfileWithThisVnfExists(Vnf vnf) {

        return !em.createNamedQuery("Profile.findByVnf")
                .setParameter("vnf", vnf)
                .getResultList().isEmpty();
    }

}
