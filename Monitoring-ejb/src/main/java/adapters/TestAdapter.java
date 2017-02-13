package adapters;

import dao.SettingsFacade;
import entities.Measure;
import entities.Meter;
import entities.Settings;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TestAdapter implements Adapter{

    @EJB
    private SettingsFacade settingsFacade;
    private Settings settings;
    
    @Override
    public Meter getMeter(String name) {
        Meter meter = new Meter();
        meter.setName(name);
        meter.setType("cumulative");
        meter.setUnit("units");
        meter.setDescription("fake meter");
        return meter;
    }

    @Override
    public Measure getMeasure(Meter meter) {
        return getMeasure(meter,new Date());
    }

    @Override
    public Measure getMeasure(Meter meter, Date timestamp) {
        Random random = new Random(new Date().getTime());
        Measure measure = new Measure();
        measure.setIdMeasure(String.valueOf(random.nextLong()));
        measure.setIdMeter(meter);
        measure.setResource("test");
        measure.setTstamp(timestamp);
        measure.setValue(random.nextDouble()%100 + random.nextInt()%50);
        measure.setUserId(settings.getUid());
        
        
        return measure;
    }

    @Override
    public Adapter setUser(String uid) {
        settings = settingsFacade.findByUid(uid);
        return TestAdapter.this;
    }

}
