
import adapters.OpenStackAdapter;
import adapters.entities.Meter;
import org.junit.Test;

public class AdapterTest {
    @Test
    public void test(){
        OpenStackAdapter adapter = new OpenStackAdapter();
        adapter.init("facebook147125119093958", "yYrTCqrC6B8GCZOE");
       // Network network = adapter.getNetwork();
        //System.out.println(network.getName()+":"+network.getStatus());
        Meter meter = adapter.getMeter("instance");
        System.out.println(meter.getName());
    }
}
