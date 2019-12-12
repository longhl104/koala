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
        if (textField == null)
            return "";
        return String.format("%s", textField.getText()).trim().replaceAll(" +", " ");
    }

    public String getText(String text) {
        if (text == null)
            return "";
        return text.trim().replaceAll(" +", " ");
    }

    public void setObserver(Observer observer) {
        Singleton.observer = observer;
    }

    public Observer getObserver() {
        return observer;
    }

}
