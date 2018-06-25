package Club;

import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author irondini
 */
public class Club implements Serializable {

    private String name;
    private List<Member> members;
    private List<Lesson> lessons;

    public Club(String name) {
        this.name = name;
        this.members = new ArrayList<Member>();
        this.lessons = new LinkedList<Lesson>();
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessonsByType(String type) {
        return this.lessons.stream().filter(x -> x.getType().equals(type)).collect(toList());

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
     * @return the members
     */
    public List<Member> getMembers() {
        return members;
    }

    public Optional<Member> getMemberByName(String name) {
        return this.members.stream().filter(x -> x.getName().equals(name)).findAny();
    }

    /**
     * @return the lessons
     */
    public List<Lesson> getLessons() {
        return this.lessons;
    }

    public void generateReport(LocalDate reportDate) {
        LocalDate today = LocalDate.now();

        List<Lesson> coursList = this.getLessons().stream().filter(x -> x.getPresentMembersList().size() > 0).filter(x -> today.getYear() == (LocalDate.parse(x.getStartDate())).now().getYear()).collect(toList());
        //Report with all prescences
        generatePresencesReport(coursList);
        //generate percentages for each member
        generatePercentageReport(coursList);

    }

    public void generatePresencesReport(List<Lesson> coursList) {

        PrintWriter writer;
        try {
            writer = new PrintWriter(LocalDate.now().toString() + "_Presences.txt", "UTF-8");

            for (Lesson cours : coursList) {
                String newCours = cours.getStartDate();
                newCours += ":" + cours.getType() + ":" + cours.getPlace() + "\n";
                for (Member m : cours.getPresentMembersList()) {
                    newCours += m.getName() + ",";
                }
                newCours += "\n";
                writer.println(newCours);
            }
            writer.close();
        } catch (Exception e) {

        }

    }

    public void generatePercentageReport(List<Lesson> coursList) {
        int size = coursList.size();

        PrintWriter writer;
        try {

            writer = new PrintWriter(LocalDate.now().toString() + "_Percentage.txt", "UTF-8");
            writer.print("Membre\t\t");
            for (Lesson.Type coursType : Lesson.getTypes()) {
                writer.print(coursType.toString() + "\t\t");
            }
            writer.print("\n");

            for (Member m : this.getMembers()) {
                writer.print(m.getName());
                for (Lesson.Type coursType : Lesson.getTypes()) {
                    List<Lesson> L = coursList.stream().filter(x -> x.getType().equals(coursType.toString())).collect(toList());
                    size = L.size();
                    Long present = L.stream().filter(x -> x.getPresentMembersList().contains(m)).count();
                    Double percentage = 1.0 * present / size;
                    writer.print("\t\t" + percentage.toString());
                }
                writer.print("\n");

            }
            writer.close();
        } catch (Exception e) {
        }
    }
}
