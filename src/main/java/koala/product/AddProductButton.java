package koala.product;


import javafx.scene.control.Button;

public class AddProductButton {
    private Button button;

    public AddProductButton() {
        button = new Button("Add a product");
    }

    public Button getButton() {
        return button;
    }
}
