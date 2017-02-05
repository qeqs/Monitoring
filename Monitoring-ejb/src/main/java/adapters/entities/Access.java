package adapters.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Access {
        private Token token;
        private Object serviceCatalog;
        private Object user;
        private Object metadata;

        
        @JsonProperty("token")
        public Token getToken() {
            return token;
        }

        public void setToken(Token token) {
            this.token = token;
        }

        @JsonProperty("serviceCatalog")
        public Object getServiceCatalog() {
            return serviceCatalog;
        }

        public void setServiceCatalog(Object serviceCatalog) {
            this.serviceCatalog = serviceCatalog;
        }
        @JsonProperty("user")
        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }
        
        @JsonProperty("metadata")
        public Object getMetadata() {
            return metadata;
        }

        public void setMetadata(Object metadata) {
            this.metadata = metadata;
        }

        public Access(){

      }
}
