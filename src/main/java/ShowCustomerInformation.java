import database.ConnectDatabase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

public class ShowCustomerInformation {
    private Stage stage;
    private Scene scene;
    private Pane pane;
    private Stage newWindow;
    private TableView tableView;

    public ShowCustomerInformation() {
        stage = new Stage();
        pane = new VBox();
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label label = new Label("Customer Information");
        label.setFont(new Font("Arial", 20));

        Button add_a_customer = new Button("Add a customer");

        add_a_customer.setOnAction(event -> {
            AddCustomer addCustomer = new AddCustomer();
            addCustomer.show();
        });

        hBox.getChildren().addAll(label, add_a_customer);

        tableView = new TableView();
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

        showAllInfo();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hBox, tableView);

        pane.getChildren().add(vbox);
    }

    private void showAllInfo() {
        tableView.getItems().clear();
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
    }

    private Pane getPane() {
        Timeline timeline = new Timeline(new KeyFrame(new Duration(2000), t->{
            showAllInfo();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return pane;
    }

    public void show() {
        scene = new Scene(getPane());
        stage.setTitle("Show Customer Information");
        stage.setScene(scene);
        stage.show();
    }

    private void close() {
        stage.close();
    }
}
