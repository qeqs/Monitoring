package dao;

import entities.Settings;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SettingsFacade extends AbstractFacade<Settings> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SettingsFacade() {
        super(Settings.class);
    }
    
    public Settings findByUid(String uid){
        return em.createNamedQuery("Settings.findByUid",Settings.class)
                .setParameter(0, uid)
                .getSingleResult();
    }

}
