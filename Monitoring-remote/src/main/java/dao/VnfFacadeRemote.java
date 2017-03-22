
package dao;

import controllers.rmi.entities.Vnf;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface VnfFacadeRemote {

    void create(Vnf vnf);

    void edit(Vnf vnf);

    void remove(Vnf vnf);

    Vnf find(Object id);

    List<Vnf> findAll();

    List<Vnf> findRange(int[] range);

    int count();
    
}
