package monitoringweb.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import monitoringweb.entities.Policy;
import org.primefaces.model.TreeNode;

 
@ManagedBean(name="ttBasicView")
@ViewScoped
public class PolicyView implements Serializable {
     
    private TreeNode root;
     
    private Policy selectedDocument;
         
    @ManagedProperty("#{policyService}")
    private PolicyService service;
     
    @PostConstruct
    public void init() {
   //     root = service.createDocuments();
    }
 
    public TreeNode getRoot() {
        return root;
    }
 
    public void setService(PolicyService service) {
        this.service = service;
    }
 
    public Policy getSelectedDocument() {
        return selectedDocument;
    }
 
    public void setSelectedDocument(Policy selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}