package scheduler;

import adapters.Adapter;
import adapters.TestAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import entities.Meter;
import entities.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import rest.Controller;
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
        System.out.println("scheduler.MeasuresJob.execute()");
        System.out.println("scheduler.MeasuresJob.execute()  "+ jdm.get("adapter")+" "+ jdm.get("meters"));
        
        System.out.println("EEEEEEEEEEE2 users = " + users + " meters = " + meters + " adapter =" + String.valueOf(adapter != null) + " controller =" + String.valueOf(controller != null));

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
