package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto_OUT.PersonDTO_OUT;
import dto.PersonsDTO;
import entities.Address;

import entities.Hobby;
import entities.Person;

import facades.GeneralFacade;
import utils.EMF_Creator;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("general")
public class GeneralResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV,
            EMF_Creator.Strategy.CREATE);
    private static final GeneralFacade FACADE = GeneralFacade.getGeneralFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all/hobby/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAllPersonByHobby(@PathParam("name") String name) {
        return FACADE.getAllPersonsByHobby(name).getAll();
    }

    @GET
    @Path("/all/city/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAllPersonByCity(@PathParam("name") String name) {
        return FACADE.getAllPersonsByCity(name).getAll();
    }

    @GET
    @Path("/all/phone/{phoneNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO_OUT> getAllPersonByPhone(@PathParam("phoneNumber") String phoneNumber) {
        return FACADE.getAllPersonsByPhone(phoneNumber).getAll();
    }

    @GET
    @Path("/all/zipcode")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllZipCodes() {
        return FACADE.getAllZipCodes();
    }

    @GET
    @Path("/count/{hobbyname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCount(@PathParam("hobbyname") String hobbyname) {
        long count = FACADE.getCountPersonByHobby(hobbyname);

        return Response.ok().entity(GSON.toJson(count)).build();
    }
}
