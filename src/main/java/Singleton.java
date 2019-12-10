import javafx.scene.control.TextField;

public class Singleton {
    private static Singleton instance;

    private static Observer observer;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null)
            return new Singleton();
        return instance;
    }

    public String getText(TextField textField) {
        if (textField.getText().equals(""))
            return null;
        return String.format("%s", textField.getText());
    }

    public void setObserver(Observer observer) {
        Singleton.observer = observer;
    }

    public Observer getObserver() {
        return observer;
    }
}
