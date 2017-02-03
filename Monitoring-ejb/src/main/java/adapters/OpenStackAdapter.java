package adapters;

import adapters.entities.Access;
import adapters.entities.Meter;
import adapters.entities.Sample;
import adapters.entities.Token;
import adapters.entities.Wrapper;
import dao.SettingsFacade;
import entities.Measure;
import entities.Settings;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    @EJB
    private SettingsFacade settingsFacade;
    private WebTarget webTarget;
    private Client client;
    private Token token;
    private WebTarget metrics;
    private WebTarget keystone;
    private Settings settings;

    @PostConstruct
    public void init() {
        client = JerseyClientBuilder.createClient();
    }
    @PreDestroy
    public void close() {
        client.close();
    }
    public void setUser(String uid){     
        if(settingsFacade==null)settingsFacade = new SettingsFacade();
        settings = settingsFacade.findByUid(uid);
        webTarget = client.target(settings.getCeliometerEndpoint());
        keystone = client.target(settings.getKeystoneEndpoint());
        metrics = webTarget.path("meters");
        getToken();
    }
    private Token getToken() {


        StringBuilder form = new StringBuilder()
                .append("{\"auth\": {\"passwordCredentials\":{\"username\": \"")
                .append(settings.getOsUsername())
                .append("\",\"password\":  \"")
                .append(settings.getOsPassword())
                .append("\"}}}");

        Wrapper wrapper = keystone.path("tokens")
                .request()
                .post(Entity.entity(form.toString(), MediaType.APPLICATION_JSON_TYPE), Wrapper.class);
        Access access = wrapper.getAccess();
        token = access.getToken();
        return token;
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
        return getMeasure(meter, uid, new Date());
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