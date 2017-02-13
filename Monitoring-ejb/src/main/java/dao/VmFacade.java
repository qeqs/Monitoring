package dao;

import entities.Vm;
import entities.Pm;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class VmFacade extends AbstractFacade<Vm> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VmFacade() {
        super(Vm.class);
    }
    
    public List<Vm> findByPm(Pm pm) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Vm.class);
        cq.where(cb.equal(e.get("idPm"),pm));
        Query query = em.createQuery(cq);
        return query.getResultList();
    }

}
