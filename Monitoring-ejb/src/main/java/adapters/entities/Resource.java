package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Resource {

    @JsonProperty
    private Date first_sample_timestamp;
    @JsonProperty
    private Date last_sample_timestamp;
    @JsonProperty
    private List<String> links;
    @JsonProperty
    private Map<String, String> metadata;
    @JsonProperty
    private String project_id;
    @JsonProperty
    private String resource_id;
    @JsonProperty
    private String source;
    @JsonProperty
    private String user_id;

    public Date getFirst_sample_timestamp() {
        return first_sample_timestamp;
    }

    public void setFirst_sample_timestamp(Date first_sample_timestamp) {
        this.first_sample_timestamp = first_sample_timestamp;
    }

    public Date getLast_sample_timestamp() {
        return last_sample_timestamp;
    }

    public void setLast_sample_timestamp(Date last_sample_timestamp) {
        this.last_sample_timestamp = last_sample_timestamp;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
