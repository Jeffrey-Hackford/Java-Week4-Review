import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;


public class Band {
  private int id;
  private String bandName;

  public Band(String bandName){
    this.bandName = bandName;
  }

  public int getId() {
    return id;
  }

  public String getBandName() {
    return bandName;
  }

  public static List<Band> all() {
   String sql = "SELECT * FROM bands";
   try (Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Band.class);
   }
 }

  @Override
  public boolean equals(Object band) {
    if (!(band instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) band;
      return this.getBandName().equals(newBand.getBandName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(bandName) VALUES (:bandName)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("bandName", this.bandName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
    }
  }

  public void addVenue(Venue passedInVenue) {
    // if(this.getVenues().contains(passedInVenue)); else {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
        con.createQuery(sql)
          .addParameter("band_id", this.getId())
          .addParameter("venue_id", passedInVenue.getId())
          .executeUpdate();
      }
    }
  }

  public List<Venue> getVenues() {
  try(Connection con = DB.sql2o.open()){
    String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
    List<Integer> venueIds = con.createQuery(joinQuery)
      .addParameter("band_id", this.getId())
      .executeAndFetch(Integer.class);
    List<Venue> venues = new ArrayList<Venue>();
    for (Integer venueId : venueIds) {
      String venueQuery = "SELECT * FROM venues WHERE id = :venueId";
      Venue venue = con.createQuery(venueQuery)
      .addParameter("venueId", venueId)
      .executeAndFetchFirst(Venue.class);
      venues.add(venue);
    }
    return venues;
  }
}

  public void removeBand() {
    String sql = "DELETE FROM bands WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
    String sql2 = "DELETE FROM bands_venues WHERE band_id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql2)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateBand(String newBandName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET bandName = :bandName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("bandName", newBandName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static Band findByName(String newBand) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE bandName = :bandName";
      return con.createQuery(sql)
        .addParameter("bandName", newBand)
        .executeAndFetchFirst(Band.class);
    }
  }
}
