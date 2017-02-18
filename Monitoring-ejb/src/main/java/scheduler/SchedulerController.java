package scheduler;

import java.text.ParseException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.CronCalendar;
import static scheduler.JobScheduler.TRIGGER_NAME;

@Stateless
public class SchedulerController {

    @EJB
    JobScheduler scheduler;

    public void setMainTriggerRepeatInterval(int seconds, String cronString, String calendarName) throws SchedulerException, ParseException {
        CronCalendar calendar = new CronCalendar(cronString);
        scheduler.getScheduler().addCalendar(calendarName, calendar, true, true);
        setMainTriggerRepeatInterval(0, calendarName);
    }

    public void setMainTriggerRepeatInterval(int seconds) throws SchedulerException {
        setMainTriggerRepeatInterval(seconds, null);
    }

    private void setTrigger(String triggerName, int seconds, String jobName, String groupName, String calendarName) throws SchedulerException {
        scheduler.getScheduler().rescheduleJob(new TriggerKey(TRIGGER_NAME + jobName), TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(seconds))
                .forJob(jobName, groupName)
                .modifiedByCalendar(calendarName)
                .build());
    }

    private void setMainTriggerRepeatInterval(int seconds, String calendarName) throws SchedulerException {
        setTrigger(TRIGGER_NAME, seconds, JobScheduler.TEST_JOB_NAME, JobScheduler.JOB_GROUP_NAME, calendarName);
        setTrigger(TRIGGER_NAME, seconds, JobScheduler.REST_JOB_NAME, JobScheduler.JOB_GROUP_NAME, calendarName);
        setTrigger(TRIGGER_NAME, seconds, JobScheduler.EXPIRED_JOB_NAME, JobScheduler.EXPIRED_JOB_GROUP_NAME, calendarName);
    }

    public void stopMainTrigger() throws SchedulerException {
        scheduler.getScheduler().pauseTrigger(TriggerKey.triggerKey(TRIGGER_NAME));
    }

    public void startMainTrigger() throws SchedulerException {
        scheduler.getScheduler().resumeTrigger(TriggerKey.triggerKey(TRIGGER_NAME));
    }

    public void setMeasuresExpirationTime(int seconds) throws SchedulerException {
        scheduler.setTimeBeforeExpired(seconds);
        setTrigger(TRIGGER_NAME, seconds, JobScheduler.EXPIRED_JOB_NAME, JobScheduler.EXPIRED_JOB_GROUP_NAME, null);
    }

}
