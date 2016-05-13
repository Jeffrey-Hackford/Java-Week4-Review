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




}
