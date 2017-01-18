/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "policy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Policy.findAll", query = "SELECT p FROM Policy p")
    , @NamedQuery(name = "Policy.findByIdPolicy", query = "SELECT p FROM Policy p WHERE p.idPolicy = :idPolicy")})
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_policy")
    private Integer idPolicy;
    @ManyToMany(mappedBy = "policyList")
    private List<Action> actionList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "policy")
    private Treshhold treshhold;
    @JoinColumn(name = "id_event_type", referencedColumnName = "id_event_type")
    @ManyToOne
    private EventType idEventType;
    @JoinColumn(name = "id_metric_type", referencedColumnName = "id_metric_type")
    @ManyToOne
    private MetricType idMetricType;
    @JoinColumn(name = "id_policy_list", referencedColumnName = "id_policy_list")
    @ManyToOne
    private PolicyList idPolicyList;

    public Policy() {
    }

    public Policy(Integer idPolicy) {
        this.idPolicy = idPolicy;
    }

    public Integer getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(Integer idPolicy) {
        this.idPolicy = idPolicy;
    }

    @XmlTransient
    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    public Treshhold getTreshhold() {
        return treshhold;
    }

    public void setTreshhold(Treshhold treshhold) {
        this.treshhold = treshhold;
    }

    public EventType getIdEventType() {
        return idEventType;
    }

    public void setIdEventType(EventType idEventType) {
        this.idEventType = idEventType;
    }

    public MetricType getIdMetricType() {
        return idMetricType;
    }

    public void setIdMetricType(MetricType idMetricType) {
        this.idMetricType = idMetricType;
    }

    public PolicyList getIdPolicyList() {
        return idPolicyList;
    }

    public void setIdPolicyList(PolicyList idPolicyList) {
        this.idPolicyList = idPolicyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPolicy != null ? idPolicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Policy)) {
            return false;
        }
        Policy other = (Policy) object;
        if ((this.idPolicy == null && other.idPolicy != null) || (this.idPolicy != null && !this.idPolicy.equals(other.idPolicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.Policy[ idPolicy=" + idPolicy + " ]";
    }
    
}
