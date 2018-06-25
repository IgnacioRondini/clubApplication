/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import java.net.URL;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Club.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import javafx.application.Application;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author irondini
 */
public class CoursController extends BaseController implements Initializable {

    @FXML
    DatePicker startDateDatePicker, endDateDatePicker;
    @FXML
    TextField placeTextField;
    @FXML
    CheckBox NonMandatoryCheckBox;
    @FXML
    Button endButton, updateButton;
    @FXML
    AnchorPane AP;
    @FXML
    Pane dataPane;
    @FXML
    MenuButton typeMenuButton;

    @Override
    public void initializeRetrieve() {
        endButton.setText("Consulter présences");

        Lesson cours = (Lesson) ApplicationMain.map.get("cours");
        if (!cours.getStartDate().isEmpty()) {
            startDateDatePicker.setValue(LocalDate.parse(cours.getStartDate()));
        }

        if (!cours.getEndDate().isEmpty()) {
            endDateDatePicker.setValue(LocalDate.parse(cours.getEndDate()));
        }
        placeTextField.setText(cours.getPlace());
        typeMenuButton.setText(cours.getType());
        NonMandatoryCheckBox.setSelected(!cours.getMandatory());

        //Disabled all input text
        dataPane.getChildren().
                stream().
                filter(x -> (x instanceof TextField) || (x instanceof CheckBox) || (x instanceof DatePicker) || (x instanceof MenuButton)).
                forEach(x -> x.setDisable(true));
    }

    @Override
    public void initializeUpdate() {
        updateButton.setVisible(false);
        endButton.setText("Modifier présences");
        loadMenuType();

        Lesson cours = (Lesson) ApplicationMain.map.get("cours");
        if (!cours.getStartDate().isEmpty()) {
            startDateDatePicker.setValue(LocalDate.parse(cours.getStartDate()));
        }

        if (!cours.getEndDate().isEmpty()) {
            endDateDatePicker.setValue(LocalDate.parse(cours.getEndDate()));
        }
        placeTextField.setText(cours.getPlace());
        typeMenuButton.setText(cours.getType());
        NonMandatoryCheckBox.setSelected(!cours.getMandatory());

        //Disabled all input text
        dataPane.getChildren().
                stream().
                filter(x -> (x instanceof TextField) || (x instanceof CheckBox) || (x instanceof DatePicker) || (x instanceof MenuButton)).
                forEach(x -> x.setDisable(false));
        this.currentCRUDL = CRUDL.UPDATE;
    }

    @Override
    public void initializeCreate() {
        updateButton.setVisible(false);

        placeTextField.setText("Default place");
        loadMenuType();

        endButton.setText("Ajouter présences");
    }

    public void loadMenuType() {

        typeMenuButton.getItems().clear();

        for (Lesson.Type t : Lesson.getTypes()) {
            MenuItem item = new MenuItem(t.toString());
            item.setOnAction(a -> {
                typeMenuButton.setText(((MenuItem) a.getSource()).getText());
                ApplicationMain.map.put("typeSelected", Boolean.TRUE);
            });

            typeMenuButton.getItems().add(item);
        }
        /*
         while (it.hasNext()){
     MenuItem item = new MenuItem(it.next());
     item.setOnAction(a->{ //lambda expression
        //..code logic here for each extension
     });
     format.getItems().add(item);
 }
         */
    }

    public void endTransaction() {
        switch (this.currentCRUDL) {
            case CREATE:
                createCours();
                break;
            case RETRIEVE:
                retrieveCours();
                break;
            case UPDATE:
                updateCours();
                break;
            case DELETE:
                deleteCours();
                break;
            default:
                return;
        }
    }

    public void createCours() {

        String startDate = startDateDatePicker.getValue() == null ? LocalDate.now().toString() : startDateDatePicker.getValue().toString();
        String endDate = endDateDatePicker.getValue() == null ? LocalDate.now().toString() : endDateDatePicker.getValue().toString();

        String place = placeTextField.getText();
        if (place.isEmpty()) {
            place = "Blankedelle";
        }
        String type;
        Boolean Selected = (Boolean) ApplicationMain.map.get("typeSelected");
        //BAD IDEA
        if (Selected != null && Selected.equals(true) && !typeMenuButton.getText().equals("Choisir")) {
            type = typeMenuButton.getText();
        } else {
            type = "NORMAL";
        }
        boolean mandatory = !NonMandatoryCheckBox.isSelected();

        Lesson newLesson = new Lesson(startDate, endDate, type, mandatory);
        newLesson.setPlace(place);
        ApplicationMain.map.put("cours", newLesson);
        this.changeScene("MembersPresent.fxml", AP, this.currentCRUDL);

    }

    public void retrieveCours() {
        //  ApplicationMain.map.remove("cours");
        this.changeScene("MembersPresent.fxml", AP, this.currentCRUDL);
    }

    public void updateCours() {

        String startDate = startDateDatePicker.getValue() == null ? "" : startDateDatePicker.getValue().toString();
        String endDate = endDateDatePicker.getValue() == null ? "" : endDateDatePicker.getValue().toString();

        String place = placeTextField.getText();
        if (place.isEmpty()) {
            place = "Blankedelle";
        }
        String type;
        Boolean Selected = (Boolean) ApplicationMain.map.get("typeSelected");
        //BAD IDEA
        if (Selected != null && Selected.equals(true) && !typeMenuButton.getText().equals("Choisir")) {
            type = typeMenuButton.getText();
        } else {
            type = "NORMAL";
        }
        boolean mandatory = !NonMandatoryCheckBox.isSelected();

        Lesson cours = (Lesson) ApplicationMain.map.get("cours");
        ApplicationMain.map.put("oldCours", cours.clone());
        cours.setStartDate(startDate);
        cours.setEndDate(endDate);
        cours.setPlace(place);
        cours.setType(type);
        cours.setMandatory(mandatory);

        //ApplicationMain.map.remove("cours");
        this.changeScene("MembersPresent.fxml", AP, this.currentCRUDL);
    }

    public void deleteCours() {
    }

    public void requestedMenu() {
        System.out.println("requested MENU");
    }

    public void cancel() {

        this.changeScene("FXMLDocument.fxml", AP, CRUDL.LIST);
    }

}
