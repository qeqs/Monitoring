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
@Table(name = "instance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instance.findAll", query = "SELECT i FROM Instance i")
    , @NamedQuery(name = "Instance.findById", query = "SELECT i FROM Instance i WHERE i.id = :id")
    , @NamedQuery(name = "Instance.findByCreatedat", query = "SELECT i FROM Instance i WHERE i.createdat = :createdat")
    , @NamedQuery(name = "Instance.findByModifiedat", query = "SELECT i FROM Instance i WHERE i.modifiedat = :modifiedat")
    , @NamedQuery(name = "Instance.findByName", query = "SELECT i FROM Instance i WHERE i.name = :name")
    , @NamedQuery(name = "Instance.findByIdOpenstack", query = "SELECT i FROM Instance i WHERE i.idOpenstack = :idOpenstack")
    , @NamedQuery(name = "Instance.findByProjectId", query = "SELECT i FROM Instance i WHERE i.projectId = :projectId")
    , @NamedQuery(name = "Instance.findByIp", query = "SELECT i FROM Instance i WHERE i.ip = :ip")
    , @NamedQuery(name = "Instance.findByDate", query = "SELECT i FROM Instance i WHERE i.date = :date")
    , @NamedQuery(name = "Instance.findByTopPosition", query = "SELECT i FROM Instance i WHERE i.topPosition = :topPosition")
    , @NamedQuery(name = "Instance.findByLeftPosition", query = "SELECT i FROM Instance i WHERE i.leftPosition = :leftPosition")
    , @NamedQuery(name = "Instance.findByIdFlavor", query = "SELECT i FROM Instance i WHERE i.idFlavor = :idFlavor")
    , @NamedQuery(name = "Instance.findByIdImage", query = "SELECT i FROM Instance i WHERE i.idImage = :idImage")
    , @NamedQuery(name = "Instance.findByUsername", query = "SELECT i FROM Instance i WHERE i.username = :username")})
public class Instance implements Serializable {

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
    @Size(max = 45)
    @Column(name = "ip")
    private String ip;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 255)
    @Column(name = "top_position")
    private String topPosition;
    @Size(max = 255)
    @Column(name = "left_position")
    private String leftPosition;
    @Size(max = 255)
    @Column(name = "id_flavor")
    private String idFlavor;
    @Size(max = 255)
    @Column(name = "id_image")
    private String idImage;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "vnf_id", referencedColumnName = "id")
    @ManyToOne
    private Vnf vnfId;

    public Instance() {
    }

    public Instance(String id) {
        this.id = id;
    }

    public Instance(String id, Date createdat) {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getIdFlavor() {
        return idFlavor;
    }

    public void setIdFlavor(String idFlavor) {
        this.idFlavor = idFlavor;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (!(object instanceof Instance)) {
            return false;
        }
        Instance other = (Instance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Instance[ id=" + id + " ]";
    }

}
