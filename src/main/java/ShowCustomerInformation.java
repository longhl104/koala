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
        tableView.setMaxWidth(180);

        TableColumn<Integer, Customer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, Customer> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(140);

        tableView.getColumns().addAll(idCol, nameCol);

        try {
            String sql = "SELECT * FROM Customer";
            Connection connection = ConnectDatabase.getInstance().connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"), rs.getString("name"));
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
