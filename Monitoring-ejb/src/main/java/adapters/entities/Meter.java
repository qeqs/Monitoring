package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meter {
    private String meter_id;
    private String name;
    private String project_id;
    private String resource_id;
    private String source;
    private String type;
    private String unit;
    private String user_id;
    
    @JsonProperty("meter_id")
    public String getMeter_id() {
        return meter_id;
    }

    /**
     * @param meter_id the meter_id to set
     */
    public void setMeter_id(String meter_id) {
        this.meter_id = meter_id;
    }

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
     * @return the project_id
     */
    @JsonProperty("project_id")
    public String getProject_id() {
        return project_id;
    }

    /**
     * @param project_id the project_id to set
     */
    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    /**
     * @return the resource_id
     */
    @JsonProperty("resource_id")
    public String getResource_id() {
        return resource_id;
    }

    /**
     * @param resource_id the resource_id to set
     */
    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    /**
     * @return the source
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the unit
     */
    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the user_id
     */
    @JsonProperty("user_id")
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
  
}
