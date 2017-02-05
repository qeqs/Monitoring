package monitoringweb.beans.generatedControllers.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import monitoringweb.entities.Pm;

@Stateless
public class PmFacade extends AbstractFacade<Pm> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmFacade() {
        super(Pm.class);
    }

}
