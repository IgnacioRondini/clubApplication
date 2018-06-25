package Club;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author irondini
 */
public class Lesson implements Serializable, Cloneable {

    public enum Type {
        NORMAL, TYPE1, TYPE2, TYPE3, TYPE4, TYPE5
    };

    public static List<Type> getTypes() {
        return new ArrayList<Type>(EnumSet.allOf(Type.class));
    }

    private String startDate, endDate;
    private String place;
    private Boolean mandatory;
    private String type;
    private List<Member> presentMembersList = new ArrayList();

    public Lesson(String startDate, String type, Boolean mandatory) {
        this.startDate = startDate;
        this.endDate = startDate;
        this.type = type;
        this.mandatory = mandatory;
    }

    public Lesson(String startDate, String endDate, String type, Boolean mandatory) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.mandatory = mandatory;
    }

    public Lesson(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.mandatory = false;
        this.type = Type.NORMAL.toString();
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @return the mandatory
     */
    public Boolean getMandatory() {
        return mandatory;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @param mandatory the mandatory to set
     */
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the presentMembersList
     */
    public List<Member> getPresentMembersList() {
        return presentMembersList;
    }

    public void addMember(Member m) {
        this.presentMembersList.add(m);
    }

    public boolean isMemberPresent(Member m) {
        return this.presentMembersList.contains(m);
    }

    public boolean removeMember(Member m) {
        return this.presentMembersList.remove(m);
    }

    public SimpleBooleanProperty mandatoryProperty() {
        return new SimpleBooleanProperty(this.mandatory);
    }

    public Lesson clone() {
        Lesson clonedLesson = new Lesson(this.startDate, this.endDate);
        clonedLesson.setPlace(this.place);
        clonedLesson.setMandatory(this.mandatory);
        for (Member m : this.presentMembersList) {
            clonedLesson.addMember(m);
        }
        return clonedLesson;
    }
}
