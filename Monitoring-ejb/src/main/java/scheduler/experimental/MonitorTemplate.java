package scheduler.experimental;

import dao.MetersFacade;
import dao.UsersFacade;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import logic.MeasureController;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import scheduler.ExpiredMeasuresJob;
import scheduler.MeasuresJob;

public class MonitorTemplate {

    public final String MAIN_TRIGGER_NAME;
    public final String EXPIRATION_TRIGGER_NAME;
    public final String JOB_NAME;
    public final String EXPIRATION_JOB_NAME;
    public static final String EXPIRATION_JOB_GROUP_NAME = "ExpiredMeasures";
    public static final String JOB_GROUP_NAME = "Measures";
    private static int trigIndex = 0;
    private static int jobIndex = 0;
    private static int expiredIndex = 0;

    {
        MAIN_TRIGGER_NAME = "MainTrigger" + String.valueOf(trigIndex++);
        EXPIRATION_TRIGGER_NAME = "ExpirationTrigger" + String.valueOf(trigIndex);
        JOB_NAME = String.valueOf(jobIndex++);
        EXPIRATION_JOB_NAME = String.valueOf(expiredIndex++);
    }

    private Object vnf;
    private final HashMap<AdapterType, JobDetail> jobsMain = new HashMap<>();
    private final HashMap<AdapterType, Trigger> mainTriggers = new HashMap<>();
    private JobDetail jobExpired;
    private Trigger expirationTrigger;

    private Integer expirationTime = 0;
    private Integer repeatTime = 5;

    @EJB
    private MetersFacade metersFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private MeasureController controller;
    private Scheduler scheduler;

    public Trigger getMainTrigger(AdapterType key) {
        return mainTriggers.get(key);
    }

    public void setMainTrigger(AdapterType key, Trigger mainTrigger) {
        if (mainTriggers.containsKey(key)) {
            this.mainTriggers.remove(key);
        }
        this.mainTriggers.put(key, mainTrigger);
    }

    public Trigger getExpirationTrigger() {
        return expirationTrigger;
    }

    public void setExpirationTrigger(Trigger expirationTrigger) {
        this.expirationTrigger = expirationTrigger;
    }

    public Object getVnf() {
        return vnf;
    }

    public void setVnf(Object vnf) {
        this.vnf = vnf;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start() throws Exception {
        init();
        for (Map.Entry<AdapterType, Trigger> entry : mainTriggers.entrySet()) {
            AdapterType key = entry.getKey();
            Trigger value = entry.getValue();

            scheduler.scheduleJob(jobsMain.get(key), value);
        }
        scheduler.scheduleJob(jobExpired, expirationTrigger);

    }

    public void restart() throws Exception {
        init();
        for (AdapterType type : AdapterType.values()) {
            scheduler.rescheduleJob(scheduler.getTriggersOfJob(JobKey.jobKey(type.name() + JOB_NAME, JOB_GROUP_NAME)).get(0).getKey(), mainTriggers.get(type));
        }
        scheduler.rescheduleJob(scheduler.getTriggersOfJob(JobKey.jobKey(EXPIRATION_JOB_NAME, EXPIRATION_JOB_GROUP_NAME)).get(0).getKey(), expirationTrigger);

    }

    private void init() throws Exception {
        if (scheduler == null) {
            throw new Exception("scheduler was not set");
        }

        if (mainTriggers.isEmpty()) {                       //triggers init
            for (AdapterType type : AdapterType.values()) {
                Trigger mainTrigger = TriggerBuilder.newTrigger().withIdentity(type.name() + MAIN_TRIGGER_NAME)
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .repeatForever()
                                .withIntervalInSeconds(repeatTime))
                        .build();
                mainTriggers.put(type, mainTrigger);
            }
        }
        if (expirationTrigger == null) {
            expirationTrigger = TriggerBuilder.newTrigger().withIdentity(EXPIRATION_TRIGGER_NAME)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .repeatForever()
                            .withIntervalInSeconds(repeatTime))
                    .build();
        }
        if (jobsMain.isEmpty()) {                        //jobs init
            for (AdapterType key : AdapterType.values()) {
                {
                    JobDetail jobMain = JobBuilder.newJob(MeasuresJob.class)
                            .withIdentity(key.name() + JOB_NAME, JOB_GROUP_NAME)
                            .build();
                    jobMain.getJobDataMap().put("adapter", key.getAdapterImpl());
                    jobMain.getJobDataMap().put("users", usersFacade.findAll());
                    jobMain.getJobDataMap().put("meters", metersFacade.findAll());
                    jobMain.getJobDataMap().put("vnf", vnf);
                    jobMain.getJobDataMap().put("controller", controller);

                    jobsMain.put(key, jobMain);
                }

                jobExpired = JobBuilder.newJob(ExpiredMeasuresJob.class)
                        .withIdentity(EXPIRATION_JOB_NAME, EXPIRATION_JOB_GROUP_NAME)
                        .build();
                jobExpired.getJobDataMap().put("controller", controller);
                jobExpired.getJobDataMap().put("date", controller);
            }
        }
    }
}
