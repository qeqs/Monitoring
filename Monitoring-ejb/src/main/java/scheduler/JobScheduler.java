package scheduler;

import adapters.OpenStackAdapter;
import adapters.TestAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import logic.MeasureController;
import org.quartz.TriggerKey;

@Startup
@Singleton
@DependsOn({"MeasureController"})
public class JobScheduler {
    
    public static final String TRIGGER_NAME = "trigger1";
    public static final String REST_JOB_NAME = "Rest";
    public static final String TEST_JOB_NAME = "Test";
    public static final String JOB_GROUP_NAME = "Measures";
    
    @EJB
    private OpenStackAdapter openStackAdapter;
    @EJB
    private TestAdapter testAdapter;
    @EJB
    private MetersFacade metersFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private MeasureController controller;
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }
    
    public Trigger getTrigger() throws SchedulerException {
        return scheduler.getTrigger(new TriggerKey(TRIGGER_NAME));
    }

    @PostConstruct
    public void start() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        try {
            scheduler = schedulerFactory.getScheduler();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TRIGGER_NAME)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .repeatForever()
                            .withIntervalInSeconds(5))
                    .build();

            JobDataMap params = new JobDataMap();
            params.put("adapter", openStackAdapter);
            params.put("users", usersFacade.findAll());
            params.put("meters", metersFacade.findAll());
            params.put("controller", controller);
            JobDetail jobRest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity(REST_JOB_NAME, JOB_GROUP_NAME)
                    .setJobData(new JobDataMap(params))
                    .build();

            JobDetail jobTest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity(TEST_JOB_NAME, JOB_GROUP_NAME)
                    .build();
            jobTest.getJobDataMap().put("adapter", testAdapter);
            jobTest.getJobDataMap().put("users", usersFacade.findAll());
            jobTest.getJobDataMap().put("meters", metersFacade.findAll());
            jobTest.getJobDataMap().put("controller", controller);

            //scheduler.scheduleJob(jobRest, trigger);
            scheduler.scheduleJob(jobTest, trigger);
            scheduler.start();

            System.out.println("scheduler.JobScheduler.start()  " + scheduler.isStarted());

        } catch (SchedulerException ex) {

        }

    }

    @PreDestroy
    public void stop() {
        try {
            if (scheduler == null) {
                return;
            }
            scheduler.shutdown();
        } catch (SchedulerException ex) {
        }
    }

}
