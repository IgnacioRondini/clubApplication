/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import Club.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author irondini
 */
public class ClubController extends BaseController implements Initializable {

    @FXML
    private TextField clubNameTextField;
    @FXML
    private Button endClubButton;

    public void endClub() {

        switch (this.currentCRUDL) {
            case CREATE:
                createClub();
                break;
            case UPDATE:
                updateClub();
                break;
            default:
                return;
        }
        return;

    }

    public void updateClub() {
        String clubName = clubNameTextField.getText();
        ApplicationMain.myClub.setName(clubName);
        this.changeScene("FXMLDocument.fxml", endClubButton, CRUDL.LIST);
    }

    public void createClub() {
        String clubName = clubNameTextField.getText();
        ApplicationMain.myClub = new Club(clubName);

        this.changeScene("FXMLDocument.fxml", endClubButton, CRUDL.LIST);

    }

    public void close() {

    }

}
