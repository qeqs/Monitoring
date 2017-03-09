package controllers.rmi;

import controllers.rmi.entities.Settings;
import controllers.rmi.entities.SnmpSettings;
import controllers.rmi.entities.Vnf;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface VnfControllerRemote {

    /**
     *
     * @param vnf your schema
     * @param settings openstack auth credentials
     * @param snmp  set this param null if you don't know about physical machines
     */
    void store(Vnf vnf, Settings settings, SnmpSettings snmp);

    /**
     * 
     * @return
     */
    List<Vnf> getAllVnf();
}
