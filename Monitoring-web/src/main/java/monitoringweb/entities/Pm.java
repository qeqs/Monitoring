/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pm.findAll", query = "SELECT p FROM Pm p")
    , @NamedQuery(name = "Pm.findByIdPm", query = "SELECT p FROM Pm p WHERE p.idPm = :idPm")
    , @NamedQuery(name = "Pm.findByIp", query = "SELECT p FROM Pm p WHERE p.ip = :ip")
    , @NamedQuery(name = "Pm.findByDatePm", query = "SELECT p FROM Pm p WHERE p.datePm = :datePm")})
public class Pm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pm")
    private Integer idPm;
    @Size(max = 40)
    @Column(name = "ip")
    private String ip;
    @Column(name = "date_pm")
    @Temporal(TemporalType.DATE)
    private Date datePm;
    @OneToMany(mappedBy = "idPm")
    private List<Vm> vmList;

    public Pm() {
    }

    public Pm(Integer idPm) {
        this.idPm = idPm;
    }

    public Integer getIdPm() {
        return idPm;
    }

    public void setIdPm(Integer idPm) {
        this.idPm = idPm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDatePm() {
        return datePm;
    }

    public void setDatePm(Date datePm) {
        this.datePm = datePm;
    }

    @XmlTransient
    public List<Vm> getVmList() {
        return vmList;
    }

    public void setVmList(List<Vm> vmList) {
        this.vmList = vmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPm != null ? idPm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pm)) {
            return false;
        }
        Pm other = (Pm) object;
        if ((this.idPm == null && other.idPm != null) || (this.idPm != null && !this.idPm.equals(other.idPm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.Pm[ idPm=" + idPm + " ]";
    }
    
}
