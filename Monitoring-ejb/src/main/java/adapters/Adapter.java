package adapters;

import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.util.Date;
import javax.ejb.Local;

@Local({TestAdapter.class,OpenStackAdapter.class})
public interface Adapter {
    controllers.rmi.entities.Meter getMeter(String name);//??? 
    controllers.rmi.entities.Measure getMeasure(controllers.rmi.entities.Meter meter);//timestamp = current time
    controllers.rmi.entities.Measure getMeasure(Meter meter,Date timestamp);
    @Deprecated
    Adapter setUser(String uid);
    Adapter setProfile(Profile profile);
    //todo: health check
    //todo: check snmp with vm
}
