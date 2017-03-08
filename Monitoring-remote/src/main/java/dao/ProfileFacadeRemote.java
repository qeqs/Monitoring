package dao;

import controllers.rmi.entities.Profile;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ProfileFacadeRemote {

    void create(Profile profile);

    void edit(Profile profile);

    void remove(Profile profile);

    Profile find(Object id);

    List<Profile> findAll();

    List<Profile> findRange(int[] range);

    int count();
    
}
