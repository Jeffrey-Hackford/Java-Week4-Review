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

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object venue) {
    if (!(venue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) venue;
      return this.getVenueName().equals(newVenue.getVenueName()) &&
             this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(venueName) VALUES (:venueName)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("venueName", this.venueName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public void removeVenue() {
    String sql2 = "DELETE FROM venues WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql2)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
