/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "vnf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vnf.findAll", query = "SELECT v FROM Vnf v")
    , @NamedQuery(name = "Vnf.findByIdVnf", query = "SELECT v FROM Vnf v WHERE v.idVnf = :idVnf")
    , @NamedQuery(name = "Vnf.findByDateVnf", query = "SELECT v FROM Vnf v WHERE v.dateVnf = :dateVnf")
    , @NamedQuery(name = "Vnf.findByTypeVnf", query = "SELECT v FROM Vnf v WHERE v.typeVnf = :typeVnf")
    , @NamedQuery(name = "Vnf.findByScript", query = "SELECT v FROM Vnf v WHERE v.script = :script")})
public class Vnf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_vnf")
    private Integer idVnf;
    @Column(name = "date_vnf")
    @Temporal(TemporalType.DATE)
    private Date dateVnf;
    @Size(max = 100)
    @Column(name = "type_vnf")
    private String typeVnf;
    @Size(max = 100)
    @Column(name = "script")
    private String script;
    @JoinColumn(name = "id_vm", referencedColumnName = "id_vm")
    @ManyToOne
    private Vm idVm;

    public Vnf() {
    }

    public Vnf(Integer idVnf) {
        this.idVnf = idVnf;
    }

    public Integer getIdVnf() {
        return idVnf;
    }

    public void setIdVnf(Integer idVnf) {
        this.idVnf = idVnf;
    }

    public Date getDateVnf() {
        return dateVnf;
    }

    public void setDateVnf(Date dateVnf) {
        this.dateVnf = dateVnf;
    }

    public String getTypeVnf() {
        return typeVnf;
    }

    public void setTypeVnf(String typeVnf) {
        this.typeVnf = typeVnf;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Vm getIdVm() {
        return idVm;
    }

    public void setIdVm(Vm idVm) {
        this.idVm = idVm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVnf != null ? idVnf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vnf)) {
            return false;
        }
        Vnf other = (Vnf) object;
        if ((this.idVnf == null && other.idVnf != null) || (this.idVnf != null && !this.idVnf.equals(other.idVnf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.Vnf[ idVnf=" + idVnf + " ]";
    }
    
}
