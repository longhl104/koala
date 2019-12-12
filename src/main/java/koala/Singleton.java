package koala;

import javafx.scene.control.TextField;
import koala.database.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Singleton {
    private static Singleton instance;

    private static Observer observer;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null)
            return new Singleton();
        return instance;
    }

    public String getText(TextField textField) {
        if (textField == null)
            return "";
        return String.format("%s", textField.getText()).trim().replaceAll(" +", " ");
    }

    public String getText(String text) {
        if (text == null)
            return "";
        return text.trim().replaceAll(" +", " ");
    }

    public void setObserver(Observer observer) {
        Singleton.observer = observer;
    }

    public Observer getObserver() {
        return observer;
    }

    public void voidParameterizedSQL(String sql, String... args) throws SQLException {
        Connection connection = ConnectDatabase.getInstance().connect();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int index = 1;
        for (String arg : args)
            pstmt.setString(index++, arg);
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }
}
