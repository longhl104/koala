import database.ConnectDatabase;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerTable {
    private TableView tableView;

    public CustomerTable() {
        tableView = new TableView();
        setup();
        showAllInfo();
    }

    public TableView getTableView() {
        return tableView;
    }

    private void setup() {
        tableView.setMinWidth(600);

        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(140);

        TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().addAll(idCol, nameCol, phoneNumberCol, addressCol);

        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("menu item 1");
        MenuItem menuItem2 = new MenuItem("menu item 2");
        MenuItem menuItem3 = new MenuItem("menu item 3");

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);

        tableView.setContextMenu(contextMenu);
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
}
