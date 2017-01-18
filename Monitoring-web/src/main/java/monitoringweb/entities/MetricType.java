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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "metric_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetricType.findAll", query = "SELECT m FROM MetricType m")
    , @NamedQuery(name = "MetricType.findByIdMetricType", query = "SELECT m FROM MetricType m WHERE m.idMetricType = :idMetricType")
    , @NamedQuery(name = "MetricType.findByTypeMetric", query = "SELECT m FROM MetricType m WHERE m.typeMetric = :typeMetric")})
public class MetricType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_metric_type")
    private Integer idMetricType;
    @Size(max = 100)
    @Column(name = "type_metric")
    private String typeMetric;
    @OneToMany(mappedBy = "idMetricType")
    private List<SnmpParams> snmpParamsList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "metricType")
    private Treshhold treshhold;
    @OneToMany(mappedBy = "idMetricType")
    private List<Policy> policyList;

    public MetricType() {
    }

    public MetricType(Integer idMetricType) {
        this.idMetricType = idMetricType;
    }

    public Integer getIdMetricType() {
        return idMetricType;
    }

    public void setIdMetricType(Integer idMetricType) {
        this.idMetricType = idMetricType;
    }

    public String getTypeMetric() {
        return typeMetric;
    }

    public void setTypeMetric(String typeMetric) {
        this.typeMetric = typeMetric;
    }

    @XmlTransient
    public List<SnmpParams> getSnmpParamsList() {
        return snmpParamsList;
    }

    public void setSnmpParamsList(List<SnmpParams> snmpParamsList) {
        this.snmpParamsList = snmpParamsList;
    }

    public Treshhold getTreshhold() {
        return treshhold;
    }

    public void setTreshhold(Treshhold treshhold) {
        this.treshhold = treshhold;
    }

    @XmlTransient
    public List<Policy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMetricType != null ? idMetricType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetricType)) {
            return false;
        }
        MetricType other = (MetricType) object;
        if ((this.idMetricType == null && other.idMetricType != null) || (this.idMetricType != null && !this.idMetricType.equals(other.idMetricType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.MetricType[ idMetricType=" + idMetricType + " ]";
    }
    
}
