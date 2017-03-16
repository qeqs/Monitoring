package scheduler;

import adapters.OpenStackAdapter;
import adapters.SnmpAdapter;
import adapters.TestAdapter;
import controllers.rmi.entities.Profile;
import dao.MetersFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import logic.MeasureController;
import org.quartz.Scheduler;

@Stateless
public class MonitorTemplateBuilder {

    @EJB
    private MetersFacade metersFacade;
    @EJB
    private MeasureController controller;
    
    @EJB
    private OpenStackAdapter restAdapter;
    @EJB
    private TestAdapter testAdapter;
    @EJB
    private SnmpAdapter snmpAdapter;

    MonitorTemplate template;

    public MonitorTemplateBuilder createInstance() {
        template = new MonitorTemplate();
        return MonitorTemplateBuilder.this;
    }

    public MonitorTemplateBuilder setScheduler(Scheduler scheduler) {
        if (template != null) {
            template.setScheduler(scheduler);
        }
        return MonitorTemplateBuilder.this;

    }

    public MonitorTemplateBuilder setProfile(Profile profile) {
        if (template != null) {
            template.setProfile(profile);
        }
        return MonitorTemplateBuilder.this;

    }

    public MonitorTemplateBuilder setExpirationTime(int expirationTime) {
        if (template != null) {
            template.setExpirationTime(expirationTime);
        }
        return MonitorTemplateBuilder.this;

    }

    public MonitorTemplateBuilder setRepeatTime(int repeatTime) {
        if (template != null) {
            template.setRepeatTime(repeatTime);
        }
        return MonitorTemplateBuilder.this;

    }

    public MonitorTemplate build() {
        if (template == null) {
            return null;
        }
        template.setController(controller);
        template.setMetersFacade(metersFacade);
        template.setTestAdapter(testAdapter);
        template.setSnmpAdapter(snmpAdapter);
        template.setRestAdapter(restAdapter);
        MonitorTemplate temp = template;
        template = null;
        return temp;
    }
}
