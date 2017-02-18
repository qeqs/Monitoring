package scheduler;

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

    @Override
    public void execute(JobExecutionContext context) {

        JobDataMap jdm = context.getMergedJobDataMap();

        try {            
            measureController = (MeasureController) jdm.get("controller");
            beforeDate = (Integer) jdm.get("date");
            Date date = new Date(new Date().getTime()-beforeDate);
            measureController.clearMeasuresBefore(date);
        } catch (Exception exception) {
            System.out.println("scheduler.ExpiredMeasuresJob.execute() " + exception.getLocalizedMessage());
        }
    }

}
