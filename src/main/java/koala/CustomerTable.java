package koala;

import koala.database.ConnectDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import koala.modifyCustomer.ChangeCustomer;

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

    private void setRightClickable(TableColumn column) {
        column.setCellFactory(t -> {
            final TableCell cell = new CustomerTableCell();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Edit");
            menuItem.setOnAction(event -> {
                TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                new ChangeCustomer(((Customer) tableView.getItems().get(row)).getId(), row).show();
            });
            contextMenu.getItems().add(menuItem);
            cell.setContextMenu(contextMenu);
            return cell;
        });
    }

    private void setup() {
        tableView.setMinWidth(600);

        TableColumn<Customer, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(140);
        setRightClickable(nameCol);

        TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        setRightClickable(phoneNumberCol);

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        setRightClickable(addressCol);

        tableView.getColumns().addAll(idCol, nameCol, phoneNumberCol, addressCol);
    }

    private void showAllInfo() {
        tableView.getItems().clear();
        try {
            String sql = "SELECT C.id,\n" +
                    "       C.name,\n" +
                    "       C.phoneNumber,\n" +
                    "       A.houseNumber, A.street, A.ward, A.district, A.city, A.province FROM Customer C\n" +
                    "         left outer join Address A on C.id = A.customerID";

            Connection connection = ConnectDatabase.getInstance().connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"), rs.getString("name"),
                        Singleton.getInstance().getText(rs.getString(
                                "phoneNumber")),
                        Singleton.getInstance().getText(String.format("%s %s %s %s %s %s",
                                rs.getString("houseNumber"),
                                rs.getString("street"),
                                rs.getString("ward"),
                                rs.getString("district"),
                                rs.getString("city"),
                                rs.getString("province"))));
                tableView.getItems().add(customer);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

class CustomerTableCell extends TableCell {
    private TextField textField;

    public CustomerTableCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
