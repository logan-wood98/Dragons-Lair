import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class NewOrderController {

    private Connection conn;
    private int customerId;

    @FXML private Button addOrderButton;
    @FXML private ComboBox<Title> setTitle;
    @FXML private TextField setQuantity;
    @FXML private TextField setIssue;

    private ObservableList<Title> titles  = FXCollections.observableArrayList();


    @FXML
    void newOrder(ActionEvent event) {

        PreparedStatement s = null;
        String sql = "INSERT INTO Orders (customerId, titleId, quantity, issue) VALUES (?, ?, ?, ?)";

        int titleID = getChoice(setTitle);
        String Issue = setIssue.getText();
        String Quantity = setQuantity.getText();
        int customerId = this.customerId;

        try
        {
            s = conn.prepareStatement(sql);
            s.setString(1, Integer.toString(customerId));
            s.setString(2, Integer.toString(titleID));
            s.setString(3, Quantity);
            s.setString(4, Issue);


            int rowsAffected = s.executeUpdate();

            if (rowsAffected == 0) {
                //TODO: Throw an error
            }
            else if (rowsAffected > 1) {
                //TODO: Throw an error
            }
            s.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        Stage window = (Stage) addOrderButton.getScene().getWindow();
        window.close();
    }

    public void setNewOrder(){
        setTitle.setItems(this.titles);
        setTitle.getSelectionModel().selectFirst();

    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    // for the main controller to pass the current selected customer
    public void setCustomerID(int id) {
        this.customerId = id;
    }

    // Get a list of all available titles (used by Controller.j)
    public void populate(ObservableList<Title> getTitles){
        this.titles = getTitles;
    }

    //To pick an option from the drop down menu
    public int getChoice(ComboBox<Title> setTitle ){
        return setTitle.getValue().getId();
    }
}