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

    get("/venues/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venue-form.vtl");
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
      model.put("band", band);
      model.put("venues", band.getVenues());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", venue);
      model.put("bands", venue.getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id", (request, response) -> {
      String inputtedBand = request.queryParams("inputtedBand");
      Band newBand = new Band(inputtedBand);
      newBand.save();
      response.redirect("/bands/" + newBand.getId());
      return null;
    });

    post("/venues/:id", (request, response) -> {
      String inputtedVenue = request.queryParams("inputtedVenue");
      Venue newVenue = new Venue(inputtedVenue);
      newVenue.save();
      response.redirect("/venues/" + newVenue.getId());
      return null;
    });

    post("/bands/:id/update", (request, response) -> {
      int bandId = Integer.parseInt(request.params("id"));
      Band band = Band.find(bandId);
      String newName = request.queryParams("updateBand");
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

    post("/venues/:id/band/new", (request, response) -> {
      Venue venue = Venue.find(Integer.parseInt(request.params(":id")));
      String inputtedBand = request.queryParams("inputtedBand");
      Band newBand = new Band(inputtedBand);

      Band bandToConnect = Band.findByName(inputtedBand);
        if (bandToConnect == null) {
          bandToConnect = new Band(inputtedBand);
          bandToConnect.save();
        }
        venue.addBand(bandToConnect);

      // boolean allreadyExists = false;
      // for (Band band : venue.getBands()) {
      //   if (newBand.getBandName().equals(band.getBandName())) {
      //     for(Band band2 : Band.all()) {
      //       if (newBand.getBandName().equals(band2.getBandName())) {
      //       allreadyExists = true;
      //       break;
      //       }
      //     }
      //   }
      // }
      // if (allreadyExists == false) {
      //   newBand.save();
      //   venue.addBand(newBand);
      // }
    response.redirect("/venues/" + venue.getId());
    return null;
    });

    post("/bands/:id/venue/new", (request, response) -> {
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      String venueName = request.queryParams("inputtedVenue");
      Venue newVenue = new Venue(venueName);


      Venue venueToConnect = Venue.findByName(venueName);
        if (venueToConnect == null) {
          venueToConnect = new Venue(venueName);
          venueToConnect.save();
        }
        band.addVenue(venueToConnect);


      // boolean allreadyExists = false;
      // for (Venue venue : band.getVenues()) {
      //   if (newVenue.getVenueName().equals(venue.getVenueName())) {
      //     for(Venue venue2 : Venue.all()) {
      //       if (newVenue.getVenueName().equals(venue2.getVenueName())) {
      //       allreadyExists = true;
      //       break;
      //       }
      //     }
      //   }
      // }
      // if (allreadyExists == false) {
      //   newVenue.save();
      //   band.addVenue(newVenue);
      // }

      // for (Venue venue : Venue.all()) {
      //
      //   if (newVenue.getVenueName().equals(venue.getVenueName())) {
      //     allreadyExists = true;
      //     break;
      //   }
      // }
      // if (allreadyExists == false) {
      //   newVenue.save();
      //   band.addVenue(newVenue);
      // }
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
