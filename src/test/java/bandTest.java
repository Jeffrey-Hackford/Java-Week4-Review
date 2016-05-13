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

  @Test
  public void Band_returnsNameOfBand_String() {
    Band newBand = new Band("Journey");
    assertEquals("Journey", newBand.getBandName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBothNamesAreTheSame_True() {
    Band firstBand = new Band("REO");
    Band secondBand = new Band("REO");

    assertTrue(firstBand.equals(secondBand));
  }



}
