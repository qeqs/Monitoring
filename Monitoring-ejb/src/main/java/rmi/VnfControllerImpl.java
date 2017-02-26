package rmi;

import javax.ejb.Remote;
import controllers.rmi.entities.Vnf;
import javax.ejb.Stateless;
import controllers.rmi.VnfControllerRemote;
import dao.VnfFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.quartz.SchedulerException;
import scheduler.experimental.SchedulerController;

@Stateless
@Remote(VnfControllerRemote.class)
public class VnfControllerImpl implements VnfControllerRemote {

    @EJB
    VnfFacade vnfFacade;
    @EJB
    SchedulerController schedulerController;

    @Override
    public void store(Vnf vnf) {
        if (vnfFacade.find(vnf.getId()).equals(vnf)) {
            vnfFacade.create(vnf);
        } else {
            vnfFacade.edit(vnf);
        }
        try {
            schedulerController.createMonitor(vnf);
        } catch (SchedulerException ex) {
            Logger.getLogger(VnfControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Vnf> getAllVnf() {
        return vnfFacade.findAll();
    }

}
