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
import org.quartz.CronExpression;
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
                System.out.println("scheduler.SchedulerController.init() creating Monitor for profile " + profile.toString());
                createMonitor(profile);
            }
            scheduler.start();

        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void restartAll() throws SchedulerException{
        for (MonitorTemplate monitor : monitors) {
            monitor.restart(profileFacade.findAll());            
        }
    }
    public void delete(Profile profile) throws SchedulerException{
        MonitorTemplate temp = find(profile);
        monitors.remove(temp);        
        temp.clear();        
    }
    /**
     * создает и запускает monitor для данного профиля, если monitor для такого
     * профиля уже есть, то обновляет профиль в мониторе и перезапускает со значениями по умолчанию
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
     * @param expirationTime время в милисикундах по истечению которого метрики
     * удаляются (0 если хранить вечно)
     * @throws SchedulerException
     */
    public void createMonitor(Profile profile, Integer repeatTime, Integer expirationTime) throws SchedulerException {
        MonitorTemplate monitorTemplate;
        if (!monitorExistsForVnf(profile.getIdProfile())) {

            monitorTemplate = monitorTemplateBuilder.createInstance()
                    .setScheduler(scheduler)
                    .setProfile(profile)
                    .setExpirationTime(expirationTime)
                    .setRepeatTime(repeatTime)
                    .build();
            monitorTemplate.start();
            monitors.add(monitorTemplate);
        } else {
            find(profile.getIdProfile()).setProfile(profile)
                    .setRepeatTime(repeatTime)
                    .setExpirationTime(expirationTime)
                    .restart();
        }
    }

    /**
     * создает новое расписание в планировщике (нужно еще связать с профилем)
     *
     * @param cronString собственно расписание, типа как в линуксе есть cron
     * календарь
     * @see
     * http://www.quartz-scheduler.org/api/2.2.1/org/quartz/CronExpression.html
     * @param calendarName просто любое имя, главное чтобы не совпадало с уже
     * имеющимся в планировщике иначе плюнет эксепшн
     * @throws ParseException
     * @throws SchedulerException
     */
    public void setCronCalendar(String cronString, String calendarName) throws ParseException, SchedulerException {
        setCronCalendar(new CronExpression(cronString), calendarName);
    }

    /**
     * создает новое расписание в планировщике (нужно еще связать с профилем)
     *
     * @param cronString у этого класса юзер френдли интерфейс
     * @see
     * http://www.quartz-scheduler.org/api/2.2.1/org/quartz/CronExpression.html
     * @param calendarName просто любое имя, главное чтобы не совпадало с уже
     * имеющимся в планировщике иначе плюнет эксепшн
     * @throws ParseException
     * @throws SchedulerException
     */
    public void setCronCalendar(CronExpression cronString, String calendarName) throws ParseException, SchedulerException {
        CronCalendar calendar = new CronCalendar(null);
        calendar.setCronExpression(cronString);
        scheduler.addCalendar(calendarName, calendar, true, true);
    }

    /**
     * привязывает профиль к календарю, можно привязать много профилей к одному
     * календарю
     *
     * @param profile
     * @param calendarName
     * @throws SchedulerException
     */
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

    public boolean monitorExistsForVnf(String profile) {
        return find(profile) != null;
    }

    public MonitorTemplate find(Profile profile) {
        for (MonitorTemplate monitor : monitors) {
            if (monitor.getProfile().equals(profile)) {
                return monitor;
            }
        }
        return null;
    }

    public MonitorTemplate find(String profileId) {
        for (MonitorTemplate monitor : monitors) {
            if (monitor.getProfile().getIdProfile().equals(profileId)) {
                return monitor;
            }
        }
        return null;
    }
}
