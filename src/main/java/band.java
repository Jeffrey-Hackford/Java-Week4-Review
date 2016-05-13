import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

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




}
