package scheduler;

import adapters.Adapter;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import controllers.rmi.entities.User;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import logic.MeasureController;

@PersistJobDataAfterExecution
public class MeasuresJob implements Job {

    private adapters.Adapter adapter;
    private List<Meter> meters;
    private List<User> users;
    private MeasureController controller;
    private Profile profile;

    @Override
    public void execute(JobExecutionContext jec) {

        JobDataMap jdm = jec.getMergedJobDataMap();
        try {
            adapter = (Adapter) jdm.get("adapter");
            meters = (List<Meter>) jdm.get("meters");
            users = (List<User>) jdm.get("users");
            controller = (MeasureController) jdm.get("controller");
            profile = (Profile) jdm.get("profile");

            for (Meter meter : meters) {
                adapter.setProfile(profile);
                controller.storeMeasure(adapter.getMeasure(meter), profile);
            }
        } catch (Exception exception) {
            System.out.println("WARNING! scheduler.MeasuresJob.execute() " + exception.getLocalizedMessage());
        }
    }

}
