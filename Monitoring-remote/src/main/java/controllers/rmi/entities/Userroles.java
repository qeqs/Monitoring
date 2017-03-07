package controllers.rmi.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "userroles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userroles.findAll", query = "SELECT u FROM Userroles u")
    , @NamedQuery(name = "Userroles.findByUsername", query = "SELECT u FROM Userroles u WHERE u.userrolesPK.username = :username")
    , @NamedQuery(name = "Userroles.findByRole", query = "SELECT u FROM Userroles u WHERE u.userrolesPK.role = :role")})
public class Userroles implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserrolesPK userrolesPK;

    public Userroles() {
    }

    public Userroles(UserrolesPK userrolesPK) {
        this.userrolesPK = userrolesPK;
    }

    public Userroles(String username, String role) {
        this.userrolesPK = new UserrolesPK(username, role);
    }

    public UserrolesPK getUserrolesPK() {
        return userrolesPK;
    }

    public void setUserrolesPK(UserrolesPK userrolesPK) {
        this.userrolesPK = userrolesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userrolesPK != null ? userrolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userroles)) {
            return false;
        }
        Userroles other = (Userroles) object;
        if ((this.userrolesPK == null && other.userrolesPK != null) || (this.userrolesPK != null && !this.userrolesPK.equals(other.userrolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.rmi.entities.Userroles[ userrolesPK=" + userrolesPK + " ]";
    }

}
