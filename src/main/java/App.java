import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      String venue = request.queryParams("inputtedVenue");
      model.put("band", band);
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id", (request, response) -> {
      String inputtedBandName = request.queryParams("inputtedBandName");
      Band newBand = new Band(inputtedBandName);
      newBand.save();
      response.redirect("/bands/" + newBand.getId());
      return null;
    });

    post("/bands/:id/update", (request, response) -> {
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
String newName = request.queryParams("update");
      band.updateBand(newName);
      response.redirect("/bands/" + band.getId());
      return null;
    });

    post("/bands/:id/delete", (request, response) -> {
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
      band.removeBand();
      response.redirect("/bands");
      return null;
    });

    post("/bands/:id/venue/new", (request, response) -> {
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      String venueName = request.queryParams("inputtedVenue");
      List<Venue> allVenues = Venue.all();
      Venue newVenue = new Venue(venueName);
      boolean repeatVenue = false;
      for(Venue listedVenue : allVenues) {
        if (newVenue.getVenueName().equals(listedVenue.getVenueName())) {
          band.addVenue(listedVenue);
          repeatVenue = true;
          break;
        }
      }
      if (repeatVenue == false) {
        newVenue.save();
        band.addVenue(newVenue);
      }
      response.redirect("/bands/" + band.getId());
      return null;
    });

    get("/venues", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request, reponse) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", venue);
      model.put("bands", venue.getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
