/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

@Stateless
@LocalBean
public class RegistrationBean {

    DataSource ds;

    public RegistrationBean() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/jboss/datasources/MonitoringResource");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String addUser(String login, String password, String firstName, String lastName,
            String email) {
        if (!verifyLoginExists(login)) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                if (ds != null) {
                    con = ds.getConnection();

                    ResultSet rs = null;
                    if (con != null) {
                        String hashPassword = hashPass(password, null);
                        
                        String sql = "INSERT INTO users(username,email,first_name,last_name,issocial, passwd)"
                                + " VALUES(?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, login);
                        ps.setString(2, email);
                        ps.setString(3, firstName);
                        ps.setString(4, lastName);
                        ps.setBoolean(5, false);
                        ps.setString(6, hashPassword);
                        ps.executeUpdate();

                        String sql2 = "INSERT INTO userroles(username, role) VALUES(?,?)";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1, login);
                        ps.setString(2, "CONFIRMEDUSER");
                        ps.executeUpdate();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                return "error";
            } finally {
                try {
                    con.close();
                    ps.close();
                    return "success";
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "error";
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
        } else {
            return "exist";
        }
    }

    private boolean verifyLoginExists(String login) {
        ArrayList<String> userList = new ArrayList();
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection con = null;
            con = ds.getConnection();
            String query = "select username from users;";

            ps = con.prepareStatement(query); // create a statement
            rs = ps.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                userList.add(rs.getString("username"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (userList.contains(login)) {
            return true;
        } else {
            return false;
        }
    }

    public static String hashPass(String plainText, String storedPassword) {
        if (plainText == null) {
            return null;
        }
        SimplePBKDF2 crypto = new SimplePBKDF2();
        PBKDF2Parameters params = crypto.getParameters();
        params.setHashCharset("UTF-8");
        params.setHashAlgorithm("HmacSHA1");
        params.setIterationCount(1000);
        if (storedPassword != null) {
            new PBKDF2HexFormatter().fromString(params, storedPassword);
        }
        return crypto.deriveKeyFormatted(plainText);
    }

    public void addSocialUser(String login, String password, String firstName, String lastName,
            String email) {

        if (!verifyLoginExists(login)) {
            PreparedStatement ps = null;
            Connection con = null;

            String hashPassword = hashPass(password, null);
            try {
                if (ds != null) {
                    con = ds.getConnection();
                    if (con != null) {

                        String query = "select username from users where username=?;";

                        ps = con.prepareStatement(query); // create a statement
                        ps.setString(1, login);

                        String sql = "INSERT INTO users(username,email,first_name,last_name,issocial, passwd)"
                                + " VALUES(?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, login);
                        ps.setString(2, email);
                        ps.setString(3, firstName);
                        ps.setString(4, lastName);
                        ps.setBoolean(5, true);
                        ps.setString(6, hashPassword);
                        ps.executeUpdate();

                        String sql2 = "INSERT INTO userroles(username, role) VALUES(?,?)";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1, login);
                        ps.setString(2, "CONFIRMEDUSER");
                        ps.executeUpdate();

                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    con.close();
                    ps.close();
                //    emailSending.send(login, firstName, lastName, email);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else {
            PreparedStatement ps = null;
                Connection con = null;
            try {
                con = ds.getConnection();
                String hashPassword = hashPass(password, null);
                
                String sql = "UPDATE users set passwd=? where username =?";
                ps = con.prepareStatement(sql);
                ps.setString(1, hashPassword);
                ps.setString(2, login);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
