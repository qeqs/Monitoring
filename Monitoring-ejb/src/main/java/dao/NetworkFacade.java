package dao;

import controllers.rmi.entities.Network;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(NetworkFacadeRemote.class)
public class NetworkFacade extends AbstractFacade<Network> implements NetworkFacadeRemote{

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NetworkFacade() {
        super(Network.class);
    }

}
