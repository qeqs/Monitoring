/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controllers.rmi.entities.SnmpSettings;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kvakin
 */
@Remote
public interface SnmpSettingsFacadeRemote {

    void create(SnmpSettings snmpSettings);

    void edit(SnmpSettings snmpSettings);

    void remove(SnmpSettings snmpSettings);

    SnmpSettings find(Object id);

    List<SnmpSettings> findAll();

    List<SnmpSettings> findRange(int[] range);

    int count();
    
}
