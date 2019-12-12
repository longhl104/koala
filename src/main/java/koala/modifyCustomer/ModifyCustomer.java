package koala.modifyCustomer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import koala.Customer;

public abstract class ModifyCustomer {
    protected Stage stage;
    private Pane pane;
    protected TextField name;
    protected TextField phoneNumber;
    protected TextField houseNumber;
    protected TextField street;
    protected TextField ward;
    protected TextField district;
    protected TextField city;
    protected TextField province;
    protected Customer newCustomer;
    protected Button submitButton;

    private HBox placeHolder(String label, TextField textField) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(new Label(label), textField);
        return hBox;
    }

    protected String getText(TextField textField) {
        if (textField.getText() == null)
            return "";
        return textField.getText();
    }

    public ModifyCustomer() {
        stage = new Stage();
        pane = new Pane();

        name = new TextField();
        phoneNumber = new TextField();
        houseNumber = new TextField();
        street = new TextField();
        ward = new TextField();
        district = new TextField();
        city = new TextField();
        province = new TextField();

        setup();
    }

    protected void setup() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(placeHolder("Name*", name),
                placeHolder("Phone Number", phoneNumber),
                placeHolder("House Number", houseNumber),
                placeHolder("Street", street),
                placeHolder("Ward", ward),
                placeHolder("District", district),
                placeHolder("City", city),
                placeHolder("Province", province));

        submitButton = new Button("Submit");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(submitButton);
        vBox.getChildren().add(hBox);

        pane.getChildren().add(vBox);
    }

    protected Pane getPane() {
        return pane;
    }

    public abstract void show();

    public abstract void notifyObserver();

    protected void close() {
        stage.close();
    }
}
