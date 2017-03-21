package dao;

import controllers.rmi.entities.Router;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(RouterFacadeRemote.class)
public class RouterFacade extends AbstractFacade<Router> implements RouterFacadeRemote{

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RouterFacade() {
        super(Router.class);
    }

}
