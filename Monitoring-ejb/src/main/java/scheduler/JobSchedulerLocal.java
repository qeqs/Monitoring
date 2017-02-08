
package scheduler;

import javax.ejb.Local;

@Local(JobScheduler.class)
public interface JobSchedulerLocal {
    void start();
}
