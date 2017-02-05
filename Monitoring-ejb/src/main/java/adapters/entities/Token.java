package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token{
        private String issued_at;
        private String expires;
        private String id;
        private String[] audit_ids;

        @JsonProperty("issued_at")
        public String getIssued_at() {
            return issued_at;
        }

        public void setIssued_at(String issued_at) {
            this.issued_at = issued_at;
        }

        @JsonProperty("expires")
        public String getExpires() {
            return expires;
        }

        public void setExpires(String expires) {
            this.expires = expires;
        }

        @JsonProperty("id")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        
        @JsonProperty("audit_ids")
        public String[] getAudit_ids() {
            return audit_ids;
        }

        public void setAudit_ids(String[] audit_ids) {
            this.audit_ids = audit_ids;
        }
    }
