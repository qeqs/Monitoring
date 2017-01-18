/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringweb.beans;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import monitoringweb.entities.Profile;
import monitoringweb.dao.ProfileFacade;

/**
 *
 * @author kvakin
 */
@ManagedBean(eager = true)
@SessionScoped
public class ProfilesBean {

    @EJB ProfileFacade profileFacade; 
    private Profile chosenProfile;

    public Profile getChosenProfile() {
        return chosenProfile;
    }

    public void setChosenProfile(Profile chosenProfile) {
        this.chosenProfile = chosenProfile;
    }

    public List<Profile> getProfiles(){
        return profileFacade.findAll();
    }
    public void createProfile(){
        
    }
}
