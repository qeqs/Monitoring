package adapters;

import controllers.rmi.entities.Meter;
import java.util.Date;
import javax.ejb.Local;

@Local({TestAdapter.class,OpenStackAdapter.class})
public interface Adapter {
    controllers.rmi.entities.Meter getMeter(String name);//??? 
    controllers.rmi.entities.Measure getMeasure(controllers.rmi.entities.Meter meter);//timestamp = current time
    controllers.rmi.entities.Measure getMeasure(Meter meter,Date timestamp);
    Adapter setUser(String uid);//todo:change it
    
    //todo:get set resource
    //todo:autorefresh meters
}
