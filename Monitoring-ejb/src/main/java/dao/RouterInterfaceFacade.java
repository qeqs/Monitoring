package dao;

import controllers.rmi.entities.RouterInterface;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(RouterInterfaceFacadeRemote.class)
public class RouterInterfaceFacade extends AbstractFacade<RouterInterface> implements RouterInterfaceFacadeRemote{

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RouterInterfaceFacade() {
        super(RouterInterface.class);
    }

}
