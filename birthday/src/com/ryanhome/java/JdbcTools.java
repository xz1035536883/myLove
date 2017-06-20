package com.ryanhome.java;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ryan.xu
 * @since 2017/6/5
 */
public class JdbcTools {
    public static void release(Connection conn , ResultSet res, Statement stat){
        if(res !=null){
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stat !=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static Connection getConnection(){
        Properties properties =new Properties();
        Connection connection=null;
        InputStream in = JdbcTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(in);
            String jdbcUrl = properties.getProperty("jdbcUrl");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("password");
            String driver = properties.getProperty("driver");
            try {
                Class.forName(driver);
                try {
                     connection=DriverManager.getConnection(jdbcUrl,user,pass);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

       return connection;
    }
}
