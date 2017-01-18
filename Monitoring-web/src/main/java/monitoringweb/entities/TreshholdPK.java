/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kvakin
 */
@Embeddable
public class TreshholdPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_m_type")
    private int idMType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_policy")
    private int idPolicy;

    public TreshholdPK() {
    }

    public TreshholdPK(int idMType, int idPolicy) {
        this.idMType = idMType;
        this.idPolicy = idPolicy;
    }

    public int getIdMType() {
        return idMType;
    }

    public void setIdMType(int idMType) {
        this.idMType = idMType;
    }

    public int getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(int idPolicy) {
        this.idPolicy = idPolicy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMType;
        hash += (int) idPolicy;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TreshholdPK)) {
            return false;
        }
        TreshholdPK other = (TreshholdPK) object;
        if (this.idMType != other.idMType) {
            return false;
        }
        if (this.idPolicy != other.idPolicy) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualizationapp.entities.TreshholdPK[ idMType=" + idMType + ", idPolicy=" + idPolicy + " ]";
    }
    
}
