//package br.com.dbc.vemser.imperiodasfichas.database;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//@Component
//public class ConexaoDataBase {
//
//    // puxa do vm args
//    @Value("${db.url}")
//    private String url;
//
//    @Value("${db.user}")
//    private String user;
//
//    @Value("${db.password}")
//    private String password;
//
//    @Value("${db.schema}")
//    private String schema;
//
//    public Connection getConnection() throws SQLException {
//        Connection con = DriverManager.getConnection(url, user, password);
//
//        con.createStatement().execute("alter session set current_schema=" + schema);
//
//        return con;
//    }
//}