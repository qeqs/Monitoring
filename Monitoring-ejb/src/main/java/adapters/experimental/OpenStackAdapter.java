package adapters.experimental;

import adapters.Adapter;
import adapters.entities.Token;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;


@Stateless
@LocalBean
public class OpenStackAdapter implements Adapter{

    
    private WebTarget webTarget;
    private Client client;
    private Token token;
    private WebTarget metrics;
    private WebTarget keystone;
    private Profile profile;
    
    
    
    
    @Override
    public Meter getMeter(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Measure getMeasure(Meter meter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Measure getMeasure(Meter meter, Date timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Adapter setUser(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Adapter setProfile(Profile profile) {
        return OpenStackAdapter.this;
    }

}
