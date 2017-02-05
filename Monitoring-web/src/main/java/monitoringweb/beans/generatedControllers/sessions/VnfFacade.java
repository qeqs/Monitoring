package monitoringweb.beans.generatedControllers.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import monitoringweb.entities.Vnf;

@Stateless
public class VnfFacade extends AbstractFacade<Vnf> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VnfFacade() {
        super(Vnf.class);
    }

}
