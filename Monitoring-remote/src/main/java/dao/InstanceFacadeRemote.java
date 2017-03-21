
package dao;

import controllers.rmi.entities.Instance;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface InstanceFacadeRemote {

    void create(Instance instance);

    void edit(Instance instance);

    void remove(Instance instance);

    Instance find(Object id);

    List<Instance> findAll();

    List<Instance> findRange(int[] range);

    int count();
    
}
