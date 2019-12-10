import javafx.scene.control.TextField;

public class GetTextFromTextField {
    private static GetTextFromTextField instance;

    private GetTextFromTextField() {}

    public static GetTextFromTextField getInstance() {
        if (instance == null)
            return new GetTextFromTextField();
        return instance;
    }

    public String getText(TextField textField) {
        if (textField.getText().equals(""))
            return null;
        return String.format("'%s'", textField.getText());
    }
}
