/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "event_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventType.findAll", query = "SELECT e FROM EventType e")
    , @NamedQuery(name = "EventType.findByIdEventType", query = "SELECT e FROM EventType e WHERE e.idEventType = :idEventType")
    , @NamedQuery(name = "EventType.findByType", query = "SELECT e FROM EventType e WHERE e.type = :type")
    , @NamedQuery(name = "EventType.findByPriority", query = "SELECT e FROM EventType e WHERE e.priority = :priority")})
public class EventType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_event_type")
    private Integer idEventType;
    @Size(max = 100)
    @Column(name = "type")
    private String type;
    @Column(name = "priority")
    private Integer priority;
    @OneToMany(mappedBy = "idEventType")
    private List<Policy> policyList;

    public EventType() {
    }

    public EventType(Integer idEventType) {
        this.idEventType = idEventType;
    }

    public Integer getIdEventType() {
        return idEventType;
    }

    public void setIdEventType(Integer idEventType) {
        this.idEventType = idEventType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
        hash += (idEventType != null ? idEventType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventType)) {
            return false;
        }
        EventType other = (EventType) object;
        if ((this.idEventType == null && other.idEventType != null) || (this.idEventType != null && !this.idEventType.equals(other.idEventType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.EventType[ idEventType=" + idEventType + " ]";
    }
    
}
