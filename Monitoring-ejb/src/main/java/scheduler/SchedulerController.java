package scheduler;

import controllers.rmi.entities.Profile;
import dao.ProfileFacade;
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
    private ProfileFacade profileFacade;
    @EJB
    private MonitorTemplateBuilder monitorTemplateBuilder;

    private ArrayList<MonitorTemplate> monitors;

    @PostConstruct
    public void init() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        monitors = new ArrayList<>();

        try {
            scheduler = schedulerFactory.getScheduler();

            List<Profile> profileList = profileFacade.findAll();
            for (Profile profile : profileList) {
                createMonitor(profile);
            }

        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * создает и запускает monitor для данного профиля, 
     * если monitor для такого профиля уже есть, 
     * то перезапускает со значениями по умолчанию
     * 
     * @param profile 
     * @throws SchedulerException
     */
    public void createMonitor(Profile profile) throws SchedulerException {
        createMonitor(profile, DEFAULT_REPEAT_TIME, DEFAULT_EXPIRATION_TIME);
    }

    /**
     *
     * @param profile 
     * @param repeatTime время в секундах между собором метрик
     * @param expirationTime время в милисикундах по истечению которого метрики удаляются (0 если хранить вечно)
     * @throws SchedulerException
     */
    public void createMonitor(Profile profile, Integer repeatTime, Integer expirationTime) throws SchedulerException {
        if (!monitorExistsForVnf(profile)) {
            MonitorTemplate monitorTemplate = monitorTemplateBuilder.createInstance()
                    .setScheduler(scheduler)
                    .setProfile(profile)
                    .setExpirationTime(expirationTime)
                    .setRepeatTime(repeatTime)
                    .build();
            
            monitorTemplate.start();
            monitors.add(monitorTemplate);
        } else {
            find(profile).setRepeatTime(repeatTime)
                    .setExpirationTime(expirationTime)
                    .restart();
        }
    }

    public void setCronCalendar(String cronString, String calendarName) throws ParseException, SchedulerException {
        CronCalendar calendar = new CronCalendar(cronString);
        scheduler.addCalendar(calendarName, calendar, true, true);
    }

    public void linkCalendar(Profile profile, String calendarName) throws SchedulerException {
        
        MonitorTemplate temp = find(profile);
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

    public boolean monitorExistsForVnf(Profile profile) {
        boolean res = false;
        for (MonitorTemplate monitor : monitors) {
            if (res = monitor.getProfile().equals(profile)) {
                return res;
            }
        }
        return res;
    }

    public MonitorTemplate find(Profile profile) {
        for (MonitorTemplate monitor : monitors) {
            if (monitor.getProfile().equals(profile)) {
                return monitor;
            }
        }
        return null;
    }
}
