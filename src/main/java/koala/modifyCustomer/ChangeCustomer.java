package koala.modifyCustomer;

import javafx.scene.Scene;

import koala.Customer;
import koala.Singleton;
import koala.database.ConnectDatabase;

import java.sql.*;

public class ChangeCustomer extends ModifyCustomer {
    private int rowNo;
    private int realRow;

    public ChangeCustomer(int rowNo, int realRow) {
        super();
        this.rowNo = rowNo;
        this.realRow = realRow;

        String sql = "SELECT C.name,\n" +
                "       C.phoneNumber,\n" +
                "       A.houseNumber,\n" +
                "       A.street,\n" +
                "       A.ward,\n" +
                "       A.district,\n" +
                "       A.city,\n" +
                "       A.province\n" +
                "FROM Customer C\n" +
                "         left outer join Address A on C.id = A.customerID\n" +
                "WHERE C.id = ?";
        try {
            Connection connection = ConnectDatabase.getInstance().connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(rowNo));
            ResultSet rs = pstmt.executeQuery();
            name.setText(rs.getString("name"));
            phoneNumber.setText(rs.getString("phoneNumber"));
            houseNumber.setText(rs.getString("houseNumber"));
            street.setText(rs.getString("street"));
            ward.setText(rs.getString("ward"));
            district.setText(rs.getString("district"));
            city.setText(rs.getString("city"));
            province.setText(rs.getString("province"));
            connection.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        submit();
    }

    private void submit() {
        submitButton.setOnAction(event -> {
            try {
                String sql1 = "UPDATE Customer\n" +
                        "SET name = ?, phoneNumber = ?\n" +
                        "WHERE id = ?";
                String sql2 = "UPDATE Address\n" +
                        "SET houseNumber = ?,\n" +
                        "    street      = ?,\n" +
                        "    ward        = ?,\n" +
                        "    district    = ?,\n" +
                        "    city        = ?,\n" +
                        "    province    = ?\n" +
                        "WHERE Address.customerID = (SELECT id\n" +
                        "                            FROM Customer\n" +
                        "                            WHERE id = ?)";

                Singleton.getInstance().voidParameterizedSQL(sql1,
                        getText(name),
                        getText(phoneNumber),
                        String.valueOf(rowNo));

                Singleton.getInstance().voidParameterizedSQL(sql2,
                        getText(houseNumber),
                        getText(street),
                        getText(ward),
                        getText(district),
                        getText(city),
                        getText(province),
                        String.valueOf(rowNo));

                newCustomer = new Customer(rowNo, name.getText(), phoneNumber.getText(), String.format(
                        "%s %s %s %s " +
                                "%s " +
                                "%s", houseNumber.getText(),
                        street.getText(),
                        ward.getText(),
                        district.getText(),
                        city.getText(),
                        province.getText()).trim().replaceAll(" +", " "));
                notifyObserver();
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void notifyObserver() {
        Singleton.getInstance().getObserver().changeCustomer(realRow, newCustomer);
    }

    @Override
    public void show() {
        Scene scene = new Scene(getPane());
        stage.setTitle("Editing");
        stage.setScene(scene);
        stage.show();
    }
}
