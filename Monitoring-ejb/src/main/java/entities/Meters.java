package entities;

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

@Entity
@Table(name = "meters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meters.findAll", query = "SELECT m FROM Meters m")
    , @NamedQuery(name = "Meters.findByIdMeters", query = "SELECT m FROM Meters m WHERE m.idMeters = :idMeters")
    , @NamedQuery(name = "Meters.findByType", query = "SELECT m FROM Meters m WHERE m.type = :type")
    , @NamedQuery(name = "Meters.findByUnit", query = "SELECT m FROM Meters m WHERE m.unit = :unit")
    , @NamedQuery(name = "Meters.findByDescription", query = "SELECT m FROM Meters m WHERE m.description = :description")})
public class Meters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_meters")
    private String idMeters;
    @Size(max = 40)
    @Column(name = "type")
    private String type;
    @Size(max = 40)
    @Column(name = "unit")
    private String unit;
    @Size(max = 40)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "idMeter")
    private List<Measure> measureList;
    @OneToMany(mappedBy = "idMeter")
    private List<Policy> policyList;

    public Meters() {
    }

    public Meters(String idMeters) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Meters)) {
            return false;
        }
        Meters other = (Meters) object;
        if ((this.idMeters == null && other.idMeters != null) || (this.idMeters != null && !this.idMeters.equals(other.idMeters))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Meters[ idMeters=" + idMeters + " ]";
    }

}
