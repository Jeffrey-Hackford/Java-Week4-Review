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
}
