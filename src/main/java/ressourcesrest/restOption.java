package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {

   public static OptionBusiness optB = new OptionBusiness();

   // 1) POST /options
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response addOption(Option option) {
      if (option != null && optB.addOption(option)) {
         return Response.status(200).build();
      }
      return Response.status(404).build();
   }

   // 2) GET /options   et   3) GET /options?domaine=Mathématiques
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptions(@QueryParam("domaine") String domaine) {
      List<Option> l;
      if (domaine == null || domaine.isEmpty()) {
         l = optB.getListeOptions();
      } else {
         l = optB.getOptionsByDomaine(domaine);
      }
      return Response.status(200).entity(l).build();
   }

   // 6) GET /options/1
   @GET
   @Path("{code}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptionByCode(@PathParam("code") int code) {
      Option option = optB.getOptionByCode(code);
      if (option == null) {
         return Response.status(404).build();
      }
      return Response.status(200).entity(option).build();
   }

   // 4) DELETE /options/2
   @DELETE
   @Path("{code}")
   public Response deleteOption(@PathParam("code") int code) {
      if (optB.deleteOption(code)) {
         return Response.noContent().build();
      }
      return Response.status(404).build();
   }

   // 5) PUT /options/1
   @PUT
   @Path("{code}")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateOption(@PathParam("code") int code, Option option) {
      if (option != null && optB.updateOption(code, option)) {
         return Response.status(200).build();
      }
      return Response.status(404).build();
   }
}