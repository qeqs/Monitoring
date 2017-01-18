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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "vm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vm.findAll", query = "SELECT v FROM Vm v")
    , @NamedQuery(name = "Vm.findByIdVm", query = "SELECT v FROM Vm v WHERE v.idVm = :idVm")
    , @NamedQuery(name = "Vm.findByName", query = "SELECT v FROM Vm v WHERE v.name = :name")
    , @NamedQuery(name = "Vm.findByDateVm", query = "SELECT v FROM Vm v WHERE v.dateVm = :dateVm")
    , @NamedQuery(name = "Vm.findByIp", query = "SELECT v FROM Vm v WHERE v.ip = :ip")
    , @NamedQuery(name = "Vm.findByImage", query = "SELECT v FROM Vm v WHERE v.image = :image")
    , @NamedQuery(name = "Vm.findByExtId", query = "SELECT v FROM Vm v WHERE v.extId = :extId")})
public class Vm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_vm")
    private Integer idVm;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Column(name = "date_vm")
    @Temporal(TemporalType.DATE)
    private Date dateVm;
    @Size(max = 40)
    @Column(name = "ip")
    private String ip;
    @Size(max = 100)
    @Column(name = "image")
    private String image;
    @Column(name = "ext_id")
    private Integer extId;
    @JoinColumn(name = "id_pm", referencedColumnName = "id_pm")
    @ManyToOne
    private Pm idPm;
    @JoinColumn(name = "id_profile", referencedColumnName = "id_profile")
    @ManyToOne
    private Profile idProfile;
    @OneToMany(mappedBy = "idVm")
    private List<Vnf> vnfList;

    public Vm() {
    }

    public Vm(Integer idVm) {
        this.idVm = idVm;
    }

    public Integer getIdVm() {
        return idVm;
    }

    public void setIdVm(Integer idVm) {
        this.idVm = idVm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateVm() {
        return dateVm;
    }

    public void setDateVm(Date dateVm) {
        this.dateVm = dateVm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getExtId() {
        return extId;
    }

    public void setExtId(Integer extId) {
        this.extId = extId;
    }

    public Pm getIdPm() {
        return idPm;
    }

    public void setIdPm(Pm idPm) {
        this.idPm = idPm;
    }

    public Profile getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Profile idProfile) {
        this.idProfile = idProfile;
    }

    @XmlTransient
    public List<Vnf> getVnfList() {
        return vnfList;
    }

    public void setVnfList(List<Vnf> vnfList) {
        this.vnfList = vnfList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVm != null ? idVm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vm)) {
            return false;
        }
        Vm other = (Vm) object;
        if ((this.idVm == null && other.idVm != null) || (this.idVm != null && !this.idVm.equals(other.idVm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.Vm[ idVm=" + idVm + " ]";
    }
    
}
