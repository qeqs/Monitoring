package adapters;

import dao.SettingsFacade;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import controllers.rmi.entities.Settings;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Profile profile;
    
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
        return getMeasure(meter,new Timestamp(new Date().getTime()));
    }

    @Override
    public Measure getMeasure(Meter meter, Date timestamp) {
        Random random = new Random(new Date().getTime());
        Measure measure = new Measure();
        measure.setIdMeter(meter);
        measure.setSource("test");
        measure.setResource(String.valueOf(random.nextLong()));
        measure.setTstamp(timestamp);
        measure.setValue(random.nextDouble()%100 + random.nextInt()%50);
        measure.setIdProfile(profile);
        
        
        return measure;
    }

    @Override
    public Adapter setUser(String uid) {
        settings = settingsFacade.findByUid(uid);
        return TestAdapter.this;
    }

    @Override
    public Adapter setProfile(Profile profile) {
        this.profile = profile;
        return TestAdapter.this;
    }

    @Override
    public List<Measure> getMeasureList(Meter meter, Date timestamp) {
        ArrayList<Measure> list = new ArrayList<>();
        for(int i = 0; i<new Random(new Date().getTime()).nextInt()%10;i++){
            list.add(getMeasure(meter, timestamp));
        }
        return list;
    }

}
