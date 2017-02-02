/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Stateless
@LocalBean
public class UnconfirmedUsersBean {

    DataSource ds;

    public UnconfirmedUsersBean() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("MonitoringResource");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getSelectedUser() {
        String user = null;
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection con = null;
            con = ds.getConnection();
            String query = "select username from userroles where role='USER';";

            ps = con.prepareStatement(query); // create a statement
            rs = ps.executeQuery();
            // extract data from the ResultSet
            rs.next();
            user = rs.getString("username");

        } catch (SQLException ex) {
            Logger.getLogger(UnconfirmedUsersBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public ArrayList<String> getUnconfirmedUsers() {
        ArrayList<String> userList = new ArrayList();
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection con = null;
            con = ds.getConnection();
            String query = "select username from userroles where role='USER';";

            ps = con.prepareStatement(query); // create a statement
            rs = ps.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                userList.add(rs.getString("username"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnconfirmedUsersBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public String confirm(String username) {
        if (username != null) {

            PreparedStatement ps = null;
            Connection con = null;
            try {
                if (ds != null) {
                    con = ds.getConnection();
                    if (con != null) {
                        String sql = "update userroles set role='CONFIRMEDUSER' where username=?;";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, username);
                        ps.executeUpdate();

                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    con.close();
                    ps.close();
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

    public String delete(String username) {
        if (username != null) {

            PreparedStatement ps = null;
            Connection con = null;
            try {
                if (ds != null) {
                    con = ds.getConnection();
                    if (con != null) {
                        String sql = "delete from users where username=?;";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, username);
                        ps.executeUpdate();

                        String sql2 = "delete from userroles where username=?;";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1, username);
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
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
        return "error";
    }

}
