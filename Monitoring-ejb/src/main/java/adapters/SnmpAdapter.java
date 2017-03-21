package adapters;

import adapters.Adapter;
import controllers.rmi.entities.Instance;
import controllers.rmi.entities.Measure;
import controllers.rmi.entities.Meter;
import controllers.rmi.entities.Profile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

@Stateless
@LocalBean
public class SnmpAdapter implements Adapter {

    private final static int SNMP_RETRIES = 1;
    private final static long SNMP_TIMEOUT = 1000L;
    private Snmp snmp = null;
    private TransportMapping transport = null;
    private Profile profile;

    @Override
    public Meter getMeter(String name) {
        return null;
    }

    @Override
    public Measure getMeasure(Meter meter) {
        return getMeasure(meter, new Date());
    }

    @Override
    public Measure getMeasure(Meter meter, Date timestamp) {
        if (meter.getOid() == null || meter.getOid().equals("")) {
            return null;
        }
        Measure measure = new Measure();
        try {

            start();
            measure.setValue((double) send(getTarget(), meter.getOid(), PDU.GET));
            measure.setTstamp(timestamp);
            measure.setResource(profile.getIdSnmp().getTarget());
            measure.setSource("snmp");
            measure.setIdMeter(meter);
            measure.setIdProfile(profile);
            System.err.println("Created snmp measure with value " + measure.getValue());
        } catch (IOException ex) {
            Logger.getLogger(SnmpAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stop();
            } catch (IOException ex) {
                Logger.getLogger(SnmpAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return measure;
    }

    public Measure getMeasure(Meter meter, Date timestamp, Instance instance) {
        Measure measure = new Measure();
        try {
            start();
            measure.setValue((double) send(getTarget(instance.getIp()), meter.getOid(), PDU.GET));
            measure.setTstamp(timestamp);
            measure.setResource(instance.getIp());
            measure.setSource("snmp");
            measure.setIdMeter(meter);
            measure.setIdProfile(profile);

        } catch (IOException ex) {
            Logger.getLogger(SnmpAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stop();
            } catch (IOException ex) {
                Logger.getLogger(SnmpAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return measure;
    }

    @Override
    public Adapter setUser(String uid) {
        return SnmpAdapter.this;
    }

    @Override
    public Adapter setProfile(Profile profile) {
        this.profile = profile;
        return SnmpAdapter.this;
    }

    @Override
    public List<Measure> getMeasureList(controllers.rmi.entities.Meter meter, Date timestamp) {
        ArrayList<Measure> list = new ArrayList<>();
        for (Instance instance : profile.getIdVnf().getInstanceList()) {
            list.add(getMeasure(meter, timestamp, instance));
        }
        return list;
    }

    public List<String> doSnmpwalk() throws IOException {

        ArrayList<String> results = new ArrayList<>();

        start();
        Target target = getTarget();

        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        List<TreeEvent> events = treeUtils.getSubtree(target, new OID(""));
        if (events
                == null || events.isEmpty()) {
            Logger.getLogger(SnmpAdapter.class.getName()).log(Level.WARNING, "snmpWalk error");
            return results;
        }

        for (TreeEvent event : events) {
            if (event != null) {
                if (event.isError()) {
                    Logger.getLogger(SnmpAdapter.class.getName()).log(Level.WARNING, event.getErrorMessage());
                }
                VariableBinding[] varBindings = event.getVariableBindings();
                if (varBindings != null) {
                    for (VariableBinding varBinding : varBindings) {

                        try {
                            varBinding.getVariable().toInt();
                        } catch (UnsupportedOperationException ex) {
                            continue;
                        }
                        results.add(varBinding.getOid().format());
                    }
                }
            }
        }
        return results;
    }

    private int send(Target target, String oid, int type) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(type);

        ResponseEvent event = snmp.send(pdu, target, null);

        if (event != null) {
            if (event.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                return event.getResponse().getVariableBindings().firstElement().getVariable().toInt();
            } else {
                throw new IOException("No success in response(this is code error mesg)");
            }
        } else {
            throw new IOException("NULL response (this is code error mesg)");
        }
    }

    private Target getTarget(String ip) {
        Address targetAddress = GenericAddress.parse(ip);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(profile.getIdSnmp().getCommunity()));
        target.setAddress(targetAddress);
        target.setRetries(SNMP_RETRIES);
        target.setTimeout(SNMP_TIMEOUT);
        target.setVersion(SnmpConstants.version1);
        return target;
    }

    private Target getTarget() {
        if (profile == null || profile.getIdSnmp() == null || profile.getIdSnmp().getTarget() == null) {
            System.err.println("PROFILE NULL " + profile);
        }
        return getTarget(profile.getIdSnmp().getTarget());
    }

    private void start() throws IOException {
        transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }

    private void stop() throws IOException {
        try {
            if (transport != null) {
                transport.close();
                transport = null;
            }
        } finally {
            if (snmp != null) {
                snmp.close();
                snmp = null;
            }
        }
    }

}
