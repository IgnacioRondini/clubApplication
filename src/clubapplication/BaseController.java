/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author irondini
 */
public abstract class BaseController implements Initializable {

    protected CRUDL currentCRUDL;

    public void initialize(URL url, ResourceBundle rb) {
        this.currentCRUDL = (CRUDL) ApplicationMain.map.get("CRUDL");

        switch (this.currentCRUDL) {
            case CREATE:
                initializeCreate();
                break;
            case RETRIEVE:
                initializeRetrieve();
                break;
            case UPDATE:
                initializeUpdate();
                break;
            case DELETE:
                initializeDelete();
                break;
            case LIST:
                initializeList();
                break;
            default: ;
                break;
        }

    }

    public void initializeCreate() {
    }

    public void initializeRetrieve() {
    }

    public void initializeUpdate() {
    }

    public void initializeDelete() {
    }

    public void initializeList() {
    }

    public void changeScene(String FXML, Region R, CRUDL crudl) {
        try {

            ApplicationMain.map.put("CRUDL", crudl);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) R.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            BaseController controller = loader.getController();
            //         controller.initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endTransaction() {
        switch (this.currentCRUDL) {
            case CREATE:
                endCreate();
                break;
            case RETRIEVE:
                endRetrieve();
                break;
            case UPDATE:
                endUpdate();
                break;
            case DELETE:
                endDelete();
                break;
            default:
                return;
        }
    }

    public void endCreate() {;
    }

    public void endRetrieve() {;
    }

    public void endUpdate() {;
    }

    public void endDelete() {;
    }
}
