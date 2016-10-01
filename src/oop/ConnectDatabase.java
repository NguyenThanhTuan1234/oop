    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manh
 */
public class ConnectDatabase {

    private final String table = "lichsu";
    private final String table1 = "dutoan";
    private final String table3 = "ls";
    private static Connection connection;

    public static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        String url = "jdbc:postgresql://localhost:5432/Demo";
        try {
            connection = DriverManager.getConnection(url, "postgres", ")@)*!((%");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (connection == null) {
            System.out.println("Kết nối không thành công");
        } else {
            System.out.println("Kết nối thành công.");
        }

    }

    public ResultSet getData() {
        ResultSet rs = null;
        String sqlCommand = "select * from " + table;
        Statement st;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sqlCommand);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("select error\n" + ex.toString());
        }
        return rs;

    }

    public ResultSet getDataDuToan() {
        ResultSet rs = null;
        String sqlCommand = "select * from dutoan";
        Statement st;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sqlCommand);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("select error\n" + ex.toString());
        }
        return rs;

    }

    public ResultSet getDataId(String ngay) {
        ResultSet rs = null;
        String sqlCommand = "select * from lichsu where ngay = ? ";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, ngay);
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("select error\n" + ex.toString());
        }
        return rs;
    }

    public ResultSet getDataMonth(int thang) {
        ResultSet rs = null;
        String sqlCommand = "select * from dutoan where thang = ? ";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setInt(1, thang);
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("select error\n" + ex.toString());
        }
        return rs;

    }

    public void deleteId(String ngay) {
        String sqlCommand = "delete from lichsu where ngay = ?";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, ngay);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("delete error \n" + ex.toString());
        }
    }

    public void Insert(Nhap n) {
        String sqlCommand = "insert into " + table1 + " values( ?, ? ) ";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setInt(1, n.getThang());
            pst.setInt(2, n.getSoTien());
            pst.executeUpdate();
            System.out.println("Insert success");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("insert error \n" + ex.toString());
        }
    }

    public void InsertLS(CapNhat x) {
        String sqlCommand = "insert into " + table + " values( ?, ?, ? ) ";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, x.getDay());
            pst.setInt(2, x.getSoTien());
            pst.setString(3, x.getCongViec());
            pst.executeUpdate();
            System.out.println("Insert success");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("insert error \n" + ex.toString());
        }
    }

    public ResultSet Sum(int thang) {
        ResultSet rs = null;
        String sqlCommand = "select sotien from lichsu where thang = ? ";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setInt(1, thang);
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("select error\n" + ex.toString());
        }
        return rs;
    }

    public int getMonthFromString(String date) {
        String[] parts = date.split("-");
        String part2 = parts[1];
        int month = Integer.parseInt(part2);
        return month;

    }

    public void showData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getInt(2) + rs.getString(3));

        }
    }

    public static void main(String[] args) throws SQLException {
        ConnectDatabase cndb = new ConnectDatabase();
        ConnectDatabase.connect();
        cndb.showData(cndb.getDataId("2016-01-02"));
        cndb.showData(cndb.Sum(1));
    }

}
