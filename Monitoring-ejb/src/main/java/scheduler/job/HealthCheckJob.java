package scheduler.job;

import controllers.rmi.entities.Profile;
import logic.EventController;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HealthCheckJob implements Job {

    EventController eventController;
    Profile profile;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        eventController = (EventController) context.get("controller");
        profile = (Profile) context.get("profile");
        
        //todo: 
    }

}
