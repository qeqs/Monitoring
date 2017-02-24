package rmi;

import javax.ejb.Remote;
import controllers.rmi.entities.Vnf;
import javax.ejb.Stateless;
import controllers.rmi.VnfControllerRemote;
import dao.VnfFacade;
import java.util.List;
import javax.ejb.EJB;

@Stateless
@Remote(VnfControllerRemote.class)
public class VnfControllerImpl implements VnfControllerRemote {

    @EJB
    VnfFacade vnfFacade;

    @Override
    public void store(Vnf vnf) {
        if (vnfFacade.find(vnf.getId()).equals(vnf)) {
            vnfFacade.create(vnf);
        } else {
            vnfFacade.edit(vnf);
        }
    }

    @Override
    public List<Vnf> getAllVnf() {
        return vnfFacade.findAll();
    }

}
