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
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import Club.*;
import java.time.LocalDate;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author irondini
 */
public class RankingHistoryController extends BaseController implements Initializable {

    @FXML
    MenuButton rankingMenuButton;
    @FXML
    DatePicker rankingDatePicker;
    @FXML
    AnchorPane AP;

    @Override
    public void initializeCreate() {

        rankingMenuButton.setText("Choisir grade");
        loadRankingButtonMenu();
    }

    @Override
    public void initializeUpdate() {
        RankingChange rankingChange = (RankingChange) ApplicationMain.map.get("rankingChange");
        loadRankingButtonMenu();
        rankingMenuButton.setText(rankingChange.getRanking());
        rankingDatePicker.setValue(LocalDate.parse(rankingChange.getDate()));
    }

    public void loadRankingButtonMenu() {
        rankingMenuButton.getItems().clear();
        for (RankingChange.Ranking r : RankingChange.getAllRankings()) {

            MenuItem item = new MenuItem(r.toString());
            item.setOnAction(a -> {
                rankingMenuButton.setText(((MenuItem) a.getSource()).getText());
            });

            rankingMenuButton.getItems().add(item);
        }
    }

    @Override
    public void endCreate() {
        String date = rankingDatePicker.getValue().toString();
        String ranking = rankingMenuButton.getText();

        Member m = (Member) ApplicationMain.map.get("member");
        m.addRankingChange(date, ranking);
        changeScene("Member.fxml", AP, CRUDL.RETRIEVE);
    }

    @Override
    public void endUpdate() {
        String date = rankingDatePicker.getValue().toString();
        String ranking = rankingMenuButton.getText();
        RankingChange rankingChange = (RankingChange) ApplicationMain.map.get("rankingChange");
        rankingChange.setDate(date);
        rankingChange.setRanking(ranking);
        ApplicationMain.map.remove("rankingChange");
        changeScene("Member.fxml", AP, CRUDL.RETRIEVE);
    }
}
