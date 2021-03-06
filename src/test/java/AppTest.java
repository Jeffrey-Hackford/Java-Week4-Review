import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome");
  }

  @Test
  public void navigatesToBandForm() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Band"));
    assertThat(pageSource()).contains("Add A New Band");
  }

  @Test
  public void bandIsAddedSuccessfully() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Band"));
    fill("#inputtedBand").with("Kansas");
    submit(".btn", withText("Add"));
    assertThat(pageSource()).contains("Kansas");
  }

  @Test
  public void venueIsAddedToBand() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Band"));
    fill("#inputtedBand").with("Kansas");
    submit(".btn", withText("Add Band"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    assertThat(pageSource()).contains("Venue 1");
  }

  @Test
  public void venueIsAddedSuccessfully() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Venue"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    assertThat(pageSource()).contains("Venue 1");
  }

  @Test
  public void bandIsAddedToVenue() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Venue"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    assertThat(pageSource()).contains("Band 1");
  }

  @Test
  public void bandIsDeletedFromDB() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Band"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    submit(".btn", withText("Delete This Band"));
    assertThat(pageSource()).contains("Here is a list of all Bands:");
  }

  @Test
  public void venueCanNotBeAddedToBandTwice() {
    goTo("http://localhost:4567");
    click("a", withText("Add A New Band"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    assertEquals(Venue.all().size(), 1);
  }

  @Test
  public void bandCanNotBeAddedToVenueTwice() {
    goTo("http://localhost:4567");
    click("a", withText("Add A New Venue"));
    fill("#inputtedVenue").with("Venue 1");
    submit(".btn", withText("Add Venue"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    assertEquals(Band.all().size(), 1);
  }

  @Test
  public void bandNameUpdatedSuccessfully() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A New Band"));
    fill("#inputtedBand").with("Band 1");
    submit(".btn", withText("Add Band"));
    fill("#updateBand").with("Band 2");
    submit(".btn", withText("Update Band Name"));
    assertThat(pageSource()).contains("Band 2");
  }
}
