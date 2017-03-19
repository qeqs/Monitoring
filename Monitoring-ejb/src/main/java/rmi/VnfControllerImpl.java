package rmi;

import javax.ejb.Remote;
import controllers.rmi.entities.Vnf;
import javax.ejb.Stateless;
import controllers.rmi.VnfControllerRemote;
import controllers.rmi.entities.Profile;
import controllers.rmi.entities.Settings;
import controllers.rmi.entities.SnmpSettings;
import dao.ProfileFacade;
import dao.VnfFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.quartz.SchedulerException;
import scheduler.SchedulerController;

@Stateless
@Remote(VnfControllerRemote.class)
public class VnfControllerImpl implements VnfControllerRemote {

    @EJB
    VnfFacade vnfFacade;
    @EJB
    ProfileFacade profileFacade;
    @EJB
    SchedulerController schedulerController;

    @Override
    public void store(Vnf vnf, Settings settings, SnmpSettings snmp) {
        if (vnf == null) {
            Logger.getLogger(VnfControllerImpl.class.getName()).log(Level.SEVERE, "Vnf = null");
            return;
        }
        if (vnf.equals(vnfFacade.find(vnf.getId()))) {
            vnfFacade.create(vnf);
        } else {
            vnfFacade.edit(vnf);
        }
        try {
            Profile profile = new Profile();
            profile.setIdVnf(vnf);
            profile.setIdSettings(settings);
            profile.setIdSnmp(snmp);

            if (!profileFacade.isProfileWithThisVnfExists(vnf)) {
                profileFacade.create(profile);
            } else {
                profileFacade.edit(profile);
            }
            schedulerController.createMonitor(profile);

        } catch (SchedulerException ex) {
            Logger.getLogger(VnfControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Vnf> getAllVnf() {
        return vnfFacade.findAll();
    }

}
