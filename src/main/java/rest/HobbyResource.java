/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HobbiesDTO;
import dto.HobbyDTO;
import entities.Hobby;
import facades.HobbyFacade;
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
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author jacobfolkehildebrandt
 */

@OpenAPIDefinition(
            info = @Info(
                    title = "Simple Hobby API",
                    version = "0.4",
                    description = "Simple API to get info about hobbies.",        
                    contact = @Contact( name = "Jacob Hildebrandt", email = "@cphbusiness.dk")
            ),
          tags = {
                    @Tag(name = "Hobby", description = "API related to Hobby")
              
            },
            servers = {
                    @Server(
                            description = "For Local host testing",
                            url = "http://localhost:8080/exam_prep1/"
                    ),
                    @Server(
                            description = "Server API",
                            url = "https://aieou.dk/exam_prep1/"
                    )
                          
            }
    )

@Path("hobby")
public class HobbyResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/exam_prep1",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final HobbyFacade FACADE = HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

         @Operation(summary = "Shows Hello World",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The Requested Path"),                       
                    @ApiResponse(responseCode = "400", description = "Path not found")})
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
     @Operation(summary = "Get Hobby by ID",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The Requested Hobby"),                       
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HobbyDTO getHobbyByID(@PathParam("id") int id) {
        HobbyDTO hobby = FACADE.getHobby(id);
        return hobby;
    }
    
         @Operation(summary = "Get All Hobbies",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbiesDTO.class))),
                    @ApiResponse(responseCode = "200", description = "A list of all Hobbies"),                       
                    @ApiResponse(responseCode = "400", description = "????")})
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<HobbyDTO> getAllHobbies() {
        return FACADE.getAllHobbies().getAll(); 
    }
    
         @Operation(summary = "Add a Hobby",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The added Hobby"),                       
                    @ApiResponse(responseCode = "400", description = "Invalid input")})
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    public HobbyDTO addHobby(String hobby) {
        Hobby h = GSON.fromJson(hobby, Hobby.class);
        HobbyDTO hobbyDTO = new HobbyDTO(h);
        FACADE.addHubby(hobbyDTO);
        return hobbyDTO;
    }
    
         @Operation(summary = "Edit a Hobby",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The edited Hobby"),                       
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    @PUT
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public HobbyDTO editHobby(String hobby) {
        Hobby h = GSON.fromJson(hobby, Hobby.class);
        HobbyDTO hobbyDTO = new HobbyDTO(h);
        FACADE.editHobby(hobbyDTO);
        return hobbyDTO;
    }
    
         @Operation(summary = "Remove/Delete a Hobby",
            tags = {"Hobby"},
            responses = {
                     @ApiResponse(
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The Removed Hobby"),                       
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public HobbyDTO removeHobby(@PathParam("id") int id) {
        return FACADE.removeHobby(id);
    }
}
