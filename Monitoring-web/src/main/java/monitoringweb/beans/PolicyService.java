package monitoringweb.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import monitoringweb.entities.Policy;
import monitoringweb.entities.PolicyList;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

 
@ManagedBean(name = "policyService")
@ApplicationScoped
public class PolicyService {
     
   /* public TreeNode createDocuments() {
       
        TreeNode root = new DefaultTreeNode(new Policy("FirstId","8.66"), null);
        TreeNode pol = new DefaultTreeNode(new Policy("SecondId","dd"), root);
        TreeNode pol2 = new DefaultTreeNode(new Policy("ЕршкыId","88.9"), pol);
        return root;
    }*/
}