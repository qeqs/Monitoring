package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "snmp_settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SnmpSettings.findAll", query = "SELECT s FROM SnmpSettings s")
    , @NamedQuery(name = "SnmpSettings.findByIdSnmp", query = "SELECT s FROM SnmpSettings s WHERE s.idSnmp = :idSnmp")
    , @NamedQuery(name = "SnmpSettings.findByCommunity", query = "SELECT s FROM SnmpSettings s WHERE s.community = :community")
    , @NamedQuery(name = "SnmpSettings.findByTarget", query = "SELECT s FROM SnmpSettings s WHERE s.target = :target")})
public class SnmpSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "id_snmp")
    private String idSnmp;
    @Size(max = 100)
    @Column(name = "community")
    private String community;
    @Size(max = 100)
    @Column(name = "target")
    private String target;

    public SnmpSettings() {
    }

    public SnmpSettings(String idSnmp) {
        this.idSnmp = idSnmp;
    }

    public String getIdSnmp() {
        return idSnmp;
    }

    public void setIdSnmp(String idSnmp) {
        this.idSnmp = idSnmp;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSnmp != null ? idSnmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SnmpSettings)) {
            return false;
        }
        SnmpSettings other = (SnmpSettings) object;
        if ((this.idSnmp == null && other.idSnmp != null) || (this.idSnmp != null && !this.idSnmp.equals(other.idSnmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.SnmpSettings[ idSnmp=" + idSnmp + " ]";
    }

}
