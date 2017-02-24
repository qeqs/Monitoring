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
@Table(name = "network")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Network.findAll", query = "SELECT n FROM Network n")
    , @NamedQuery(name = "Network.findById", query = "SELECT n FROM Network n WHERE n.id = :id")
    , @NamedQuery(name = "Network.findByCreatedat", query = "SELECT n FROM Network n WHERE n.createdat = :createdat")
    , @NamedQuery(name = "Network.findByModifiedat", query = "SELECT n FROM Network n WHERE n.modifiedat = :modifiedat")
    , @NamedQuery(name = "Network.findByName", query = "SELECT n FROM Network n WHERE n.name = :name")
    , @NamedQuery(name = "Network.findByIdOpenstack", query = "SELECT n FROM Network n WHERE n.idOpenstack = :idOpenstack")
    , @NamedQuery(name = "Network.findByProjectId", query = "SELECT n FROM Network n WHERE n.projectId = :projectId")
    , @NamedQuery(name = "Network.findByTopPosition", query = "SELECT n FROM Network n WHERE n.topPosition = :topPosition")
    , @NamedQuery(name = "Network.findByLeftPosition", query = "SELECT n FROM Network n WHERE n.leftPosition = :leftPosition")
    , @NamedQuery(name = "Network.findByUsername", query = "SELECT n FROM Network n WHERE n.username = :username")})
public class Network implements Serializable {

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
    @Size(max = 100)
    @Column(name = "project_id")
    private String projectId;
    @Size(max = 255)
    @Column(name = "top_position")
    private String topPosition;
    @Size(max = 255)
    @Column(name = "left_position")
    private String leftPosition;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @OneToOne(mappedBy = "idNetwork")
    private Subnet subnet;
    @OneToMany(mappedBy = "idNetwork")
    private List<Router> routerList;
    @JoinColumn(name = "vnf_id", referencedColumnName = "id")
    @ManyToOne
    private Vnf vnfId;

    public Network() {
    }

    public Network(String id) {
        this.id = id;
    }

    public Network(String id, Date createdat) {
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTopPosition() {
        return topPosition;
    }

    public void setTopPosition(String topPosition) {
        this.topPosition = topPosition;
    }

    public String getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(String leftPosition) {
        this.leftPosition = leftPosition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subnet getSubnet() {
        return subnet;
    }

    public void setSubnet(Subnet subnet) {
        this.subnet = subnet;
    }

    @XmlTransient
    public List<Router> getRouterList() {
        return routerList;
    }

    public void setRouterList(List<Router> routerList) {
        this.routerList = routerList;
    }

    public Vnf getVnfId() {
        return vnfId;
    }

    public void setVnfId(Vnf vnfId) {
        this.vnfId = vnfId;
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
        if (!(object instanceof Network)) {
            return false;
        }
        Network other = (Network) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Network[ id=" + id + " ]";
    }

}
