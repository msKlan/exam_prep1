package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;

import entities.Hobby;
import entities.Person;

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

@OpenAPIDefinition(
        info = @Info(
                title = "Demo on using JPA, Rest and OpenApi",
                version = "0.1",
                description = "This is the main ressource Person that has relationships to Clup and Address",
                contact = @Contact(name = "......", email = "...........")
        ),
        tags = {
            @Tag(name = "person", description = "API related to person")
        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/exam_prep1/"
            ),
            @Server(
                    description = "Server API",
                    url = "https://aieou.dk/exam_prep1"
            )

        }
)
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/exam_prep1",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Path("/setup")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSetup() {
        EntityManager em = EMF.createEntityManager();

        Address a1 = new Address("Gottersgade 10", "st. tv.");

        

        Hobby h1 = new Hobby("Football", "Sport");
        
        Person p1 = new Person("Hans", "Hansen", "hans@hansen.com");
        
        h1.setPerson(p1);
        p1.setHobby(h1);
        
        p1.setAddress(a1);
        

        

        Address a2 = new Address("Øresundsvej 1", "4. th.");

        

        
        Person p2 = new Person("Jens", "Jensen", "jens@jensen.com");
       
        h1.setPerson(p2);
        p2.setHobby(h1);
        
        p2.setAddress(a2);
        

        

        Address a3 = new Address("Lyngbyvej 10", "1 . tv.");

        

        Hobby h3 = new Hobby("Coins", "Collection");
        
        Person p3 = new Person("Simon", "Snow", "simon@snow.com");
        
        h3.setPerson(p3);
        p3.setHobby(h3);
        
        p3.setAddress(a3);
        

        
        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return Response.ok().entity(GSON.toJson("Setup Complete")).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Operation(summary = "Get all person",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Requested person"),
                @ApiResponse(responseCode = "400", description = "Person not found")}
    )
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAll() {
        return FACADE.getAllPersons().getAll();
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getPersonByID(@PathParam("id") int id) {
        return FACADE.getPerson(id);
    }

//    @POST
//    @Path("/add")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public PersonDTO addPerson(String person) {
//        Person p = GSON.fromJson(person, Person.class);
//        PersonDTO pDTO = new PersonDTO(p);
//        return FACADE.addPerson(pDTO);
//    }
//
//    @PUT
//    @Path("/edit")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public PersonDTO editPerson(String person) {
//        Person p = GSON.fromJson(person, Person.class);
//        PersonDTO pDTO = new PersonDTO(p);
//        return FACADE.editPerson(pDTO);
//    }
//
//    @DELETE
//    @Path("/delete/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public PersonDTO deletePerson(@PathParam("id") int id) {
//        return FACADE.deletePerson(id);
//    }

}
