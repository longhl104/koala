import database.ConnectDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class AddCustomer {
    private Stage stage;
    private Pane pane;
    private TextField name;
    private TextField phoneNumber;
    private TextField houseNumber;
    private TextField street;
    private TextField ward;
    private TextField district;
    private TextField city;
    private TextField province;
    private Customer newCustomer;

    private HBox placeHolder(String label, TextField textField) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(new Label(label), textField);
        return hBox;
    }

    private String getText(TextField textField) {
        if (textField.getText() == null)
            return "";
        return textField.getText();
    }

    public AddCustomer() {
        stage = new Stage();
        pane = new Pane();
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        name = new TextField();
        phoneNumber = new TextField();
        houseNumber = new TextField();
        street = new TextField();
        ward = new TextField();
        district = new TextField();
        city = new TextField();
        province = new TextField();

        vBox.getChildren().addAll(placeHolder("Name*", name),
                placeHolder("Phone Number", phoneNumber),
                placeHolder("House Number", houseNumber),
                placeHolder("Street", street),
                placeHolder("Ward", ward),
                placeHolder("District", district),
                placeHolder("City", city),
                placeHolder("Province", province));

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                String sql1 = "INSERT INTO Customer(name, phoneNumber) VALUES (?, ?)";
                String sql2 = "INSERT INTO Address VALUES ((SELECT max(id) FROM Customer), ?, ?, " +
                        "?, ?, ?, ?)";
                Connection connection = ConnectDatabase.getInstance().connect();
                PreparedStatement pstmt = connection.prepareStatement(sql1);
                pstmt.setString(1, Singleton.getInstance().getText(name));
                pstmt.setString(2, Singleton.getInstance().getText(phoneNumber));
                pstmt.executeUpdate();

                pstmt = connection.prepareStatement(sql2);
                pstmt.setString(1, getText(houseNumber));
                pstmt.setString(2, getText(street));
                pstmt.setString(3, getText(ward));
                pstmt.setString(4, getText(district));
                pstmt.setString(5, getText(city));
                pstmt.setString(6, getText(province));
                pstmt.executeUpdate();

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
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(submitButton);
        vBox.getChildren().add(hBox);

        pane.getChildren().add(vBox);
    }

    private Pane getPane() {
        return pane;
    }

    public void show() {
        Scene scene = new Scene(getPane());
        stage.setTitle("Add a customer");
        stage.setScene(scene);
        stage.show();
    }

    private void notifyObserver() {
        Singleton.getInstance().getObserver().update(newCustomer);
    }

    private void close() {
        stage.close();
    }
}
