/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package httpbotnet.db;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class DBUtils {
   private static Connection con;

   private static String url = "jdbc:mysql://localhost:3306/httpbotnet?user=root&password=mysql";

   public static void connect() {
        try {
            if(con==null) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }


   }

   public static void insertStandardDeviation(double deviation) {
        try {
            String sql = "SELECT COUNT(*) FROM STANDARDDEVIATIONS";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            sql = "INSERT INTO STANDARDDEVIATIONS VALUES(" + (++count) + "," + deviation + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

       
   }

   public static boolean updateBlackList(List<String> blackList) {
        try {
            String[] blacklist = new String[blackList.size()];
            blacklist = blackList.toArray(blacklist);
            String blacklist1 = "";
            for (String blackl : blacklist) {
                blacklist1 += blackl + ";";
            }
            String sql = "SELECT COUNT(*) FROM BLACKLIST";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            if(rs.next()) {
                count = rs.getInt(1);
                if(count>0) {
                    sql = "UPDATE BLACKLIST SET BLACKLISTS='"+blacklist1+"' WHERE ID=1";
                } else {
                    sql = "INSERT INTO BLACKLIST VALUES(1,'"+blacklist1+"')";
                }
            }

            if(stmt.executeUpdate(sql)>0)
                return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
   }

   public static boolean updateGrayList(List<String> blackList) {
        try {
            String[] blacklist = new String[blackList.size()];
            blacklist = blackList.toArray(blacklist);
            String blacklist1 = "";
            for (String blackl : blacklist) {
                blacklist1 += blackl + ";";
            }
            String sql = "SELECT COUNT(*) FROM GRAYLIST";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            if(rs.next()) {
                count = rs.getInt(1);
                if(count>0) {
                    sql = "UPDATE GRAYLIST SET GRAYLISTS='"+blacklist1+"' WHERE ID=1";
                } else {
                    sql = "INSERT INTO GRAYLIST VALUES(1,'"+blacklist1+"')";
                }
            }

            if(stmt.executeUpdate(sql)>0)
                return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
   }

   public static String[] getBlackList() {
        try {
            String sql = "SELECT * FROM BLACKLIST";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String blackList = "";
            if(rs.next()) {
                blackList = rs.getString("BLACKLISTS");
                String[] blackLists = blackList.split(";");
                return blackLists;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new String[0];
   }

   public static String[] getGrayList() {
        try {
            String sql = "SELECT * FROM GRAYLIST";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String blackList = "";
            if(rs.next()) {
                blackList = rs.getString("GRAYLISTS");
                String[] blackLists = blackList.split(";");
                return blackLists;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new String[0];
   }
}
