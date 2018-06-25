/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import Club.Member;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author irondini
 */
public class FXMLDocumentController extends BaseController implements Initializable {

    @FXML
    private Label label, clubNameLabel;
    @FXML
    private AnchorPane AP;
    @FXML
    private Button clubButton, reportButton;
    @FXML
    private TableColumn integrantNameColumn, integrantRankingColumn, integrantMedicalColumn, integrantLicenceColumn,
            startDateCoursColumn, placeCoursColumn, typeCoursColumn;
    @FXML
    private TableView memberTableView, coursTableView;
    @FXML
    private Pane integrantCoursPane;
    @FXML
    private LineChart presenceChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url,
                    rb);
        } catch (Exception e) {

            this.currentCRUDL = CRUDL.LIST;
        }

        if (!this.currentCRUDL.equals(CRUDL.CREATE)) {

            presenceChart.setVisible(true);

            clubNameLabel.setText(ApplicationMain.myClub.getName());
            clubButton.setText("Modify club");
            integrantCoursPane.setVisible(true);
            final ObservableList<Member> data = FXCollections.observableArrayList(ApplicationMain.myClub.getMembers());
            integrantNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            integrantRankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
            integrantMedicalColumn.setCellValueFactory(new PropertyValueFactory<>("medicalDate"));
            integrantLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("licenceDate"));

            memberTableView.setItems(data);

            final ObservableList<Club.Lesson> dataCours = FXCollections.observableArrayList(ApplicationMain.myClub.getLessons()).sorted((x, y) -> y.getStartDate().compareTo(x.getStartDate()));
            startDateCoursColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            placeCoursColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
            typeCoursColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            coursTableView.setItems(dataCours);

            XYChart.Series<String, Number> series = new XYChart.Series();
            presenceChart.setAnimated(false);

            try {
                // Add the number of present person to the data
                ApplicationMain.myClub.getLessons().stream().filter(x -> x.getPresentMembersList().size() > 0).forEach(x -> {
                    series.getData().add(new XYChart.Data(x.getStartDate(), x.getPresentMembersList().size()));
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            presenceChart.getData().add(series);

        } else {
            integrantCoursPane.setVisible(false);
            reportButton.setVisible(false);
            clubButton.setText("Create club");
            presenceChart.setVisible(false);

        }

    }

    @FXML
    public void clubButtonAction(Event e) {

        CRUDL crudl = this.currentCRUDL == CRUDL.CREATE ? CRUDL.CREATE : CRUDL.UPDATE;

        this.changeScene("Club.fxml", clubButton, crudl);
    }

    @FXML
    public void createMember(Event e) {
        CRUDL crudl = CRUDL.CREATE;
        this.changeScene("Member.fxml", AP, crudl);
    }

    @FXML
    public void consultMember(Event e) {
        Member member = (Member) memberTableView.getSelectionModel().selectedItemProperty().get();
        if (member != null) {
            ApplicationMain.map.put("member", member);
            this.changeScene("Member.fxml", AP, CRUDL.RETRIEVE);
        }
    }

    @FXML
    public void createCours(Event e) {
        this.changeScene("Cours.fxml", AP, CRUDL.CREATE);
    }

    @FXML
    public void consultCours(Event e) {
        Club.Lesson newLesson = (Club.Lesson) coursTableView.getSelectionModel().selectedItemProperty().get();
        if (newLesson != null) {
            ApplicationMain.map.put("cours", newLesson);
            this.changeScene("Cours.fxml", AP, CRUDL.RETRIEVE);
        }
    }

    @FXML
    public void CheckBoxTest() {
        this.changeScene("MembersPresent.fxml", AP, currentCRUDL);
    }

    public void createReport() {
        ApplicationMain.myClub.generateReport(LocalDate.now());
    }
}
