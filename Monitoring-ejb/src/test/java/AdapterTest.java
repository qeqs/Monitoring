
import adapters.OpenStackAdapter;
import org.junit.Test;

public class AdapterTest {
    @Test
    public void test(){
        OpenStackAdapter adapter = new OpenStackAdapter();
        adapter.init("facebook147125119093958", "yYrTCqrC6B8GCZOE");
        System.out.println(adapter.getMeter("instance").getName());
    }
}
