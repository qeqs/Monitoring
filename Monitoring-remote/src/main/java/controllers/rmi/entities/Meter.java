package controllers.rmi.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "meters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meter.findAll", query = "SELECT m FROM Meter m")
    , @NamedQuery(name = "Meter.findByIdMeters", query = "SELECT m FROM Meter m WHERE m.idMeters = :idMeters")
    , @NamedQuery(name = "Meter.findByType", query = "SELECT m FROM Meter m WHERE m.type = :type")
    , @NamedQuery(name = "Meter.findByUnit", query = "SELECT m FROM Meter m WHERE m.unit = :unit")
    , @NamedQuery(name = "Meter.findByName", query = "SELECT m FROM Meter m WHERE m.name = :name")
    , @NamedQuery(name = "Meter.findByOid", query = "SELECT m FROM Meter m WHERE m.oid = :oid")
    , @NamedQuery(name = "Meter.findByDescription", query = "SELECT m FROM Meter m WHERE m.description = :description")
    , @NamedQuery(name = "Meter.findByGatherType", query = "SELECT m FROM Meter m WHERE m.gatherType = :gatherType")})
public class Meter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "system-uuid")
    @Size(min = 1, max = 100)
    @Column(name = "id_meters")
    private String idMeters;
    @Size(max = 100)
    @Column(name = "type")
    private String type;
    @Size(max = 100)
    @Column(name = "unit")
    private String unit;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "oid")
    private String oid;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Size(max = 100)
    @Column(name = "gather_type")
    private String gatherType;
    @OneToMany(mappedBy = "idMeter")
    private List<Measure> measureList;
    @OneToMany(mappedBy = "idMeter")
    private List<Policy> policyList;

    public Meter() {
    }

    public Meter(String idMeters) {
        this.idMeters = idMeters;
    }

    public String getIdMeters() {
        return idMeters;
    }

    public void setIdMeters(String idMeters) {
        this.idMeters = idMeters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGatherType() {
        return gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    @XmlTransient
    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
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
        hash += (idMeters != null ? idMeters.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meter)) {
            return false;
        }
        Meter other = (Meter) object;
        if ((this.idMeters == null && other.idMeters != null) || (this.idMeters != null && !this.idMeters.equals(other.idMeters))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.Meters[ idMeters=" + idMeters + " ]";
    }

}
