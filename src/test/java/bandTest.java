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

  @Test
  public void save_savesInstanceToDBWithId_True() {
    Band newBand = new Band("Kansas");
    newBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(newBand.getId(), savedBand.getId());
  }

  @Test
  public void find_returnsCorrectInstanceOfBand_True() {
    Band newBand = new Band("Rock");
    newBand.save();
    Band foundBand = Band.find(newBand.getId());
    assertTrue(newBand.equals(foundBand));
  }

  @Test
  public void remove_removesInstanceOfBand_True() {
    Band newBand = new Band("Band 1");
    newBand.save();
    newBand.removeBand();
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void update_updatesBandName() {
    Band newBand = new Band("Band 2");
    newBand.save();
    newBand.update("Band 3");
    assertEquals("Band 3", Band.find(newBand.getId()).getBandName());
  }
}
