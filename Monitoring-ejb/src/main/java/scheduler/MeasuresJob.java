package scheduler;

import adapters.TestAdapter;
import dao.MetersFacade;
import dao.UsersFacade;
import entities.Meter;
import entities.Users;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import rest.ControllerLocal;

public class MeasuresJob implements Job {

    MetersFacade metersFacade = lookupMetersFacadeBean();

    UsersFacade usersFacade = lookupUsersFacadeBean();

    TestAdapter adapter = lookupTestAdapterBean();
        
    ControllerLocal controller = lookupControllerLocal();
    
//    private adapters.Adapter adapter;
   private List<Meter> meters;
   private List<Users> users;
//    private Controller controller;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("scheduler.MeasuresJob.execute()");

        JobDataMap jdm = jec.getMergedJobDataMap();
        users = usersFacade.findAll();
        meters = metersFacade.findAll();
        System.out.println("scheduler.MeasuresJob.execute() " + jdm.size());

            //        adapter = (Adapter) jdm.get("adapter");
//        meters = (List<Meter>) jdm.get("meters");
//        users = (List<Users>) jdm.get("users");
//        controller = (Controller) jdm.get("controller");
        
        System.out.println("EEEEEEEEEEE2 users = " + users + " meters = " + meters + " adapter =" + String.valueOf(adapter != null) + " controller =" + String.valueOf(controller != null));

        for (Users user : users) {
            for (Meter meter : meters) {
                adapter.setUser(user.getUid());
                controller.storeMeasure(adapter.getMeasure(meter));
                System.out.println("EEEEEEEEEEE");
            }
        }
    }

    private ControllerLocal lookupControllerLocal() {
        try {
            Context c = new InitialContext();
            return (ControllerLocal) c.lookup("java:global/Monitoring-ear/Monitoring-ejb-1.0-SNAPSHOT/Controller");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private TestAdapter lookupTestAdapterBean() {
        try {
            Context c = new InitialContext();
            return (TestAdapter) c.lookup("java:global/Monitoring-ear/Monitoring-ejb-1.0-SNAPSHOT/TestAdapter!adapters.TestAdapter");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UsersFacade lookupUsersFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/Monitoring-ear/Monitoring-ejb-1.0-SNAPSHOT/UsersFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MetersFacade lookupMetersFacadeBean() {
        try {
            Context c = new InitialContext();
            return (MetersFacade) c.lookup("java:global/Monitoring-ear/Monitoring-ejb-1.0-SNAPSHOT/MetersFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
