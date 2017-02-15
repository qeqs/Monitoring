package scheduler;

import adapters.Adapter;
import entities.Meter;
import entities.User;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import logic.MeasureController;

@PersistJobDataAfterExecution
public class MeasuresJob implements Job {

    private adapters.Adapter adapter;
    private List<Meter> meters;
    private List<User> users;
    private MeasureController controller;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        JobDataMap jdm = jec.getMergedJobDataMap();
        try {
            adapter = (Adapter) jdm.get("adapter");
            meters = (List<Meter>) jdm.get("meters");
            users = (List<User>) jdm.get("users");
            controller = (MeasureController) jdm.get("controller");

            for (User user : users) {
                for (Meter meter : meters) {
                    adapter.setUser(user.getUid());
                    controller.storeMeasure(adapter.getMeasure(meter));
                }
            }
        } catch (Exception exception) {
            System.out.println("scheduler.MeasuresJob.execute() " + exception.getLocalizedMessage());
        }
    }

}
