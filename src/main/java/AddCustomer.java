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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddCustomer {
    private Stage stage;
    private Scene scene;
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
        return String.format("'%s'", textField.getText());
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
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String sql1 = String.format("INSERT INTO Customer(name, phoneNumber) VALUES (%s, %s);",
                            GetTextFromTextField.getInstance().getText(name),
                            GetTextFromTextField.getInstance().getText(phoneNumber));
                    String sql2 = String.format("INSERT INTO Address VALUES ((SELECT MAX(id) FROM Customer), %s, %s, " +
                                    "%s, %s, %s, %s);",
                            getText(houseNumber),
                            getText(street),
                            getText(ward),
                            getText(district),
                            getText(city),
                            getText(province));
                    Connection connection = ConnectDatabase.getInstance().connect();
                    Statement statement = connection.createStatement();
                    statement.execute(sql1);
                    statement.execute(sql2);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                close();
            }
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
        scene = new Scene(getPane());
        stage.setTitle("Add a customer");
        stage.setScene(scene);
        stage.show();
    }

    private void close() {
        stage.close();
    }
}
