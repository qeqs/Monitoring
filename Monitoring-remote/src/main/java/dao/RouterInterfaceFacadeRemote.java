
package dao;

import controllers.rmi.entities.RouterInterface;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface RouterInterfaceFacadeRemote {

    void create(RouterInterface routerInterface);

    void edit(RouterInterface routerInterface);

    void remove(RouterInterface routerInterface);

    RouterInterface find(Object id);

    List<RouterInterface> findAll();

    List<RouterInterface> findRange(int[] range);

    int count();
    
}
