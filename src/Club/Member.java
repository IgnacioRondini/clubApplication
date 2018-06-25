package Club;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author irondini
 */
public class Member implements Serializable {

    private String name, ranking, email, licenceDate, medicalDate;
    private final List<RankingChange> rankingHistory = new ArrayList<>();
    private transient SimpleBooleanProperty checked;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, String ranking) {
        this.name = name;
        this.ranking = ranking;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @param ranking the ranking to set
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the LicenceDate
     */
    public String getLicenceDate() {
        return licenceDate;
    }

    /**
     * @param licenceDate the licenceDate to set
     */
    public void setLicenceDate(String licenceDate) {
        this.licenceDate = licenceDate;
    }

    /**
     * @return the MedicalDate
     */
    public String getMedicalDate() {
        return medicalDate;
    }

    /**
     * @param medicalDate the medicalDate to set
     */
    public void setMedicalDate(String medicalDate) {
        this.medicalDate = medicalDate;
    }

    public SimpleBooleanProperty isChecked() {
        return this.checked;
    }

    public void setChecked(SimpleBooleanProperty c) {
        this.checked = c;
    }

    /**
     * @return the rankingHistory
     */
    public List<RankingChange> getRankingHistory() {
        return rankingHistory;
    }

    public void addRankingChange(String date, String ranking) {
        this.rankingHistory.add(new RankingChange(date, ranking));
    }
}
