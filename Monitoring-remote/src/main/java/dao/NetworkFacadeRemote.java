
package dao;

import controllers.rmi.entities.Network;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface NetworkFacadeRemote {

    void create(Network network);

    void edit(Network network);

    void remove(Network network);

    Network find(Object id);

    List<Network> findAll();

    List<Network> findRange(int[] range);

    int count();
    
}
