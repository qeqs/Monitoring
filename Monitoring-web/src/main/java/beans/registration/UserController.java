package monitoringweb.beans.registration;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import registration.RegistrationBean;

@ManagedBean(name = "user")
@RequestScoped
public class UserController {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String login;
    @EJB
    RegistrationBean regBean;

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String add() {
        return regBean.addUser(login, password, firstName, lastName, email);
    }
}