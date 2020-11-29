package com.test.fiveinarow.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private final static String url = "jdbc:derby:five_in_a_row;create=true";
    private final static String username = "david";
    private final static String password = "david";

    static Connection createDBconncetion() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        return connection;
    }

    public static void closeAll(ResultSet rs, PreparedStatement pstmt, Statement statement, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean excuteUpdate(String sql, List<Object> params) {
        int result = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = createDBconncetion();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));  ////
                }
            }
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, pstmt, null, connection);
        }
        return result > 0 ? true : false;
    }

    public static boolean createTable(String sql) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = createDBconncetion();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (tableExists(e)) {
                return true;
            }
            e.printStackTrace();
            return false;
        } finally {
            closeAll(null, null, statement, connection);
        }
        return true;
    }
    
    public static int getRecordCount(String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = createDBconncetion();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));
                }
            }
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, pstmt, null, connection);
        }
        return -1;
    }

    public static <T> List<T> executeQuery(String sql, List<Object> params, Class<T> cls) {////////////////。。。。。。。。。get the label name from database in list
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> data = new ArrayList<T>();

        try {
            connection = createDBconncetion();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i)); ////....
                }
            }
            rs = pstmt.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            while (rs.next()) {
                T m = cls.newInstance();
                for (int i = 0; i < rsd.getColumnCount(); i++) {
                    String col_name = rsd.getColumnName(i + 1);
                    Object value = rs.getObject(col_name);   /////....
                    
                    if (null != value) {
                        for (Field field : cls.getDeclaredFields()) {
                            if (field.getName().equalsIgnoreCase(col_name)) {
                                field.setAccessible(true);
                                field.set(m, value);
                                break;
                            }
                        }
                    }
                }
                data.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean tableExists(Exception e) {
        if (e.getMessage().contains("X0Y32") || e.getMessage().contains("already exists")) {  ////////
            return true;
        }

        return false;
    }
}
