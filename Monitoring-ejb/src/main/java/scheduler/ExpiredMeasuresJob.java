package scheduler;

import controllers.rmi.entities.Profile;
import java.util.Date;
import logic.MeasureController;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
public class ExpiredMeasuresJob implements Job {

    private Integer beforeDate;
    private MeasureController measureController;
    private Profile profile;

    @Override
    public void execute(JobExecutionContext context) {

        JobDataMap jdm = context.getMergedJobDataMap();

        try {
            measureController = (MeasureController) jdm.get("controller");
            profile = (Profile) jdm.get("profile");
            beforeDate = (Integer) jdm.get("date");
            if (beforeDate == 0) {
                return;
            }
            Date date = new Date(new Date().getTime() - beforeDate);
            measureController.clearMeasuresByProfile(profile, date);
        } catch (Exception exception) {
            System.out.println("scheduler.ExpiredMeasuresJob.execute() " + exception.getLocalizedMessage());
        }
    }

}
