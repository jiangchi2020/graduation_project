package com.scarike.gp.crawler.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtil {
    public static Connection getConn() {
        return conn;
    }

    private static Connection conn=null;

    static {
        try {
            Class.forName(Context.DB_DRIVER);
            conn = DriverManager.getConnection(Context.DB_URL,Context.DB_USERNAME,Context.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static int execute(String sql){
        try(Statement statement = conn.createStatement()){
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
