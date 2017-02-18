package scheduler;

import adapters.OpenStackAdapter;
import adapters.TestAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.quartz.JobBuilder;
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
    
    public static final String TRIGGER_NAME = "trigger";
    public static final String REST_JOB_NAME = "Rest";
    public static final String TEST_JOB_NAME = "Test";
    public static final String EXPIRED_JOB_NAME = "Expired";
    public static final String EXPIRED_JOB_GROUP_NAME = "ExpiredMeasures";
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
    private Trigger triggerTemplate;
    private Integer timeBeforeExpired = 1000*60*60*2;  //millisecs

    public Scheduler getScheduler() {
        return scheduler;
    }
    
    public Trigger getTrigger(String jobName) throws SchedulerException {
        return scheduler.getTrigger(new TriggerKey(TRIGGER_NAME+jobName));
    }
    public Trigger getTriggerTemplate(){
        return triggerTemplate;
    }

    @PostConstruct
    public void start() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            
            triggerTemplate = TriggerBuilder.newTrigger()
                    .withIdentity(TRIGGER_NAME)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .repeatForever()
                            .withIntervalInSeconds(5))
                    .build();
            
            Trigger triggerTest =triggerTemplate.getTriggerBuilder()
                    .withIdentity(TRIGGER_NAME+TEST_JOB_NAME)
                    .build();
            Trigger triggerRest = triggerTemplate.getTriggerBuilder()
                    .withIdentity(TRIGGER_NAME+REST_JOB_NAME)
                    .build();
            Trigger triggerExpired = triggerTemplate.getTriggerBuilder()
                    .withIdentity(TRIGGER_NAME+EXPIRED_JOB_NAME)
                    .build();
            

            JobDetail jobRest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity(REST_JOB_NAME, JOB_GROUP_NAME)
                    .build();
            jobRest.getJobDataMap().put("adapter", openStackAdapter);
            jobRest.getJobDataMap().put("users", usersFacade.findAll());
            jobRest.getJobDataMap().put("meters", metersFacade.findAll());
            jobRest.getJobDataMap().put("controller", controller);

            JobDetail jobTest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity(TEST_JOB_NAME, JOB_GROUP_NAME)
                    .build();
            jobTest.getJobDataMap().put("adapter", testAdapter);
            jobTest.getJobDataMap().put("users", usersFacade.findAll());
            jobTest.getJobDataMap().put("meters", metersFacade.findAll());
            jobTest.getJobDataMap().put("controller", controller);

            
            JobDetail jobExpired = JobBuilder.newJob(ExpiredMeasuresJob.class)
                    .withIdentity(EXPIRED_JOB_NAME,EXPIRED_JOB_GROUP_NAME)
                    .build();
            jobExpired.getJobDataMap().put("controller", controller);
            jobExpired.getJobDataMap().put("date", controller);
            
            //scheduler.scheduleJob(jobRest, triggerRest);
            scheduler.scheduleJob(jobTest, triggerTest);
            scheduler.scheduleJob(jobExpired, triggerExpired);
            scheduler.start();

            System.out.println("scheduler.JobScheduler.start()  " + scheduler.isStarted());

        } catch (SchedulerException ex) {
            try {
                System.out.println("scheduler.JobScheduler.start()  " + scheduler.isStarted());
            } catch (SchedulerException ex1) {
                Logger.getLogger(JobScheduler.class.getName()).log(Level.SEVERE,"JobScheduler.start()= false", ex1);
            }
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
