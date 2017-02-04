package scheduler;

import adapters.OpenStackAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
import rest.Controller;

@Singleton
public class JobScheduler {

    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;
    @EJB
    OpenStackAdapter restAdapter;
    @EJB
    private MetersFacade metersFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private Controller controller;

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
            JobDetail job = JobBuilder.newJob(RestMeasuresJob.class)
                    .withIdentity("Measures", "Rest")
                    .setJobData(new JobDataMap(params))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(JobScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @PreDestroy
    public void stop() throws SchedulerException{
        scheduler.clear();
    }
}
