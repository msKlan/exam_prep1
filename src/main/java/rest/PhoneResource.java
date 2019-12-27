/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PhoneDTO;
import dto.PhonesDTO;
import entities.Phone;
import facades.PhoneFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author Klan
 */

@OpenAPIDefinition(
            info = @Info(
                    title = "Simple Phone API",
                    version = "0.1",
                    description = "Test and understand what you can do with the Phone api.",        
                    contact = @Contact( name = "Jacob Hildebrandt", email = "@cphbusiness.dk")
            ),
          tags = {
                    @Tag(name = "Phone", description = "API related to Phone")
              
            },
            servers = {
                    @Server(
                            description = "For Local host testing",
                            url = "http://localhost:8080/CA-2/"
                    ),
                    @Server(
                            description = "Server API",
                            url = "http://vincentcph.dk/CA-2/"
                    )
                          
            }
    )


@Path("phone")
public class PhoneResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA2",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PhoneFacade FACADE = PhoneFacade.getPhoneFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @GET
    @Path("setup")
    @Produces({MediaType.APPLICATION_JSON})
    public String setup() {

        EntityManager em = EMF.createEntityManager();
        
        Phone p1 = new Phone("123456", "Test1");
        Phone p2 = new Phone("654321", "Test2");
        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2); 
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
        
        return "{\"msg\":\"Setup done\"}";
    }
   
    @Operation(summary = "Get Phone by ID",
        tags = {"Phone"},
            responses = {
                @ApiResponse(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = PhoneDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The Requested Phone"),                       
                    @ApiResponse(responseCode = "400", description = "The requested Phone is not found")})
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PhoneDTO getPhoneByID(@PathParam("id") int id) {
        PhoneDTO p = FACADE.getPhoneID(id);
//        return Response.ok().entity(GSON.toJson(p)).build();
        return p;
    }
   
    @Operation(summary = "Get All Phones",
        tags = {"Phone"},
            responses = {
                @ApiResponse(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = PhonesDTO.class))),
                    @ApiResponse(responseCode = "200", description = "A list of all Phones"),                       
                    @ApiResponse(responseCode = "400", description = "Sonething went wrong")})

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PhoneDTO> getAllPhones() {
        return FACADE.getAllPhones().getAll();
//        List<Phone> ps = new ArrayList(FACADE.getAllPhones());
//        return Response.ok().entity(GSON.toJson(ps)).build();
    }
   
    @Operation(summary = "Add a Phone",
        tags = {"Phone"},
        responses = {
            @ApiResponse(
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = PhoneDTO.class))),
                @ApiResponse(responseCode = "200", description = "The added Phone now with an ID"),                       
                @ApiResponse(responseCode = "400", description = "Invalid input")})
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    public PhoneDTO addPhone(String phone) {
        Phone p = GSON.fromJson(phone, Phone.class);
        PhoneDTO pDTO = new PhoneDTO(p);
        FACADE.addPhone(pDTO);
        return pDTO;
//        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @Operation(summary = "Edit a Phone",
        tags = {"Phone"},
        responses = {
            @ApiResponse(
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = PhoneDTO.class))),
                @ApiResponse(responseCode = "200", description = "The edited Phones returned"),                       
                @ApiResponse(responseCode = "400", description = "Phone edited Phone not found")})
    @PUT
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public PhoneDTO editPhone(String phone) {
        Phone p = GSON.fromJson(phone, Phone.class);
        PhoneDTO pDTO = new PhoneDTO(p);
        FACADE.editPhone(pDTO);
        return pDTO;
//        return Response.ok().entity(GSON.toJson(p)).build();
    }
   
    @Operation(summary = "Delete a Phone",
        tags = {"Phone"},
        responses = {
            @ApiResponse(
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = PhoneDTO.class))),
                @ApiResponse(responseCode = "200", description = "The deletes Phone returned as confirmation"),                       
                @ApiResponse(responseCode = "400", description = "Phone not found")})
    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public PhoneDTO removePhone(@PathParam("id") int id) {
        return FACADE.removePhone(id);
    }
   
}