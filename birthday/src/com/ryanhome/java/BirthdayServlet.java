package com.ryanhome.java;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ryan.xu
 * @since 2017/6/20
 */
public class BirthdayServlet extends HttpServlet{
    public void saveContext(String content,String ip){
        Connection connection =null;
        Statement statement =null;
        ResultSet res = null;
        connection = JdbcTools.getConnection();
        try {
            statement=connection.createStatement();
            String sql = "insert into birthday(ip,content) VALUES('"+ip+"','"+content+"') ";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcTools.release(connection,res,statement);
        }
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
            ip = request.getHeader("Proxy-Client-IP");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
            ip = request.getRemoteAddr();
        return ip;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String context=req.getParameter("content");
        String ip = this.getIpAddr(req);
        this.saveContext(context,ip);
        resp.sendRedirect("birthdaySources");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
