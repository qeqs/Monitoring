package scheduler;

import controllers.rmi.entities.Vnf;
import dao.VnfFacade;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;

@Startup
@Singleton
public class SchedulerController {

    public static final int DEFAULT_REPEAT_TIME = 5;//sec
    public static final int DEFAULT_EXPIRATION_TIME = 24 * 60 * 60 * 1000;//milisec
    private Scheduler scheduler;
    @EJB
    VnfFacade vnfFacade;

    private ArrayList<MonitorTemplate> monitors;

    @PostConstruct
    public void init() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        monitors = new ArrayList<>();

        try {
            scheduler = schedulerFactory.getScheduler();

            List<Vnf> vnfList = vnfFacade.findAll();
            for (Vnf vnf : vnfList) {
                createMonitor(vnf);
            }

        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createMonitor(Vnf vnf) throws SchedulerException {
        createMonitor(vnf, DEFAULT_REPEAT_TIME, DEFAULT_EXPIRATION_TIME);
    }

    public void createMonitor(Vnf vnf, Integer repeatTime, Integer expirationTime) throws SchedulerException {
        if (!monitorExistsForVnf(vnf)) {
            MonitorTemplate monitorTemplate = new MonitorTemplate();
            monitorTemplate.setScheduler(scheduler)
                    .setVnf(vnf)
                    .setExpirationTime(expirationTime)
                    .setRepeatTime(repeatTime)
                    .start();
            monitors.add(monitorTemplate);
        } else {
            find(vnf).setRepeatTime(repeatTime)
                    .setExpirationTime(expirationTime)
                    .restart();
        }
    }

    public void setCronCalendar(String cronString, String calendarName) throws ParseException, SchedulerException {
        CronCalendar calendar = new CronCalendar(cronString);
        scheduler.addCalendar(calendarName, calendar, true, true);
    }

    public void linkCalendar(Vnf vnf, String calendarName) throws SchedulerException {
        
        MonitorTemplate temp = find(vnf);
        TriggerBuilder builder = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(DEFAULT_REPEAT_TIME))
                .modifiedByCalendar(calendarName);
        
        for (AdapterType adapter : AdapterType.values()) {
            temp.setMainTrigger(adapter, builder
                    .withIdentity(temp.getMainTrigger(adapter).getKey())
                    .build());
        }
        temp.setExpirationTrigger(builder
                .withIdentity(temp.getExpirationTrigger().getKey())
                .build());
        
        temp.restart();
    }

    private boolean monitorExistsForVnf(Vnf vnf) {
        boolean res = false;
        for (MonitorTemplate monitor : monitors) {
            if (res = monitor.getVnf().equals(vnf)) {
                return res;
            }
        }
        return res;
    }

    private MonitorTemplate find(Vnf vnf) {
        for (MonitorTemplate monitor : monitors) {
            if (monitor.getVnf().equals(vnf)) {
                return monitor;
            }
        }
        return null;
    }
}
