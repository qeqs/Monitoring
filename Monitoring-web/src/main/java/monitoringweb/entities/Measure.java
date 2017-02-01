package monitoringweb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "measure")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Measure.findAll", query = "SELECT m FROM Measure m")
    , @NamedQuery(name = "Measure.findByIdMeasure", query = "SELECT m FROM Measure m WHERE m.idMeasure = :idMeasure")
    , @NamedQuery(name = "Measure.findByValue", query = "SELECT m FROM Measure m WHERE m.value = :value")
    , @NamedQuery(name = "Measure.findByTstamp", query = "SELECT m FROM Measure m WHERE m.tstamp = :tstamp")
    , @NamedQuery(name = "Measure.findByUserId", query = "SELECT m FROM Measure m WHERE m.userId = :userId")
    , @NamedQuery(name = "Measure.findByResource", query = "SELECT m FROM Measure m WHERE m.resource = :resource")})
public class Measure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_measure")
    private String idMeasure;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "tstamp")
    @Temporal(TemporalType.DATE)
    private Date tstamp;
    @Size(max = 40)
    @Column(name = "user_id")
    private String userId;
    @Size(max = 40)
    @Column(name = "resource")
    private String resource;
    @JoinColumn(name = "id_meter", referencedColumnName = "id_meters")
    @ManyToOne
    private Meters idMeter;

    public Measure() {
    }

    public Measure(String idMeasure) {
        this.idMeasure = idMeasure;
    }

    public String getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(String idMeasure) {
        this.idMeasure = idMeasure;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Meters getIdMeter() {
        return idMeter;
    }

    public void setIdMeter(Meters idMeter) {
        this.idMeter = idMeter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMeasure != null ? idMeasure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Measure)) {
            return false;
        }
        Measure other = (Measure) object;
        if ((this.idMeasure == null && other.idMeasure != null) || (this.idMeasure != null && !this.idMeasure.equals(other.idMeasure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "monitoringweb.entities.Measure[ idMeasure=" + idMeasure + " ]";
    }

}
