package scheduler;

import dao.MetersFacade;
import entities.Meter;
import java.util.List;
import javax.ejb.EJB;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RestMeasuresJob implements Job{

    @EJB
    private MetersFacade metersFacade;
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        List<Meter> meters = metersFacade.findAll();
        
    }
    
}
