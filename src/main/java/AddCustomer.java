import database.ConnectDatabase;
import javafx.scene.Scene;

import java.sql.*;

public class AddCustomer extends ModifyCustomer {
    public AddCustomer() {
        super();
        submit();
    }

    private void submit() {
        submitButton.setOnAction(event -> {
            try {
                String sql1 = "INSERT INTO Customer(name, phoneNumber) VALUES (?, ?)";
                String sql2 = "INSERT INTO Address VALUES ((SELECT max(id) FROM Customer), ?, ?, ?, ?, ?, ?)";
                Connection connection = ConnectDatabase.getInstance().connect();
                PreparedStatement pstmt = connection.prepareStatement(sql1);
                pstmt.setString(1, Singleton.getInstance().getText(name));
                pstmt.setString(2, Singleton.getInstance().getText(phoneNumber));
                pstmt.executeUpdate();
                pstmt.close();
                connection.close();

                connection = ConnectDatabase.getInstance().connect();
                pstmt = connection.prepareStatement(sql2);
                pstmt.setString(1, getText(houseNumber));
                pstmt.setString(2, getText(street));
                pstmt.setString(3, getText(ward));
                pstmt.setString(4, getText(district));
                pstmt.setString(5, getText(city));
                pstmt.setString(6, getText(province));
                pstmt.executeUpdate();
                connection.close();
                pstmt.close();

                connection = ConnectDatabase.getInstance().connect();
                Statement statement = connection.createStatement();
                String sql3 = "SELECT max(id) as id FROM Customer";
                ResultSet rs = statement.executeQuery(sql3);
                newCustomer = new Customer(rs.getInt("id"), name.getText(), phoneNumber.getText(), String.format(
                        "%s %s %s %s " +
                                "%s " +
                                "%s", houseNumber.getText(),
                        street.getText(),
                        ward.getText(),
                        district.getText(),
                        city.getText(),
                        province.getText()).trim().replaceAll(" +", " "));
                notifyObserver();

                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        });
    }

    public void notifyObserver() {
        Singleton.getInstance().getObserver().addCustomer(newCustomer);
    }

    @Override
    public void show() {
        Scene scene = new Scene(getPane());
        stage.setTitle("Add a Customer");
        stage.setScene(scene);
        stage.show();
    }
}
