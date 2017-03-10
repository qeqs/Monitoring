package scheduler;

import adapters.Adapter;
import adapters.OpenStackAdapter;
import adapters.SnmpAdapter;
import adapters.TestAdapter;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public enum AdapterType {
    Rest {
        @EJB
        private OpenStackAdapter restAdapter;

        @Override
        public Adapter getAdapterImpl() {
            return restAdapter;
        }

    },
    Snmp {
        @EJB
        private SnmpAdapter SnmpAdapter;

        @Override
        public Adapter getAdapterImpl() {
            return SnmpAdapter;
        }

    },
    Test {
        @EJB
        private TestAdapter testAdapter;

        @Override
        public Adapter getAdapterImpl() {
            return testAdapter;
        }

    };

    public abstract Adapter getAdapterImpl();
}
