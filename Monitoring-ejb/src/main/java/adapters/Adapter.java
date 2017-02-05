package adapters;

import entities.Measure;
import entities.Meter;
import java.util.Date;

public interface Adapter {
    Meter getMeter(String name);//??? 
<<<<<<< HEAD
    Measure getMeasure(Meter meter,String uid);//timestamp = current time
    Measure getMeasure(Meter meter,String uid,Date timestamp);
    
=======
    Measure getMeasure(Meter meter);//timestamp = current time
    Measure getMeasure(Meter meter,Date timestamp);
    void setUser(String uid);
>>>>>>> 07237b412a26df4fd2d1bc9fbca716d8e13a54a1
}
