package koala;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import koala.customer.ShowCustomerInformation;
import koala.product.ShowProductInformation;

public class WelcomePage {
    private VBox pane;

    public WelcomePage() {
        pane = new VBox();
        pane.setPadding(new Insets(5, 5, 5, 5));
        Label label = new Label("Welcome to Koala's Management System");
        label.setFont(new Font("Arial", 20));
        pane.setSpacing(5);
        pane.getChildren().add(label);
        pane.getChildren().add(showCustomer());
        pane.getChildren().add(showProduct());
    }

    private Button showCustomer() {
        Button customerButton = new Button("Show Customers Information");

        customerButton.setOnAction(event -> {
            ShowCustomerInformation showCustomerInformation = new ShowCustomerInformation();
            Singleton.getInstance().setObserver(showCustomerInformation);
            showCustomerInformation.show();
        });
        return customerButton;
    }

    private Button showProduct() {
        Button productButton = new Button("Show Products Information");

        productButton.setOnAction(event -> {
            ShowProductInformation showProductInformation = new ShowProductInformation();
            showProductInformation.show();
        });
        return productButton;
    }

    public Pane getPane() {
        return pane;
    }
}
