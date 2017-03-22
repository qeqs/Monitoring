package scheduler.job;

import adapters.Adapter;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import dao.MetersFacade;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import logic.MeasureController;
import scheduler.AdapterType;

@PersistJobDataAfterExecution
public class MeasuresJob implements Job {

    private adapters.Adapter adapter;
    private List<Meter> meters;
    private MeasureController controller;
    private Profile profile;
    private AdapterType type;

    @Override
    public void execute(JobExecutionContext jec) {

        JobDataMap jdm = jec.getMergedJobDataMap();
        try {
            type = (AdapterType) jdm.get("type");
            adapter = (Adapter) jdm.get("adapter");
            meters = ((MetersFacade) jdm.get("meters")).findAll();
            controller = (MeasureController) jdm.get("controller");
            profile = (Profile) jdm.get("profile");

            for (Meter meter : meters) {
                adapter.setProfile(profile);
                Measure measure = adapter.getMeasure(meter);
                if (measure != null) {
                    controller.storeMeasure(measure, profile);
                }
//                for (Measure measure : adapter.getMeasureList(meter, new Date())) {
//                    controller.storeMeasure(measure, profile);
//                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR MeasureJob " + type);
            //ex.printStackTrace();
        }

    }

}
