package adapters.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wrapper {
    private Access access;
    private Network network;
    
    
    @JsonProperty("access")
    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    /**
     * @return the network
     */
    @JsonProperty("network")
    public Network getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(Network network) {
        this.network = network;
    }
    
}
