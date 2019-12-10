package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    private static ConnectDatabase instance;

    private ConnectDatabase() {}

    public static ConnectDatabase getInstance() {
        if (instance == null)
            instance = new ConnectDatabase();
        return instance;
    }

    public Connection connect() {
        String url = "jdbc:sqlite:D:\\Project\\Product Manager\\src\\main\\resources\\sqlite\\koala.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}