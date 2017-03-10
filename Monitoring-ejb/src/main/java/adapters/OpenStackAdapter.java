package adapters;

import adapters.Adapter;
import adapters.entities.Access;
import adapters.entities.Sample;
import adapters.entities.Token;
import adapters.entities.Wrapper;
import controllers.rmi.entities.Instance;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import org.glassfish.jersey.client.JerseyClientBuilder;

@Stateless
@LocalBean
public class OpenStackAdapter implements Adapter {

    private WebTarget webTarget;
    private Client client;
    private Token token;
    private WebTarget metrics;
    private WebTarget keystone;
    private Profile profile;

    @PostConstruct
    public void init() {
        client = JerseyClientBuilder.createClient();
    }

    @PreDestroy
    public void close() {
        client.close();
    }

    private Token getToken() {

        StringBuilder form = new StringBuilder()
                .append("{\"auth\": {\"passwordCredentials\":{\"username\": \"")
                .append(profile.getIdSettings().getOsUsername())
                .append("\",\"password\":  \"")
                .append(profile.getIdSettings().getOsPassword())
                .append("\"}}}");

        Wrapper wrapper = keystone.path("/tokens/")
                .request()
                .post(Entity.entity(form.toString(), MediaType.APPLICATION_JSON_TYPE), Wrapper.class);
        Access access = wrapper.getAccess();
        token = access.getToken();
        return token;
    }

    private adapters.entities.Meter getOsMeter(String name) {
        return metrics.path(name)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("X-Auth-Token", token.getId())
                .get(adapters.entities.Meter.class);
    }

    private Sample getOsSample(String meter, Date timestamp, String osId) {
        Sample sample = new Sample();
        sample.setMeter(meter);
        sample.setUser_id(getOsMeter(meter).getUser_id());
        sample.setRecorded_at(timestamp);
        sample.setResource_id(osId);

        GenericType<JAXBElement<List<Sample>>> listGenericType = new GenericType<JAXBElement<List<Sample>>>() {
        };

        List<Sample> samples = (List<Sample>) metrics.path(meter)
                .request()
                .header("direct", "True")
                .header("X-Auth-Token", token.getId())
                .post(Entity.entity(sample, MediaType.APPLICATION_JSON_TYPE), listGenericType).getValue();

        return samples.get(samples.size() - 1);

    }

    @Override
    public Meter getMeter(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Measure getMeasure(controllers.rmi.entities.Meter meter) {
        return getMeasure(meter, new Timestamp(new Date().getTime()));
    }

    @Override
    public Measure getMeasure(controllers.rmi.entities.Meter meter, Date timestamp) {
        return getMeasure(meter, timestamp, profile.getIdVnf().getInstanceList().get(0));

    }

    public List<Measure> getMeasureList(controllers.rmi.entities.Meter meter, Date timestamp) {
        ArrayList<Measure> list = new ArrayList<>();
        for (Instance instance : profile.getIdVnf().getInstanceList()) {
            list.add(getMeasure(meter, timestamp, instance));
        }
        return list;
    }

    public Measure getMeasure(controllers.rmi.entities.Meter meter, Date timestamp, Instance instance) {

        Sample sample = getOsSample(meter.getName(), timestamp, instance.getIdOpenstack());

        Measure measure = new Measure();
        measure.setIdMeter(meter);
        measure.setResource(sample.getSource());
        measure.setSource("openstack");
        measure.setTstamp(sample.getRecorded_at());
        measure.setValue((double) sample.getVolume());
        measure.setIdProfile(profile);

        return measure;
    }

    @Override
    public Adapter setUser(String uid) {
        return OpenStackAdapter.this;
    }

    @Override
    public Adapter setProfile(Profile profile) {
        this.profile = profile;
        webTarget = client.target(profile.getIdSettings().getCeliometerEndpoint());
        keystone = client.target(profile.getIdSettings().getKeystoneEndpoint());
        metrics = webTarget.path("meters");
        getToken();
        return OpenStackAdapter.this;
    }


}
