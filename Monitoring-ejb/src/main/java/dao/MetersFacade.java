package dao;

import entities.Meters;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MetersFacade extends AbstractFacade<Meters> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetersFacade() {
        super(Meters.class);
    }

}
