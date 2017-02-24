package controllers.rmi.entities;

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
@Table(name = "router_interface")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RouterInterface.findAll", query = "SELECT r FROM RouterInterface r")
    , @NamedQuery(name = "RouterInterface.findById", query = "SELECT r FROM RouterInterface r WHERE r.id = :id")
    , @NamedQuery(name = "RouterInterface.findByCreatedat", query = "SELECT r FROM RouterInterface r WHERE r.createdat = :createdat")
    , @NamedQuery(name = "RouterInterface.findByModifiedat", query = "SELECT r FROM RouterInterface r WHERE r.modifiedat = :modifiedat")
    , @NamedQuery(name = "RouterInterface.findByName", query = "SELECT r FROM RouterInterface r WHERE r.name = :name")
    , @NamedQuery(name = "RouterInterface.findByIdOpenstack", query = "SELECT r FROM RouterInterface r WHERE r.idOpenstack = :idOpenstack")
    , @NamedQuery(name = "RouterInterface.findByProjectId", query = "SELECT r FROM RouterInterface r WHERE r.projectId = :projectId")
    , @NamedQuery(name = "RouterInterface.findByUsername", query = "SELECT r FROM RouterInterface r WHERE r.username = :username")})
public class RouterInterface implements Serializable {

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
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "id_router", referencedColumnName = "id")
    @ManyToOne
    private Router idRouter;
    @JoinColumn(name = "id_subnet", referencedColumnName = "id")
    @ManyToOne
    private Subnet idSubnet;

    public RouterInterface() {
    }

    public RouterInterface(String id) {
        this.id = id;
    }

    public RouterInterface(String id, Date createdat) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Router getIdRouter() {
        return idRouter;
    }

    public void setIdRouter(Router idRouter) {
        this.idRouter = idRouter;
    }

    public Subnet getIdSubnet() {
        return idSubnet;
    }

    public void setIdSubnet(Subnet idSubnet) {
        this.idSubnet = idSubnet;
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
        if (!(object instanceof RouterInterface)) {
            return false;
        }
        RouterInterface other = (RouterInterface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.RouterInterface[ id=" + id + " ]";
    }

}
