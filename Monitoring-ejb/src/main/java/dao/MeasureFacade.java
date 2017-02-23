package dao;

import entities.Measure;
import entities.Meter;
import entities.User;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

@Stateless
public class MeasureFacade extends AbstractFacade<Measure> {

    @PersistenceContext(unitName = "virtualizationapp_Monitoring-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MeasureFacade() {
        super(Measure.class);
    }

    public List<Measure> findAllOrderedByTime() {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Measure.class);
        cq.orderBy(cb.desc(e.get("tstamp")));
        Query query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Measure> findAllForMeterOrderedByTime(Meter met) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Measure.class);
        cq.where(cb.equal(e.get("idMeter"), met));
        cq.orderBy(cb.desc(e.get("tstamp")));
        Query query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Measure> findByDate(Date dateFrom, Date dateTo) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(Measure.class);
        ParameterExpression<Date> parameter = cb.parameter(Date.class);
        cq.where(cb.between(parameter, dateFrom, dateTo));
        Query query = em.createQuery(cq);
        return query.getResultList();

    }

    public void deleteAll() {
        em.createNamedQuery("Measure.deleteAll").executeUpdate();
    }
    public void deleteAllByUser(User userId){
        em.createNamedQuery("Measure.deleteAllByUser").setParameter("userId", userId.getUid()).executeUpdate();        
    }
    public void deleteAllBeforeDate(Date date){
        em.createNamedQuery("Measure.deleteAllBeforeDate").setParameter("date", date).executeUpdate();
    }
}
