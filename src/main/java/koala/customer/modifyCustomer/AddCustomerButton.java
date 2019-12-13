package koala.customer.modifyCustomer;

import javafx.scene.control.Button;

public class AddCustomerButton {
    private Button button;

    public AddCustomerButton() {
        button = new Button("Add a customer");
        button.setOnAction(event -> {
            ModifyCustomer addCustomer = new AddCustomer();
            addCustomer.show();
        });
    }

    public Button getButton() {
        return button;
    }
}
