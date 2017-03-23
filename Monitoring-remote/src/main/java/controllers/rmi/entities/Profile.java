package controllers.rmi.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profile.findAll", query = "SELECT p FROM Profile p")
    , @NamedQuery(name = "Profile.findByIdProfile", query = "SELECT p FROM Profile p WHERE p.idProfile = :idProfile")
    , @NamedQuery(name = "Profile.findByName", query = "SELECT p FROM Profile p WHERE p.name = :name")
    , @NamedQuery(name = "Profile.findByVnf", query = "SELECT p FROM Profile p WHERE p.idVnf = :vnf")})
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "id_profile")
    @GeneratedValue(generator = "system-uuid")
    private String idProfile;
    @Size(max = 200)
    @Column(name = "name")
    private String name;
    @JoinTable(name = "users_profiles", joinColumns = {
        @JoinColumn(name = "id_profile", referencedColumnName = "id_profile")}, inverseJoinColumns = {
        @JoinColumn(name = "username", referencedColumnName = "username")})
    @ManyToMany
    private List<User> userList;
    @JoinColumn(name = "id_policy_list", referencedColumnName = "id_policylist")
    @ManyToOne
    private PolicyList idPolicyList;
    @JoinColumn(name = "id_settings", referencedColumnName = "id_settings")
    @ManyToOne
    private Settings idSettings;
    @JoinColumn(name = "id_snmp", referencedColumnName = "id_snmp")
    @ManyToOne
    private SnmpSettings idSnmp;
    @JoinColumn(name = "id_vnf", referencedColumnName = "id")
    @ManyToOne
    private Vnf idVnf;
    @OneToMany(mappedBy = "idProfile", cascade = CascadeType.PERSIST)
    private List<Measure> measureList;

    public Profile() {
    }

    public Profile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public PolicyList getIdPolicyList() {
        return idPolicyList;
    }

    public void setIdPolicyList(PolicyList idPolicyList) {
        this.idPolicyList = idPolicyList;
    }

    public Settings getIdSettings() {
        return idSettings;
    }

    public void setIdSettings(Settings idSettings) {
        this.idSettings = idSettings;
    }

    public SnmpSettings getIdSnmp() {
        return idSnmp;
    }

    public void setIdSnmp(SnmpSettings idSnmp) {
        this.idSnmp = idSnmp;
    }

    public Vnf getIdVnf() {
        return idVnf;
    }

    public void setIdVnf(Vnf idVnf) {
        this.idVnf = idVnf;
    }

    @XmlTransient
    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfile != null ? idProfile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.idProfile == null && other.idProfile != null) || (this.idProfile != null && !this.idProfile.equals(other.idProfile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.Profile[ idProfile=" + idProfile + " ]";
    }

}
