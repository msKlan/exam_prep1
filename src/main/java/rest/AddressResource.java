/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AddressDTO;
import dto.AddressesDTO;
import entities.Address;
import facades.AddressFacade;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author Renz
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Simple address API",
                version = "0.4",
                description = "Simple API to get info about Addresses.",
                contact = @Contact(name = "Renz Oliver de Chavez", email = "cph@cphbusiness.dk")
        ),
        tags = {
            @Tag(name = "address", description = "API related to addresses")

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

@Path("address")
public class AddressResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA2",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final AddressFacade FACADE = AddressFacade.getAddressFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Operation(summary = "Get address based on ID",
            tags = {"address"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Requested addressByID"),
                @ApiResponse(responseCode = "400", description = "Address by ID not found")}
    )
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressByID(@PathParam("id") int id) {
        AddressDTO address = FACADE.getAddress(id);
        return Response.ok().entity(GSON.toJson(address)).build();
    }

    @Operation(summary = "Get all addresses info",
            tags = {"address"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Requested address"),
                @ApiResponse(responseCode = "400", description = "Address not found")}
    )
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<AddressDTO> getAllAddress() {
        return FACADE.getAllAddress().getAll();
    }

    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "add Address", tags = {"address"},
            responses = {
                @ApiResponse(responseCode = "200", description = "The Newly created address"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body")
            })
    public AddressDTO addAddress(String address) {
        Address ad = GSON.fromJson(address, Address.class);
        AddressDTO addressDTO = new AddressDTO(ad);
        if (addressDTO.getStreet() == null || addressDTO.getAdditionalInfo() == null) {
            throw new WebApplicationException("Not all required arguments indecluded", 400);
        }
        FACADE.addAddress(addressDTO);
        return addressDTO;
    }
@Operation(summary = "Edit address",
            tags = {"address"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Requested address"),
                @ApiResponse(responseCode = "400", description = "Address not found")}
    )
    @PUT
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public AddressDTO editAddress(String address) {
        Address ad = GSON.fromJson(address, Address.class);
        AddressDTO addressDTO = new AddressDTO(ad);
        FACADE.editAddress(addressDTO);
        return addressDTO;
    }

    @Operation(summary = "Delete Address by ID",
            tags = {"address"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Resquested address"),
                @ApiResponse(responseCode = "400", description = "id not found")}
    )
    @DELETE
    @Path("delete")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public AddressDTO removeAddress(int id) {
        AddressDTO ad = FACADE.getAddress(id);
        FACADE.removeAddress(id);
        return ad;
    }
}
