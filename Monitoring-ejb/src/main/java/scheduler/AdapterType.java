package scheduler;

import adapters.OpenStackAdapter;
import adapters.SnmpAdapter;
import adapters.TestAdapter;


public enum AdapterType {
    Rest,
    Snmp,
    Test;
    
    public static AdapterType parse(Class adapter) {
        if(adapter.isAssignableFrom(OpenStackAdapter.class))return Rest;
        if(adapter.isAssignableFrom(TestAdapter.class))return Test;
        if(adapter.isAssignableFrom(SnmpAdapter.class))return Snmp;
        return null;
    }
}
