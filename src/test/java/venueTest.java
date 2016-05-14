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

  @Test
  public void save_savesInstanceToDBWithId_True() {
    Venue newVenue = new Venue("Venue 4");
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_returnsCorrectInstanceOfVenue_True() {
    Venue newVenue = new Venue("Venue 5");
    newVenue.save();
    Venue foundVenue = Venue.find(newVenue.getId());
    assertTrue(newVenue.equals(foundVenue));
  }

  @Test
  public void remove_deletesInstanceFromDB() {
    Venue newVenue = new Venue("Venue 6");
    newVenue.save();
    newVenue.removeVenue();
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void update_updatesVenueName() {
    Venue newVenue = new Venue("Venue 7");
    newVenue.save();
    newVenue.update("Venue 8");
    assertEquals("Venue 8", Venue.find(newVenue.getId()).getVenueName());
  }
}
