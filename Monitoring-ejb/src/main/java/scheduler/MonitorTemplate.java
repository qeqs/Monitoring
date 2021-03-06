package scheduler;

import adapters.Adapter;
import adapters.OpenStackAdapter;
import adapters.SnmpAdapter;
import adapters.TestAdapter;
import scheduler.job.MeasuresJob;
import scheduler.job.ExpiredMeasuresJob;
import controllers.rmi.entities.Profile;
import dao.MetersFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logic.MeasureController;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class MonitorTemplate {//todo:думаю надо сделать этот класс как @Entity и в бд его закидывать

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

    private Profile profile;
    private final HashMap<AdapterType, JobDetail> jobsMain = new HashMap<>();
    private final HashMap<AdapterType, Trigger> mainTriggers = new HashMap<>();
    private JobDetail jobExpired;
    private Trigger expirationTrigger;

    private Integer expirationTime = 0;
    private Integer repeatTime = 5;

    private MetersFacade metersFacade;

    private MeasureController controller;
    private Scheduler scheduler;

    private OpenStackAdapter restAdapter;
    private TestAdapter testAdapter;
    private SnmpAdapter snmpAdapter;

    public void setRestAdapter(OpenStackAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public void setTestAdapter(TestAdapter testAdapter) {
        this.testAdapter = testAdapter;
    }

    public void setSnmpAdapter(SnmpAdapter snmpAdapter) {
        this.snmpAdapter = snmpAdapter;
    }

    public void setMetersFacade(MetersFacade metersFacade) {
        this.metersFacade = metersFacade;
    }

    public void setController(MeasureController controller) {
        this.controller = controller;
    }

    public Profile getProfile() {
        return profile;
    }

    public MonitorTemplate setProfile(Profile profile) {
        this.profile = profile;
        return MonitorTemplate.this;
    }

    public Trigger getMainTrigger(AdapterType key) {
        return mainTriggers.get(key);
    }

    public MonitorTemplate setMainTrigger(AdapterType key, Trigger mainTrigger) {
        if (mainTriggers.containsKey(key)) {
            this.mainTriggers.remove(key);
        }
        this.mainTriggers.put(key, mainTrigger);
        return MonitorTemplate.this;
    }

    public Trigger getExpirationTrigger() {
        return expirationTrigger;
    }

    public MonitorTemplate setExpirationTrigger(Trigger expirationTrigger) {
        this.expirationTrigger = expirationTrigger;
        return MonitorTemplate.this;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public MonitorTemplate setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
        return MonitorTemplate.this;
    }

    public Integer getRepeatTime() {
        return repeatTime;
    }

    public MonitorTemplate setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
        return MonitorTemplate.this;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public MonitorTemplate setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return MonitorTemplate.this;
    }

    public void start() throws SchedulerException {
        init();
        for (Map.Entry<AdapterType, Trigger> entry : mainTriggers.entrySet()) {
            AdapterType key = entry.getKey();
            Trigger value = entry.getValue();

            scheduler.scheduleJob(jobsMain.get(key), value);
        }
        scheduler.scheduleJob(jobExpired, expirationTrigger);
    }

    public void restart() throws SchedulerException {
        init();
        for (AdapterType type : AdapterType.values()) {
            scheduler.rescheduleJob(scheduler.getTriggersOfJob(JobKey.jobKey(type.name() + JOB_NAME, JOB_GROUP_NAME)).get(0).getKey(), mainTriggers.get(type));
        }
        scheduler.rescheduleJob(scheduler.getTriggersOfJob(JobKey.jobKey(EXPIRATION_JOB_NAME, EXPIRATION_JOB_GROUP_NAME)).get(0).getKey(), expirationTrigger);
    }

    public void restart(List<Profile> profiles) throws SchedulerException {
        String id = this.profile.getIdProfile();
        for (Profile p : profiles) {
            if (p.getIdProfile().equals(id)) {
                profile = p;
            }
        }
        jobsMain.clear();
        restart();
    }

    public void clear() throws SchedulerException {
        for (Map.Entry<AdapterType, Trigger> entry : mainTriggers.entrySet()) {
            scheduler.unscheduleJob(entry.getValue().getKey());
        }

        scheduler.unscheduleJob(expirationTrigger.getKey());
    }

    private void init() throws SchedulerException {
        if (scheduler == null) {
            throw new SchedulerException("scheduler was not set");
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
                    jobMain.getJobDataMap().put("adapter", getAdapterImpl(key));
                    jobMain.getJobDataMap().put("meters", metersFacade);
                    jobMain.getJobDataMap().put("profile", profile);
                    jobMain.getJobDataMap().put("controller", controller);
                    jobMain.getJobDataMap().put("type", key);

                    jobsMain.put(key, jobMain);
                }

                jobExpired = JobBuilder.newJob(ExpiredMeasuresJob.class)
                        .withIdentity(EXPIRATION_JOB_NAME, EXPIRATION_JOB_GROUP_NAME)
                        .build();
                jobExpired.getJobDataMap().put("controller", controller);
                jobExpired.getJobDataMap().put("date", expirationTime);
                jobExpired.getJobDataMap().put("profile", profile);
            }
        }
    }

    private Adapter getAdapterImpl(AdapterType type) {

        Adapter adapter = null;
        switch (type) {
//            case Rest:
//                adapter = restAdapter;
//                break;
            case Snmp:
                adapter = snmpAdapter;
                break;
            case Test:
                adapter = testAdapter;
                break;
        }
        return adapter;
    }
}
