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

@Startup
@Singleton
@DependsOn({"MeasureController"})
public class JobScheduler {

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
    Scheduler scheduler;

    @PostConstruct
    public void start() {
        System.out.println("AAAAAAAAA");
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        try {
            scheduler = schedulerFactory.getScheduler();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1")
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
                    .withIdentity("Rest", "Measures")
                    .setJobData(new JobDataMap(params))
                    .build();

            JobDetail jobTest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity("Test", "Measures")
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
            if(scheduler==null)return;
            scheduler.shutdown();
        } catch (SchedulerException ex) {
        }
    }

}
