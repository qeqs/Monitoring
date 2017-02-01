package adapters;

import adapters.entities.Access;
import adapters.entities.Meter;
import adapters.entities.Sample;
import adapters.entities.Token;
import adapters.entities.Wrapper;
import dao.SettingsFacade;
import entities.Measure;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import org.glassfish.jersey.client.JerseyClientBuilder;

@Stateless
public class OpenStackAdapter implements Adapter {

    private final String REST_SERVICE_URL = "http://x86.trystack.org:8777";
    private final String KEYSTONE_REST_SERVICE_URL = "http://8.43.86.2:5000/v2.0/";
    
    @EJB private SettingsFacade settingsFacade;
    private WebTarget webTarget;
    private Client client;
    private Token token;
    private WebTarget metrics;
    private WebTarget keystone;

    public void init(String login, String pass) {///todo:change with settings
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

        Wrapper wrapper = keystone.path("tokens")
                .request()
                .post(Entity.entity(form.toString(), MediaType.APPLICATION_JSON_TYPE), Wrapper.class);
        Access access = wrapper.getAccess();
        token = access.getToken();

        System.out.println("adapters.OpenStackAdapter.init() - resault: " + access.getToken().getId());
    }
    public void close(){
        client.close();
    }

    public List<Meter> getMeters() {
        ArrayList<Meter> meterList;
        GenericType<JAXBElement<List<Meter>>> listGenericType = new GenericType<JAXBElement<List<Meter>>>() {
        };
        Invocation.Builder builder = metrics.request(MediaType.APPLICATION_JSON_TYPE);
        builder.header("X-Auth-Token", token);
        meterList = (ArrayList<Meter>) builder.get(listGenericType).getValue();

        return meterList;
    }

    private Meter getOsMeter(String name) {
        return metrics.path(name)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Auth-Token", token)
                .get(Meter.class);
    }

    private Sample getOsSample(String meter, Date timestamp) {
        Sample sample = new Sample();
        sample.setMeter(meter);
        sample.setUser_id(getOsMeter(meter).getUser_id());
        sample.setRecorded_at(timestamp);

        GenericType<JAXBElement<List<Sample>>> listGenericType = new GenericType<JAXBElement<List<Sample>>>() {
        };
        List<Sample> samples = (List<Sample>) metrics.path(meter)
                .request()
                .header("direct", "True")
                .header("X-Auth-Token", token)
                .post(Entity.entity(sample, MediaType.APPLICATION_JSON_TYPE), listGenericType);

        return samples.get(samples.size() - 1);

    }

    @Override
    public Measure getMeasure(entities.Meter meter, String uid) {
        return getMeasure(meter, uid,new Date());
    }

    @Override
    public Measure getMeasure(entities.Meter meter, String uid, Date timestamp) {
        
        Sample sample = getOsSample(meter.getName(), timestamp);

        Measure measure = new Measure();
        measure.setIdMeter(meter);
        measure.setResource(sample.getSource());
        measure.setTstamp(sample.getRecorded_at());
        measure.setUserId(uid);
        measure.setValue((double) sample.getVolume());

        return measure;
    }

    @Override
    public entities.Meter getMeter(String name) {
        Meter osMeter = getOsMeter(name);
        entities.Meter meter = new entities.Meter();
        meter.setName(name);
        meter.setType(osMeter.getType());
        meter.setUnit(osMeter.getUnit());
        meter.setIdMeters(osMeter.getMeter_id());
        return meter;
    }

}
