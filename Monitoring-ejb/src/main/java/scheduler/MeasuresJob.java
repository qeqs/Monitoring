package scheduler;

import adapters.Adapter;
import entities.Meter;
import entities.Users;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import rest.Controller;

public class MeasuresJob implements Job{

    
    private adapters.Adapter adapter;
    private List<Meter> meters;
    private List<Users> users;
    private Controller controller;
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        adapter = (Adapter) jec.get("adapter");
        meters =  (List<Meter>) jec.get("meters");
        users =  (List<Users>) jec.get("users");
        controller = (Controller) jec.get("controller");
        
        
        for (Users user : users) {
            for (Meter meter : meters) {
                adapter.setUser(user.getUid());
                controller.storeMeasure(adapter.getMeasure(meter));
            }
        }
    }
    
}
