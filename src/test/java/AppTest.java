import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.junit.*;

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

}
