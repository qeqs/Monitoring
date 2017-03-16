package controllers.rmi.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "policy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Policy.findAll", query = "SELECT p FROM Policy p")
    , @NamedQuery(name = "Policy.findByIdPolicy", query = "SELECT p FROM Policy p WHERE p.idPolicy = :idPolicy")
    , @NamedQuery(name = "Policy.findByUsers", query = "SELECT p FROM Policy p WHERE p.users = :users")
    , @NamedQuery(name = "Policy.findByResource", query = "SELECT p FROM Policy p WHERE p.resource = :resource")
    , @NamedQuery(name = "Policy.findByTreshhold", query = "SELECT p FROM Policy p WHERE p.treshhold = :treshhold")
    , @NamedQuery(name = "Policy.findBySign", query = "SELECT p FROM Policy p WHERE p.sign = :sign")
    , @NamedQuery(name = "Policy.findByEnabled", query = "SELECT p FROM Policy p WHERE p.enabled = :enabled")
    , @NamedQuery(name = "Policy.findByGroups", query = "SELECT p FROM Policy p WHERE p.groups = :groups")})
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "system-uuid")
    @Size(min = 1, max = 100)
    @Column(name = "id_policy")
    private String idPolicy;
    @Size(max = 100)
    @Column(name = "users")
    private String users;
    @Size(max = 100)
    @Column(name = "resource")
    private String resource;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "treshhold")
    private Double treshhold;
    @Size(max = 100)
    @Column(name = "sign")
    private String sign;
    @Column(name = "enabled")
    private Boolean enabled;
    @Size(max = 100)
    @Column(name = "groups")
    private String groups;
    @JoinColumn(name = "id_event", referencedColumnName = "id_event")
    @ManyToOne
    private Event idEvent;
    @JoinColumn(name = "id_meter", referencedColumnName = "id_meters")
    @ManyToOne
    private Meter idMeter;
    @JoinColumn(name = "id_policylist", referencedColumnName = "id_policylist")
    @ManyToOne
    private PolicyList idPolicylist;

    public Policy() {
    }

    public Policy(String idPolicy) {
        this.idPolicy = idPolicy;
    }

    public String getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(String idPolicy) {
        this.idPolicy = idPolicy;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Double getTreshhold() {
        return treshhold;
    }

    public void setTreshhold(Double treshhold) {
        this.treshhold = treshhold;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public Event getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Event idEvent) {
        this.idEvent = idEvent;
    }

    public Meter getIdMeter() {
        return idMeter;
    }

    public void setIdMeter(Meter idMeter) {
        this.idMeter = idMeter;
    }

    public PolicyList getIdPolicylist() {
        return idPolicylist;
    }

    public void setIdPolicylist(PolicyList idPolicylist) {
        this.idPolicylist = idPolicylist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPolicy != null ? idPolicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Policy)) {
            return false;
        }
        Policy other = (Policy) object;
        if ((this.idPolicy == null && other.idPolicy != null) || (this.idPolicy != null && !this.idPolicy.equals(other.idPolicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.Policy[ idPolicy=" + idPolicy + " ]";
    }

}
