package adapters;

import dao.SettingsFacade;
import entities.Measure;
import entities.Meter;
import entities.Settings;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
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

@Stateless
@LocalBean
public class SnmpAdapter{

    @EJB
    private SettingsFacade settingsFacade;
    private Settings settings;

    private final static String SNMP_COMMUNITY = "public";
    private final static String OID = "1.3.6.1.2.1.1.3.0";
    private final static int SNMP_RETRIES = 3;
    private final static long SNMP_TIMEOUT = 1000L;

    private Snmp snmp = null;
    private TransportMapping transport = null;
    private String test = null;

    private void test() throws IOException {
        Target t = getTarget("udp:127.0.0.1/161");
        String r = send(t, OID);
        test = r;
        System.out.println(r);
    }

    private String send(Target target, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, target, null);
        if (event != null) {
            return event.getResponse().get(0).toString();
        } else {
            return "Timeout exceeded";
        }
    }

    private Target getTarget(String address) {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(SNMP_COMMUNITY));
        target.setAddress(targetAddress);
        target.setRetries(SNMP_RETRIES);
        target.setTimeout(SNMP_TIMEOUT);
        target.setVersion(SnmpConstants.version1);
        return target;
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

    public void tes() {
        SnmpAdapter t = new SnmpAdapter();
        try {
            try {
                t.start();
                t.test();
            } finally {
                t.stop();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public Meter getMeter(String name) {
        tes();
        Meter meter = new Meter();
        meter.setName(name);
        meter.setType(test);
        meter.setUnit(test);
        meter.setDescription("fake meter");
        return meter;
    }

    public Measure getMeasure(Meter meter) {
        return getMeasure(meter, new Timestamp(new Date().getTime()));
    }

    public Measure getMeasure(Meter meter, Date timestamp) {
        Random random = new Random(new Date().getTime());
        Measure measure = new Measure();
        measure.setIdMeasure(String.valueOf(random.nextLong()));
        measure.setIdMeter(meter);
        measure.setResource("test");
        measure.setTstamp(timestamp);
        measure.setValue(random.nextDouble() % 100 + random.nextInt() % 50);
        measure.setUserId(settings.getUid());

        return measure;
    }
}
