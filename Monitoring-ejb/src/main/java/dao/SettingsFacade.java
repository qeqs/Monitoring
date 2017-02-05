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
<<<<<<< HEAD
        return (Settings) em.createNamedQuery("Settings.findByUid").getSingleResult();
=======
        return em.createNamedQuery("Settings.findByUid",Settings.class)
                .setParameter(0, uid)
                .getSingleResult();
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
    }

}
