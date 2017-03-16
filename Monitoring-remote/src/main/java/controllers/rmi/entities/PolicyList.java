package controllers.rmi.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "policy_list")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolicyList.findAll", query = "SELECT p FROM PolicyList p")
    , @NamedQuery(name = "PolicyList.findByIdPolicylist", query = "SELECT p FROM PolicyList p WHERE p.idPolicylist = :idPolicylist")
    , @NamedQuery(name = "PolicyList.findByEnabled", query = "SELECT p FROM PolicyList p WHERE p.enabled = :enabled")
    , @NamedQuery(name = "PolicyList.findByName", query = "SELECT p FROM PolicyList p WHERE p.name = :name")})
public class PolicyList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "system-uuid")
    @Size(min = 1, max = 100)
    @Column(name = "id_policylist")
    private String idPolicylist;
    @Column(name = "enabled")
    private Boolean enabled;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "idPolicyList")
    private List<Profile> profileList;
    @OneToMany(mappedBy = "idPolicylist")
    private List<Policy> policyList;

    public PolicyList() {
    }

    public PolicyList(String idPolicylist) {
        this.idPolicylist = idPolicylist;
    }

    public String getIdPolicylist() {
        return idPolicylist;
    }

    public void setIdPolicylist(String idPolicylist) {
        this.idPolicylist = idPolicylist;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    @XmlTransient
    public List<Policy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPolicylist != null ? idPolicylist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolicyList)) {
            return false;
        }
        PolicyList other = (PolicyList) object;
        if ((this.idPolicylist == null && other.idPolicylist != null) || (this.idPolicylist != null && !this.idPolicylist.equals(other.idPolicylist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.PolicyList[ idPolicylist=" + idPolicylist + " ]";
    }

}
