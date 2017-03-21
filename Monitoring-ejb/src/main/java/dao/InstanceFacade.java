package dao;

import controllers.rmi.entities.Instance;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(InstanceFacadeRemote.class)
public class InstanceFacade extends AbstractFacade<Instance> implements InstanceFacadeRemote{

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstanceFacade() {
        super(Instance.class);
    }

}
