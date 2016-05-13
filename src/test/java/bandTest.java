import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BandTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly() {
    Band newBand = new Band("Styx");
    assertEquals(true, newBand instanceof Band);
  }

}
