import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void venue_instantiatesCorrectly() {
    Venue newVenue = new Venue("Venue 1");
    assertEquals(true, newVenue instanceof Venue);
  }

  @Test
  public void venue_returnsNameOfInstance() {
    Venue newVenue = new Venue("Venue 2");
    assertEquals("Venue 2", newVenue.getVenueName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBothNamesAreTheSame_True() {
    Venue firstVenue = new Venue("Venue 1");
    Venue secondVenue = new Venue("Venue 1");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesInstanceToDB_true() {
    Venue newVenue = new Venue("Venue 3");
    newVenue.save();
    assertEquals(Venue.all().size(), 1);
  }
}
