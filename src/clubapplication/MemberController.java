/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Club.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.AnchorPane;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author irondini
 */
public class MemberController extends BaseController implements Initializable {

    @FXML
    Label titleMember;
    @FXML
    TextField memberNameText, memberRankingText, memberMailText;
    @FXML
    DatePicker memberLicenceDatePicker, memberMedicalDatePicker;
    @FXML
    Button memberButton, updateMemberButton, createRankingButton, updateRankingButton;
    @FXML
    AnchorPane AP;
    @FXML
    TableView rankingDatesTableView;
    @FXML
    TableColumn rankingTableColumn, rankingDatesTableColumn;

    @Override
    public void initializeRetrieve() {
        this.createRankingButton.setVisible(true);
        this.updateRankingButton.setVisible(true);
        this.loadTableRankingHistory();
        this.titleMember.setText("Consulter un membre");
        updateMemberButton.setVisible(true);
        Member member = (Member) ApplicationMain.map.get("member");

        this.memberNameText.setText(member.getName());
        this.memberRankingText.setText(member.getRanking());
        this.memberMailText.setText(member.getEmail());
        if (member.getLicenceDate() != null && !member.getLicenceDate().isEmpty()) {
            this.memberLicenceDatePicker.setValue(LocalDate.parse(member.getLicenceDate()));
        }
        if (member.getMedicalDate() != null && !member.getMedicalDate().isEmpty()) {
            this.memberMedicalDatePicker.setValue(LocalDate.parse(member.getMedicalDate()));
        }

        //Disabled all input text
        AP.getChildren().stream().filter(x -> (x instanceof TextField) || (x instanceof DatePicker)).forEach(x -> x.setDisable(true));

    }

    @Override
    public void initializeCreate() {
        this.titleMember.setText("Créer un membre");
        this.createRankingButton.setVisible(false);
        this.updateRankingButton.setVisible(false);
        rankingDatesTableView.setVisible(false);
        updateMemberButton.setVisible(false);
    }

    @Override
    public void initializeUpdate() {
        this.loadTableRankingHistory();
        this.titleMember.setText("Modifier un membre");
        updateMemberButton.setVisible(false);
        this.createRankingButton.setVisible(false);
        this.updateRankingButton.setVisible(true);

        Member member = (Member) ApplicationMain.map.get("member");
        this.memberNameText.setText(member.getName());
        this.memberRankingText.setText(member.getRanking());
        this.memberMailText.setText(member.getEmail());
        if (!member.getLicenceDate().isEmpty()) {
            this.memberLicenceDatePicker.setValue(LocalDate.parse(member.getLicenceDate()));
        }
        //Disabled all input text
        AP.getChildren().stream().filter(x -> (x instanceof TextField) || (x instanceof DatePicker)).forEach(x -> x.setDisable(false));
        this.currentCRUDL = CRUDL.UPDATE;
    }

    public void memberButtonAction() {

        switch (this.currentCRUDL) {
            case CREATE:
                createMember();
                break;
            case RETRIEVE:
                retrieveMember();
                break;
            case UPDATE:
                updateMember();
                break;
            case DELETE:
                deleteMember();
                break;
            default:
                return;
        }
        return;
    }

    public void createMember() {

        String name = this.memberNameText.getText();
        String ranking = this.memberRankingText.getText();
        String email = this.memberMailText.getText();
        LocalDate licence = this.memberLicenceDatePicker.getValue();
        LocalDate medical = this.memberMedicalDatePicker.getValue();
        String licenceString = licence == null ? "" : licence.toString();
        String medicalString = medical == null ? "" : licence.toString();

        Member newMember = new Member(name);
        newMember.setRanking(ranking);
        newMember.setEmail(email);
        newMember.setLicenceDate(licenceString);
        newMember.setMedicalDate(medicalString);
        newMember.setChecked(new SimpleBooleanProperty(false));
        ApplicationMain.myClub.addMember(newMember);

        this.changeScene("FXMLDocument.fxml", titleMember, CRUDL.LIST);
    }

    public void retrieveMember() {
        ApplicationMain.map.remove("member");
        this.changeScene("FXMLDocument.fxml", titleMember, CRUDL.LIST);
    }

    public void updateMember() {
        String name = this.memberNameText.getText();
        String ranking = this.memberRankingText.getText();
        String email = this.memberMailText.getText();
        LocalDate licence = this.memberLicenceDatePicker.getValue();
        LocalDate medical = this.memberMedicalDatePicker.getValue();
        String licenceString = licence == null ? "" : licence.toString();
        String medicalString = medical == null ? "" : licence.toString();

        Member member = (Member) ApplicationMain.map.get("member");
        member.setName(name);
        member.setRanking(ranking);
        member.setEmail(email);
        member.setLicenceDate(licenceString);
        member.setMedicalDate(medicalString);

        ApplicationMain.map.remove("member");
        this.changeScene("FXMLDocument.fxml", titleMember, CRUDL.LIST);
    }

    public void deleteMember() {
    }

    public void loadTableRankingHistory() {

        //Get observable to put in the colum
        final ObservableList<RankingChange> rankings = FXCollections.observableArrayList();
        for (RankingChange r : ((Member) ApplicationMain.map.get("member")).getRankingHistory()) {
            rankings.add(r);

        }

        rankingTableColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        rankingDatesTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        rankingDatesTableView.setItems(rankings);

    }

    public void updateRankingHistory() {
        //put in map selected date
        RankingChange rankingChange = (RankingChange) rankingDatesTableView.getSelectionModel().selectedItemProperty().get();
        if (rankingChange != null) {
            ApplicationMain.map.put("rankingChange", rankingChange);
            this.changeScene("rankingHistory.fxml", AP, CRUDL.UPDATE);
        }
    }

    public void createRankingHistory() {
        //put in map selected date
        this.changeScene("rankingHistory.fxml", titleMember, CRUDL.CREATE);
    }

    public void cancel() {
        this.changeScene("FXMLDocument.fxml", titleMember, CRUDL.LIST);
    }
}
