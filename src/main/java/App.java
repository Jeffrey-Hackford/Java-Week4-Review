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

    get("/band/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("bandInput");
      Band newBand = new Band(name);
      newBand.save();
      Boolean addedNewBand = true;
      model.put("bands", Band.all());
      model.put("addedNewBand", addedNewBand);
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/band/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", band);
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //
    // post("band/:id", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   Band band = Band.find(Integer.parseInt(request.params(":id")));
    //   model.put("band", band);
    //   model.put("venues", Venue.all());
    //   model.put("template", "templates/band.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
