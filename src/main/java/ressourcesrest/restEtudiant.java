package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class restEtudiant {

    private static EtudiantBusiness etuB = new EtudiantBusiness();

    // 1) POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant != null && etudiant.getOption() != null && etuB.addEtudiant(etudiant)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    // 2) GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> l = etuB.getAllEtudiants();
        return Response.status(200).entity(l).build();
    }

    // 6) GET /etudiants/option?codeOption=1   (réponse XML)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = restOption.optB.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(404).build();
        }
        List<Etudiant> l = etuB.getEtudiantsByOption(option);
        return Response.status(200).entity(new EtudiantList(l)).build();
    }

    // 3) GET /etudiants/I003
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant e = etuB.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(e).build();
    }

    // 4) DELETE /etudiants/I003
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etuB.deleteEtudiant(id)) {
            return Response.noContent().build();
        }
        return Response.status(404).build();
    }

    // 5) PUT /etudiants/I001
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant != null && etuB.updateEtudiant(id, etudiant)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }
}