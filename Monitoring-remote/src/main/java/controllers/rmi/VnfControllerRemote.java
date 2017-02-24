/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.rmi;

import controllers.rmi.entities.Vnf;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kvakin
 */
@Remote
public interface VnfControllerRemote {

    /**
     *
     * @param vnf
     */
    void store(Vnf vnf);

    /**
     * 
     * @return
     */
    List<Vnf> getAllVnf();
}
