package scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@Stateful
public class JobScheduler {

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;

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
            JobDetail job = JobBuilder.newJob(RestMeasuresJob.class)
                    .withIdentity("Measures", "Rest")
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(JobScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
