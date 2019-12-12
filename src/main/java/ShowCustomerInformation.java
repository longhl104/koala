import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ShowCustomerInformation implements Observer {
    private Stage stage;
    private Scene scene;
    private Pane pane;
    private TableView tableView;

    public ShowCustomerInformation() {
        stage = new Stage();
        pane = new VBox();
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label label = new Label("Customer Information");
        label.setFont(new Font("Arial", 20));

        hBox.getChildren().addAll(label, new AddCustomerButton().getButton());

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        tableView = new CustomerTable().getTableView();
        vbox.getChildren().addAll(hBox, tableView);

        pane.getChildren().add(vbox);
    }

    private Pane getPane() {
        return pane;
    }

    public void show() {
        scene = new Scene(getPane());
        stage.setTitle("Show Customer Information");
        stage.setScene(scene);
        stage.show();
    }

    private void close() {
        stage.close();
    }

    @Override
    public void update(Customer customer) {
        tableView.getItems().add(customer);
    }
}
