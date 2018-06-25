package Club;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author irondini
 */
public class RankingChange implements Serializable {

    public enum Ranking {
        KYU1 {
            public String toString() {
                return "1 Kyu";
            }
        },
        KYU2 {
            public String toString() {
                return "2 Kyu";
            }
        },
        KYU3 {
            public String toString() {
                return "3 Kyu";
            }
        },
        KYU4 {
            public String toString() {
                return "4 Kyu";
            }
        },
        KYU5 {
            public String toString() {
                return "5 Kyu";
            }
        },
        KYU6 {
            public String toString() {
                return "6 Kyu";
            }
        },
        DAN1 {
            public String toString() {
                return "1 Dan";
            }
        },
        DAN2 {
            public String toString() {
                return "2 Dan";
            }
        },
        DAN3 {
            public String toString() {
                return "3 Dan";
            }
        },
        DAN4 {
            public String toString() {
                return "4 Dan";
            }
        },
        DAN5 {
            public String toString() {
                return "5 Dan";
            }
        },
        DAN6 {
            public String toString() {
                return "6 Dan";
            }
        },
        DAN7 {
            public String toString() {
                return "7 Dan";
            }
        },
        DAN8 {
            public String toString() {
                return "8 Dan";
            }
        },
        DAN9 {
            public String toString() {
                return "9 Dan";
            }
        },
        DAN10 {
            public String toString() {
                return "10 Dan";
            }
        }
    };

    public static List<Ranking> getAllRankings() {

        return new ArrayList<Ranking>(EnumSet.allOf(Ranking.class));
    }

    private String date;
    private String ranking;

    public RankingChange(String date, String ranking) {
        System.out.println("Create ranking change:" + date + ";" + ranking);
        this.date = date;
        this.ranking = ranking;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public String getRanking() {
        return this.ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
