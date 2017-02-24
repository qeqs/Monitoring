package controllers.rmi.entities;

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

@Entity
@Table(name = "vnf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vnf.findAll", query = "SELECT v FROM Vnf v")
    , @NamedQuery(name = "Vnf.findById", query = "SELECT v FROM Vnf v WHERE v.id = :id")
    , @NamedQuery(name = "Vnf.findByCreatedat", query = "SELECT v FROM Vnf v WHERE v.createdat = :createdat")
    , @NamedQuery(name = "Vnf.findByModifiedat", query = "SELECT v FROM Vnf v WHERE v.modifiedat = :modifiedat")
    , @NamedQuery(name = "Vnf.findByName", query = "SELECT v FROM Vnf v WHERE v.name = :name")
    , @NamedQuery(name = "Vnf.findByUsername", query = "SELECT v FROM Vnf v WHERE v.username = :username")})
public class Vnf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;
    @Column(name = "modifiedat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @OneToMany(mappedBy = "vnfId")
    private List<Subnet> subnetList;
    @OneToMany(mappedBy = "vnfId")
    private List<Router> routerList;
    @OneToMany(mappedBy = "vnfId")
    private List<Instance> instanceList;
    @OneToMany(mappedBy = "vnfId")
    private List<Network> networkList;

    public Vnf() {
    }

    public Vnf(String id) {
        this.id = id;
    }

    public Vnf(String id, Date createdat) {
        this.id = id;
        this.createdat = createdat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(Date modifiedat) {
        this.modifiedat = modifiedat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public List<Subnet> getSubnetList() {
        return subnetList;
    }

    public void setSubnetList(List<Subnet> subnetList) {
        this.subnetList = subnetList;
    }

    @XmlTransient
    public List<Router> getRouterList() {
        return routerList;
    }

    public void setRouterList(List<Router> routerList) {
        this.routerList = routerList;
    }

    @XmlTransient
    public List<Instance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    @XmlTransient
    public List<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<Network> networkList) {
        this.networkList = networkList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vnf)) {
            return false;
        }
        Vnf other = (Vnf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vnf[ id=" + id + " ]";
    }

}
