/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "snmp_params")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SnmpParams.findAll", query = "SELECT s FROM SnmpParams s")
    , @NamedQuery(name = "SnmpParams.findByIdSnmpParams", query = "SELECT s FROM SnmpParams s WHERE s.idSnmpParams = :idSnmpParams")
    , @NamedQuery(name = "SnmpParams.findByUdpheader", query = "SELECT s FROM SnmpParams s WHERE s.udpheader = :udpheader")
    , @NamedQuery(name = "SnmpParams.findByCommunity", query = "SELECT s FROM SnmpParams s WHERE s.community = :community")
    , @NamedQuery(name = "SnmpParams.findByPduType", query = "SELECT s FROM SnmpParams s WHERE s.pduType = :pduType")})
public class SnmpParams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_snmp_params")
    private Integer idSnmpParams;
    @Size(max = 100)
    @Column(name = "udpheader")
    private String udpheader;
    @Size(max = 100)
    @Column(name = "community")
    private String community;
    @Size(max = 100)
    @Column(name = "pdu_type")
    private String pduType;
    @JoinColumn(name = "id_metric_type", referencedColumnName = "id_metric_type")
    @ManyToOne
    private MetricType idMetricType;

    public SnmpParams() {
    }

    public SnmpParams(Integer idSnmpParams) {
        this.idSnmpParams = idSnmpParams;
    }

    public Integer getIdSnmpParams() {
        return idSnmpParams;
    }

    public void setIdSnmpParams(Integer idSnmpParams) {
        this.idSnmpParams = idSnmpParams;
    }

    public String getUdpheader() {
        return udpheader;
    }

    public void setUdpheader(String udpheader) {
        this.udpheader = udpheader;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getPduType() {
        return pduType;
    }

    public void setPduType(String pduType) {
        this.pduType = pduType;
    }

    public MetricType getIdMetricType() {
        return idMetricType;
    }

    public void setIdMetricType(MetricType idMetricType) {
        this.idMetricType = idMetricType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSnmpParams != null ? idSnmpParams.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SnmpParams)) {
            return false;
        }
        SnmpParams other = (SnmpParams) object;
        if ((this.idSnmpParams == null && other.idSnmpParams != null) || (this.idSnmpParams != null && !this.idSnmpParams.equals(other.idSnmpParams))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.SnmpParams[ idSnmpParams=" + idSnmpParams + " ]";
    }
    
}
