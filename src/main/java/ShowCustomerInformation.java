import database.ConnectDatabase;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;

public class ShowCustomerInformation {
    private Pane pane;

    public ShowCustomerInformation() {
        pane = new Pane();
        Label label = new Label("Customer Information");
        label.setFont(new Font("Arial", 20));

        TableView tableView = new TableView();
        tableView.setMinWidth(600);

        TableColumn<Integer, Customer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, Customer> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(140);

        TableColumn<String, Customer> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<String, Customer> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().addAll(idCol, nameCol, phoneNumberCol, addressCol);

        try {
            String sql = "SELECT C.id,\n" +
                    "       C.name,\n" +
                    "       C.phoneNumber,\n" +
                    "       (A.houseNumber || ' ' || A.street || ' ' || A.ward || ' ' || A.district\n" +
                    "           || ' ' || A.city || ' ' || A.province) as \"Address\"\n" +
                    "FROM Customer C\n" +
                    "         left outer join Address A on C.id = A.customerID";
            Connection connection = ConnectDatabase.getInstance().connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phoneNumber"),
                        rs.getString("Address"));
                tableView.getItems().add(customer);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableView);


        pane.getChildren().add(vbox);
    }

    public Pane getPane() {
        return pane;
    }
}
