
package dao;

import controllers.rmi.entities.Subnet;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SubnetFacadeRemote {

    void create(Subnet subnet);

    void edit(Subnet subnet);

    void remove(Subnet subnet);

    Subnet find(Object id);

    List<Subnet> findAll();

    List<Subnet> findRange(int[] range);

    int count();
    
}
