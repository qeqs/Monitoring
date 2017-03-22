package registration;

import de.rtner.security.auth.spi.PBKDF2HexFormatter;
import de.rtner.security.auth.spi.PBKDF2Parameters;
import de.rtner.security.auth.spi.SimplePBKDF2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.*;
import java.io.Serializable;

@Stateless
@LocalBean
public class AuthenticationBean implements Serializable {
    
DataSource ds;

    public AuthenticationBean() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/jboss/datasources/MonitoringResource");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getUserName(String login) {

        String firstName = null;
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection con = null;
            con = ds.getConnection();
            String query = "select first_name from users where username=?;";
            ps = con.prepareStatement(query); // create a statement
            ps.setString(1, login);
            rs = ps.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                firstName = rs.getString("first_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return firstName;
    }
    
}
