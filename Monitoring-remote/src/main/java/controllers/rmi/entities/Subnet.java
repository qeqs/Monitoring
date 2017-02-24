package controllers.rmi.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "subnet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subnet.findAll", query = "SELECT s FROM Subnet s")
    , @NamedQuery(name = "Subnet.findById", query = "SELECT s FROM Subnet s WHERE s.id = :id")
    , @NamedQuery(name = "Subnet.findByCreatedat", query = "SELECT s FROM Subnet s WHERE s.createdat = :createdat")
    , @NamedQuery(name = "Subnet.findByModifiedat", query = "SELECT s FROM Subnet s WHERE s.modifiedat = :modifiedat")
    , @NamedQuery(name = "Subnet.findByName", query = "SELECT s FROM Subnet s WHERE s.name = :name")
    , @NamedQuery(name = "Subnet.findByIdOpenstack", query = "SELECT s FROM Subnet s WHERE s.idOpenstack = :idOpenstack")
    , @NamedQuery(name = "Subnet.findByIpVersion", query = "SELECT s FROM Subnet s WHERE s.ipVersion = :ipVersion")
    , @NamedQuery(name = "Subnet.findByCidr", query = "SELECT s FROM Subnet s WHERE s.cidr = :cidr")
    , @NamedQuery(name = "Subnet.findByUsername", query = "SELECT s FROM Subnet s WHERE s.username = :username")})
public class Subnet implements Serializable {

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
    @Size(max = 100)
    @Column(name = "id_openstack")
    private String idOpenstack;
    @Size(max = 10)
    @Column(name = "ip_version")
    private String ipVersion;
    @Size(max = 45)
    @Column(name = "cidr")
    private String cidr;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "id_network", referencedColumnName = "id")
    @OneToOne
    private Network idNetwork;
    @JoinColumn(name = "vnf_id", referencedColumnName = "id")
    @ManyToOne
    private Vnf vnfId;
    @OneToMany(mappedBy = "idSubnet")
    private List<RouterInterface> routerInterfaceList;

    public Subnet() {
    }

    public Subnet(String id) {
        this.id = id;
    }

    public Subnet(String id, Date createdat) {
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

    public String getIdOpenstack() {
        return idOpenstack;
    }

    public void setIdOpenstack(String idOpenstack) {
        this.idOpenstack = idOpenstack;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Network getIdNetwork() {
        return idNetwork;
    }

    public void setIdNetwork(Network idNetwork) {
        this.idNetwork = idNetwork;
    }

    public Vnf getVnfId() {
        return vnfId;
    }

    public void setVnfId(Vnf vnfId) {
        this.vnfId = vnfId;
    }

    @XmlTransient
    public List<RouterInterface> getRouterInterfaceList() {
        return routerInterfaceList;
    }

    public void setRouterInterfaceList(List<RouterInterface> routerInterfaceList) {
        this.routerInterfaceList = routerInterfaceList;
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
        if (!(object instanceof Subnet)) {
            return false;
        }
        Subnet other = (Subnet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Subnet[ id=" + id + " ]";
    }

}
