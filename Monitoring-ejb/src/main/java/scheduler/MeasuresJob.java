package scheduler;

import adapters.Adapter;
import entities.Meter;
import entities.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import rest.ControllerLocal;

@PersistJobDataAfterExecution
public class MeasuresJob implements Job {

    
   private adapters.Adapter adapter;
   private List<Meter> meters;
   private List<Users> users;
   private ControllerLocal controller;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("scheduler.MeasuresJob.execute()");

        JobDataMap jdm = jec.getMergedJobDataMap();
        ArrayList<Object> objects = new ArrayList<>();
        System.out.println("scheduler.MeasuresJob.execute() " + jdm.size());
        for (Map.Entry<String, Object> entry : jdm.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("- "+key+" - "+value);
            objects.add(value);
            
            
        }
        try{
        adapter = (Adapter) jdm.get("adapter");
        meters = (List<Meter>) jdm.get("meters");
        users = (List<Users>) jdm.get("users");
        controller = (ControllerLocal) jdm.get("controller");
        
        for (Users user : users) {
            for (Meter meter : meters) {
                adapter.setUser(user.getUid());
                controller.storeMeasure(adapter.getMeasure(meter));
                System.out.println("EEEEEEEEEEE");
            }
        }
        }
        catch(Exception exception){
            System.out.println("scheduler.MeasuresJob.execute() "+exception.getLocalizedMessage());
        }
    }


}
