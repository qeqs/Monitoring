package scheduler.job;

import adapters.Adapter;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.util.Date;
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
            adapter = (Adapter) jdm.get("adapter");
            meters = (List<Meter>) jdm.get("meters");
            controller = (MeasureController) jdm.get("controller");
            profile = (Profile) jdm.get("profile");
            type = (AdapterType) jdm.get("type");
            boolean isSnmp = type == AdapterType.Snmp;

            for (Meter meter : meters) {
                adapter.setProfile(profile);
                if (isSnmp) {
                    controller.storeMeasure(adapter.getMeasure(meter), profile);
                }
                for (Measure measure : adapter.getMeasureList(meter, new Date())) {
                    controller.storeMeasure(measure, profile);
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR MeasureJob "+ type);
            ex.printStackTrace();
        }

    }

}
