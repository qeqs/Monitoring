package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Network {
        
    private String name;
    private String status;

    /**
     * @return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
