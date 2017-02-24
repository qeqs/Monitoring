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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "router")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Router.findAll", query = "SELECT r FROM Router r")
    , @NamedQuery(name = "Router.findById", query = "SELECT r FROM Router r WHERE r.id = :id")
    , @NamedQuery(name = "Router.findByCreatedat", query = "SELECT r FROM Router r WHERE r.createdat = :createdat")
    , @NamedQuery(name = "Router.findByModifiedat", query = "SELECT r FROM Router r WHERE r.modifiedat = :modifiedat")
    , @NamedQuery(name = "Router.findByName", query = "SELECT r FROM Router r WHERE r.name = :name")
    , @NamedQuery(name = "Router.findByIdOpenstack", query = "SELECT r FROM Router r WHERE r.idOpenstack = :idOpenstack")
    , @NamedQuery(name = "Router.findByProjectId", query = "SELECT r FROM Router r WHERE r.projectId = :projectId")
    , @NamedQuery(name = "Router.findByTopPosition", query = "SELECT r FROM Router r WHERE r.topPosition = :topPosition")
    , @NamedQuery(name = "Router.findByLeftPosition", query = "SELECT r FROM Router r WHERE r.leftPosition = :leftPosition")
    , @NamedQuery(name = "Router.findByUsername", query = "SELECT r FROM Router r WHERE r.username = :username")})
public class Router implements Serializable {

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
    @JoinColumn(name = "id_network", referencedColumnName = "id")
    @ManyToOne
    private Network idNetwork;
    @JoinColumn(name = "vnf_id", referencedColumnName = "id")
    @ManyToOne
    private Vnf vnfId;
    @OneToMany(mappedBy = "idRouter")
    private List<RouterInterface> routerInterfaceList;

    public Router() {
    }

    public Router(String id) {
        this.id = id;
    }

    public Router(String id, Date createdat) {
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
        if (!(object instanceof Router)) {
            return false;
        }
        Router other = (Router) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Router[ id=" + id + " ]";
    }

}
