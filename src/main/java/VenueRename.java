import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

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
      return con.createQuery(sql)
      .executeAndFetch(Venue.class);
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

  public void addBand(Band band) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (venue_id, band_id) VALUES (:venue_id, :band_id)";
      con.createQuery(sql)
        .addParameter("venue_id", this.getId())
        .addParameter("band_id", band.getId())
        .executeUpdate();
    }
  }

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> bandIds = con.createQuery(joinQuery)
        .addParameter("venue_id", this.getId())
        .executeAndFetch(Integer.class);
      List<Band> bands = new ArrayList<Band>();
      for (Integer bandId : bandIds) {
        String bandQuery = "SELECT * FROM bands WHERE id = :bandId";
        Band band = con.createQuery(bandQuery)
        .addParameter("bandId", bandId)
        .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }

  public void removeVenue() {
    String sql = "DELETE FROM venues WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void update(String newVenueName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET venueName = :venueName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("venueName", newVenueName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
