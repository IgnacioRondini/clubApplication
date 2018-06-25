/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import Club.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import Club.*;
import java.util.Optional;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author irondini
 */
public class MembersPresentController extends BaseController implements Initializable {

    @FXML
    ListView TestListView;
    @FXML
    private Pane membersPane;
    @FXML
    private TableColumn<Member, Boolean> checkBoxCol;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableView testTableView;
    @FXML
    private Label startDateLabel, endDateLabel, placeLabel, typeLabel, mandatoryLabel;
    @FXML
    private CheckBox mandatoryCheckBox;
    @FXML
    private AnchorPane AP;
    @FXML
    private Button endButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //init labels
        this.loadLabels();

        //init List
        this.loadMemberList();

        //execute corresponding crudl init
        super.initialize(url,
                rb);

    }

    public void loadLabels() {

        String endDate, place, type;

        Lesson cours = (Lesson) ApplicationMain.map.get("cours");
        endDate = cours.getEndDate() == null ? "" : cours.getEndDate();
        place = cours.getPlace() == null ? "" : cours.getPlace();
        type = cours.getType() == null ? "" : cours.getType();

        startDateLabel.setText("Date debut: " + cours.getStartDate());
        endDateLabel.setText("Date fin: " + endDate);
        placeLabel.setText("Endroit: " + place);
        typeLabel.setText("Type de cours: " + type);
        mandatoryLabel.setText("Cours obligatoire?: ");
        mandatoryCheckBox.setSelected(cours.getMandatory());
        mandatoryCheckBox.setDisable(true);

    }

    public void loadMemberList() {
        final ObservableList<Member> members = FXCollections.observableArrayList(ApplicationMain.myClub.getMembers());
        testTableView.setEditable(true);
        checkBoxCol.setCellFactory(
                CheckBoxTableCell.forTableColumn(checkBoxCol)
        );

        checkBoxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Member, Boolean>, ObservableValue<Boolean>>() {//This callback tell the cell how to bind the data model 'Registered' property to
            //the cell, itself.

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Member, Boolean> param) {
                return param.getValue().isChecked();
            }

        }
        );

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        testTableView.setItems(members);
    }

    @Override
    public void initializeRetrieve() {
        //Fill in checks
        for (Member m : ((Lesson) ApplicationMain.map.get("cours")).getPresentMembersList()) {
            m.setChecked(new SimpleBooleanProperty(true));
        }
        // Protect
        membersPane.setDisable(true);

    }

    @Override
    public void initializeUpdate() {
        //Fill in checks
        for (Member m : ((Lesson) ApplicationMain.map.get("cours")).getPresentMembersList()) {
            m.setChecked(new SimpleBooleanProperty(true));
        }
    }

    @Override
    public void endCreate() {

        ObservableList<TableColumn> columns = testTableView.getColumns();

        Lesson cours = (Lesson) ApplicationMain.map.get("cours");

        for (Member m : ApplicationMain.myClub.getMembers()) {
            if (m.isChecked() != null && m.isChecked().getValue()) {
                cours.addMember(m);
                m.setChecked(new SimpleBooleanProperty(false));
            }
        }
        ApplicationMain.myClub.addLesson(cours);

        this.changeScene("FXMLDocument.fxml", AP, CRUDL.LIST);
    }

    @Override
    public void endRetrieve() {
        //Remove from map
        ApplicationMain.map.remove("cours");
        this.changeScene("FXMLDocument.fxml", AP, CRUDL.LIST);
    }

    @Override
    public void endUpdate() {

        ObservableList<TableColumn> columns = testTableView.getColumns();
        Lesson cours = (Lesson) ApplicationMain.map.get("cours");

        for (Member m : ApplicationMain.myClub.getMembers()) {
            if (m.isChecked() != null && m.isChecked().getValue()) {
                cours.addMember(m);
                m.setChecked(new SimpleBooleanProperty(false));
            }
        }

        this.changeScene("FXMLDocument.fxml", AP, CRUDL.LIST);

    }

    public void cancel() {
        this.changeScene("FXMLDocument.fxml", AP, CRUDL.LIST);
    }
}
