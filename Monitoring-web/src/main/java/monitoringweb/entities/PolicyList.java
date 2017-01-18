/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kvakin
 */
@Entity
@Table(name = "policy_list")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolicyList.findAll", query = "SELECT p FROM PolicyList p")
    , @NamedQuery(name = "PolicyList.findByIdPolicyList", query = "SELECT p FROM PolicyList p WHERE p.idPolicyList = :idPolicyList")})
public class PolicyList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_policy_list")
    private Integer idPolicyList;
    @JoinColumn(name = "id_profile", referencedColumnName = "id_profile")
    @ManyToOne
    private Profile idProfile;
    @OneToMany(mappedBy = "idPolicyList")
    private List<Policy> policyList;

    public PolicyList() {
    }

    public PolicyList(Integer idPolicyList) {
        this.idPolicyList = idPolicyList;
    }

    public Integer getIdPolicyList() {
        return idPolicyList;
    }

    public void setIdPolicyList(Integer idPolicyList) {
        this.idPolicyList = idPolicyList;
    }

    public Profile getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Profile idProfile) {
        this.idProfile = idProfile;
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
        hash += (idPolicyList != null ? idPolicyList.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolicyList)) {
            return false;
        }
        PolicyList other = (PolicyList) object;
        if ((this.idPolicyList == null && other.idPolicyList != null) || (this.idPolicyList != null && !this.idPolicyList.equals(other.idPolicyList))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.PolicyList[ idPolicyList=" + idPolicyList + " ]";
    }
    
}
