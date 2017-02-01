package adapters;

import adapters.entities.Access;
import adapters.entities.Meter;
import adapters.entities.Network;
import adapters.entities.Token;
import adapters.entities.Wrapper;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import org.glassfish.jersey.client.JerseyClientBuilder;



public class OpenStackAdapter implements Adapter{ 
  
    private final String REST_SERVICE_URL ="http://x86.trystack.org:8777";
    private final String KEYSTONE_REST_SERVICE_URL = "http://8.43.86.2:5000/v2.0/";
    private WebTarget webTarget;
    private Client client;
    private Token token;    
    private WebTarget metrics;
    private WebTarget keystone;
    
    public void init(String login,String pass){
        client = JerseyClientBuilder.createClient();
        webTarget = client.target(REST_SERVICE_URL);
        keystone = client.target(KEYSTONE_REST_SERVICE_URL);
        metrics = webTarget.path("meters");
        
        StringBuilder form = new StringBuilder()
                 .append("{\"auth\": {\"passwordCredentials\":{\"username\": \"")
                 .append(login)
                 .append("\",\"password\":  \"")
                 .append(pass)
                 .append("\"}}}");
        
        Wrapper wrapper=keystone.path("tokens")
                .request()
                .post(Entity.entity(form.toString(), MediaType.APPLICATION_JSON_TYPE), Wrapper.class);
        Access access = wrapper.getAccess();
        token = access.getToken();
        
        System.out.println("adapters.OpenStackAdapter.init() - resault: "+access.getToken().getId());
    }
    
    public List<Meter> getMeters(){
        ArrayList<Meter> meterList = new ArrayList<Meter>();
        GenericType<JAXBElement<List<Meter>>> listGenericType = new GenericType<JAXBElement<List<Meter>>>() {};
        Invocation.Builder builder = metrics.request(MediaType.APPLICATION_JSON_TYPE);
        builder.header("X-Auth-Token",token);
        meterList= (ArrayList<Meter>) builder.get(listGenericType).getValue();
        
        return meterList;
    }
    public Meter getMeter(String name){
        return metrics.path(name)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Auth-Token", token)
                .get(Meter.class);
    }
    public Network getNetwork(){
        return client.target("http://8.43.86.2:9696")
                .path("/v2.0/networks")
                .path("721d4378-a212-45fc-8b33-eb0baed69fb6")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Auth-Token", token)
                .get(Network.class);
    }
}
