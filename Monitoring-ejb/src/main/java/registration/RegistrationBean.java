/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Stateless
@LocalBean
public class RegistrationBean {

    DataSource ds;
    @EJB
    private EmailSendingBean emailSending;

    public RegistrationBean() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("MonitoringResource");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String addUser(String login, String password, String firstName, String lastName,
            String email) {
        if (login != null & password != null) {

            PreparedStatement ps = null;
            Connection con = null;
            try {
                if (ds != null) {
                    con = ds.getConnection();
                    if (con != null) {
                        String SHA256password = Base64Encode(password);
                        String sql = "INSERT INTO users(username, passwd) VALUES(?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, login);
                        ps.setString(2, SHA256password);
                        ps.executeUpdate();

                        String sql2 = "INSERT INTO userroles(username, role) VALUES(?,?)";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1, login);
                        ps.setString(2, "USER");
                        ps.executeUpdate();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    con.close();
                    ps.close();
                    emailSending.send(login, firstName, lastName, email);
                    return "success";
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
        return "error";
    }

    private String Base64Encode(String pass) {
        return Base64.getEncoder().encodeToString(encrypt(pass));
    }

    private byte[] encrypt(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
