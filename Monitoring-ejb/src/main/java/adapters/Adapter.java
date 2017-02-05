package adapters;

import entities.Measure;
import entities.Meter;
import java.util.Date;

public interface Adapter {
    Meter getMeter(String name);//??? 
    Measure getMeasure(Meter meter,String uid);//timestamp = current time
    Measure getMeasure(Meter meter,String uid,Date timestamp);
    
}
