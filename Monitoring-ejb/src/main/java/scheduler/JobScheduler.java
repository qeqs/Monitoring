package scheduler;

import adapters.OpenStackAdapter;
import adapters.TestAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
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
import rest.ControllerLocal;

@Singleton
public class JobScheduler implements JobSchedulerLocal{


    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;
    OpenStackAdapter restAdapter;
    @EJB
    private MetersFacade metersFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private ControllerLocal controller;
    
    
    private TestAdapter testAdapter;

    @PostConstruct
    public void start() {
        try {
            scheduler = schedulerFactory.getScheduler();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .repeatForever()
                            .withIntervalInSeconds(2))
                    .build();
            
            Map<String,Object> params = new HashMap<>();
            params.put("adapter", restAdapter);
            params.put("users", usersFacade.findAll());
            params.put("meters", metersFacade.findAll());
            params.put("controller", controller);
            JobDetail jobRest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity("Rest", "Measures")
                    .setJobData(new JobDataMap(params))
                    .build();
            
            params = new HashMap<>();
            params.put("adapter", testAdapter);
            params.put("users", usersFacade.findAll());
            params.put("meters", metersFacade.findAll());
            params.put("controller", controller);
            JobDetail jobTest = JobBuilder.newJob(MeasuresJob.class)
                    .withIdentity("Test", "Measures")
                    .setJobData(new JobDataMap(params))
                    .build();

            //scheduler.scheduleJob(jobRest, trigger);
            scheduler.scheduleJob(jobTest,trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(JobScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
