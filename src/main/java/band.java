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

  


}
