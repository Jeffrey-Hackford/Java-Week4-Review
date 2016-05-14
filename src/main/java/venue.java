import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

public class Venue {
  private int id;
  private String venueName;

  public Venue(String venueName) {
    this.venueName = venueName;
  }

  public String getVenueName() {
    return venueName;
  }

  public static List<Venue> all() {
   String sql = "SELECT * FROM venues";
   try (Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Venue.class);
   }
 }

}
