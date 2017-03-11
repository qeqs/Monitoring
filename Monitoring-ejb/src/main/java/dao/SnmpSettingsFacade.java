package dao;

import controllers.rmi.entities.SnmpSettings;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SnmpSettingsFacade extends AbstractFacade<SnmpSettings>  {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SnmpSettingsFacade() {
        super(SnmpSettings.class);
    }

}
