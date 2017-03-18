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
@Table(name = "settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s")
    , @NamedQuery(name = "Settings.findByIdSettings", query = "SELECT s FROM Settings s WHERE s.idSettings = :idSettings")
    , @NamedQuery(name = "Settings.findByKeystoneEndpoint", query = "SELECT s FROM Settings s WHERE s.keystoneEndpoint = :keystoneEndpoint")
    , @NamedQuery(name = "Settings.findByCeliometerEndpoint", query = "SELECT s FROM Settings s WHERE s.celiometerEndpoint = :celiometerEndpoint")
    , @NamedQuery(name = "Settings.findByTenantName", query = "SELECT s FROM Settings s WHERE s.tenantName = :tenantName")
    , @NamedQuery(name = "Settings.findByOsUsername", query = "SELECT s FROM Settings s WHERE s.osUsername = :osUsername")
    , @NamedQuery(name = "Settings.findByOsPassword", query = "SELECT s FROM Settings s WHERE s.osPassword = :osPassword")})
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "system-uuid")
    @Size(min = 1, max = 100)
    @Column(name = "id_settings")
    private String idSettings;
    @Size(max = 100)
    @Column(name = "keystone_endpoint")
    private String keystoneEndpoint;
    @Size(max = 100)
    @Column(name = "celiometer_endpoint")
    private String celiometerEndpoint;
    @Size(max = 100)
    @Column(name = "tenant_name")
    private String tenantName;
    @Size(max = 100)
    @Column(name = "os_username")
    private String osUsername;
    @Size(max = 100)
    @Column(name = "os_password")
    private String osPassword;
    @OneToMany(mappedBy = "idSettings")
    private List<Profile> profileList;

    public Settings() {
    }

    public Settings(String idSettings) {
        this.idSettings = idSettings;
    }

    public String getIdSettings() {
        return idSettings;
    }

    public void setIdSettings(String idSettings) {
        this.idSettings = idSettings;
    }

    public String getKeystoneEndpoint() {
        return keystoneEndpoint;
    }

    public void setKeystoneEndpoint(String keystoneEndpoint) {
        this.keystoneEndpoint = keystoneEndpoint;
    }

    public String getCeliometerEndpoint() {
        return celiometerEndpoint;
    }

    public void setCeliometerEndpoint(String celiometerEndpoint) {
        this.celiometerEndpoint = celiometerEndpoint;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOsUsername() {
        return osUsername;
    }

    public void setOsUsername(String osUsername) {
        this.osUsername = osUsername;
    }

    public String getOsPassword() {
        return osPassword;
    }

    public void setOsPassword(String osPassword) {
        this.osPassword = osPassword;
    }

    @XmlTransient
    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSettings != null ? idSettings.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.idSettings == null && other.idSettings != null) || (this.idSettings != null && !this.idSettings.equals(other.idSettings))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.Settings[ idSettings=" + idSettings + " ]";
    }

}
