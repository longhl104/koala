package koala.customer.modifyCustomer;

import koala.customer.Customer;
import koala.Singleton;
import koala.database.ConnectDatabase;
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
                Singleton.getInstance().voidParameterizedSQL(sql1,
                        Singleton.getInstance().getText(name),
                        Singleton.getInstance().getText(phoneNumber));

                Singleton.getInstance().voidParameterizedSQL(sql2,
                        getText(houseNumber),
                        getText(street),
                        getText(ward),
                        getText(district),
                        getText(city),
                        getText(province));

                Connection connection = ConnectDatabase.getInstance().connect();
                Statement statement = connection.createStatement();
                String sql3 = "SELECT max(id) as id FROM Customer";
                ResultSet rs = statement.executeQuery(sql3);
                newCustomer = new Customer(rs.getInt("id"),
                        name.getText(),
                        phoneNumber.getText(),
                        String.format(
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
