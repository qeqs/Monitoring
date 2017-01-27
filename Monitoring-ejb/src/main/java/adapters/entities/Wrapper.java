package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wrapper {
    private Access access;
    
    @JsonProperty("access")
    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
    
}
