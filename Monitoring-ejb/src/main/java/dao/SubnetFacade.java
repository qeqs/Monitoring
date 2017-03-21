package dao;

import controllers.rmi.entities.Subnet;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(SubnetFacadeRemote.class)
public class SubnetFacade extends AbstractFacade<Subnet> implements SubnetFacadeRemote{

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubnetFacade() {
        super(Subnet.class);
    }

}
