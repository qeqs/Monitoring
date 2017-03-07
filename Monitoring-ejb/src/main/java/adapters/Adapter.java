package adapters;

import entities.Measure;
import entities.Meter;
import java.util.Date;
import javax.ejb.Local;

@Local({TestAdapter.class,OpenStackAdapter.class})
public interface Adapter {
    Meter getMeter(String name);//??? 
    Measure getMeasure(Meter meter);//timestamp = current time
    Measure getMeasure(Meter meter,Date timestamp);
    Adapter setUser(String uid);//todo:change it
    
    //todo:get set resource
    //todo:autorefresh meters
}
