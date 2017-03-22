
package dao;

import controllers.rmi.entities.Router;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface RouterFacadeRemote {

    void create(Router router);

    void edit(Router router);

    void remove(Router router);

    Router find(Object id);

    List<Router> findAll();

    List<Router> findRange(int[] range);

    int count();
    
}
