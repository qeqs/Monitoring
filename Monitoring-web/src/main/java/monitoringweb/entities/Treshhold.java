/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "treshhold")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treshhold.findAll", query = "SELECT t FROM Treshhold t")
    , @NamedQuery(name = "Treshhold.findByIdMType", query = "SELECT t FROM Treshhold t WHERE t.treshholdPK.idMType = :idMType")
    , @NamedQuery(name = "Treshhold.findByIdPolicy", query = "SELECT t FROM Treshhold t WHERE t.treshholdPK.idPolicy = :idPolicy")
    , @NamedQuery(name = "Treshhold.findBySign", query = "SELECT t FROM Treshhold t WHERE t.sign = :sign")
    , @NamedQuery(name = "Treshhold.findByValue", query = "SELECT t FROM Treshhold t WHERE t.value = :value")})
public class Treshhold implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TreshholdPK treshholdPK;
    @Size(max = 100)
    @Column(name = "sign")
    private String sign;
    @Size(max = 100)
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "id_m_type", referencedColumnName = "id_metric_type", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private MetricType metricType;
    @JoinColumn(name = "id_policy", referencedColumnName = "id_policy", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Policy policy;

    public Treshhold() {
    }

    public Treshhold(TreshholdPK treshholdPK) {
        this.treshholdPK = treshholdPK;
    }

    public Treshhold(int idMType, int idPolicy) {
        this.treshholdPK = new TreshholdPK(idMType, idPolicy);
    }

    public TreshholdPK getTreshholdPK() {
        return treshholdPK;
    }

    public void setTreshholdPK(TreshholdPK treshholdPK) {
        this.treshholdPK = treshholdPK;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treshholdPK != null ? treshholdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treshhold)) {
            return false;
        }
        Treshhold other = (Treshhold) object;
        if ((this.treshholdPK == null && other.treshholdPK != null) || (this.treshholdPK != null && !this.treshholdPK.equals(other.treshholdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.Treshhold[ treshholdPK=" + treshholdPK + " ]";
    }
    
}
