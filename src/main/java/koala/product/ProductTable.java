package koala.product;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import koala.database.ConnectDatabase;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductTable {
    private TableView tableView;

    public ProductTable() {
        tableView = new TableView();
        setup();
        showAllInfo();
        tableView.setRowFactory(t -> {
            TableRow row = new TableRow();
            showImage(row);
            showMenu(row);
            return row;
        });
    }

    public TableView getTableView() {
        return tableView;
    }

    private void setup() {
        tableView.setMinWidth(1000);

        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));

        TableColumn<Product, String> providerCol = new TableColumn<>("Provider");
        providerCol.setCellValueFactory(new PropertyValueFactory<>("provider"));

//        TableColumn<Product, String> imageCol = new TableColumn<>("Image");
//        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn<Product, String> buyPriceCol = new TableColumn<>("Buy Price");
        buyPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));

        TableColumn<Product, String> sellPriceCol = new TableColumn<>("Sell Price");
        sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        TableColumn<Product, String> howToUseCol = new TableColumn<>("How To Use");
        howToUseCol.setCellValueFactory(new PropertyValueFactory<>("howToUse"));

        tableView.getColumns().addAll(idCol,
                nameCol,
                providerCol,
                buyPriceCol,
                sellPriceCol
        );
    }

    private void showAllInfo() {
        tableView.getItems().clear();
        try {
            String sql = "select * from Product";

            Connection connection = ConnectDatabase.getInstance().connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getString("provider"),
                        rs.getBytes("image"),
                        rs.getFloat("buyPrice"),
                        rs.getFloat("sellPrice"),
                        rs.getString("howToUse"));
                tableView.getItems().add(product);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showImage(TableRow row) {
        Tooltip tp = new Tooltip();
        row.setOnMousePressed(event -> {
            if (row.getItem() != null) {
                Image img = new Image(new ByteArrayInputStream(((Product) row.getItem()).getImage()),
                        150,
                        150,
                        true,
                        true);
                tp.setGraphic(new ImageView(img));
                Node node = (Node) event.getSource();
                tp.show(node,
                        event.getScreenX(),
                        event.getScreenY());
            }
        });

        row.setOnMouseExited(event -> {
            if (row.getItem() != null)
                tp.hide();
        });
    }

    private void showMenu(TableRow row) {
        ContextMenu menu = new ContextMenu();
        MenuItem showDesc = new MenuItem("Description");
        MenuItem showHowToUse = new MenuItem("How to use");

        showDesc.setOnAction(event -> {
            if (row.getItem() != null) {
                showContent(((Product) row.getItem()).getDesc());
            }
        });

        showHowToUse.setOnAction(event -> {
            if (row.getItem() != null) {
                showContent(((Product) row.getItem()).getHowToUse());
            }
        });

        menu.getItems().addAll(showDesc, showHowToUse);
        row.setContextMenu(menu);
    }

    private void showContent(String content) {
        Pane pane = new Pane();
        TextArea textArea = new TextArea(content);
        textArea.setWrapText(true);
        textArea.setMinHeight(500);
        pane.getChildren().add(textArea);
        textArea.setEditable(false);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Description");
        stage.setScene(scene);
        stage.show();
    }
}
