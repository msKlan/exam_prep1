package rest;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfoDTO;
import entities.Address;
import entities.CityInfo;
import facades.CityInfoFacade;
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
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;
 
/**
 *
 * @author Renz
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Simple cityinfo API",
                version = "0.4",
                description = "Simple API to get info about cityinfo.",
                contact = @Contact(name = "Renz Oliver de Chavez", email = "cph@cphbusiness.dk")
        ),
        tags = {
            @Tag(name = "cityinfo", description = "API related to cityinfos")
 
        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/CA-2"
            ),
            @Server(
                    description = "Server API",
                    url = "http://vincentcph.dk/CA-2/"
            )
 
        }
)
@Path("cityinfo")
public class CityInfoResource {
 
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA2",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final CityInfoFacade FACADE = CityInfoFacade.getCityInfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
 
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
 
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CityInfoDTO getCityInfoByID(@PathParam("id") int id) {
        CityInfoDTO cityinfo = FACADE.getCityInfo(id);
        return cityinfo;
    }
 
    @Operation(summary = "Get all cityinfo",
            tags = {"cityinfo"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class))),
                @ApiResponse(responseCode = "200", description = "The Requested cityinfo"),
                @ApiResponse(responseCode = "400", description = "cityinfo not found")}
    )
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CityInfoDTO> getAllCityInfo() {
        return FACADE.getAllCityInfo().getAll();
    }
 
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    public CityInfoDTO addCityInfo(String cityinfo) {
        CityInfo c = GSON.fromJson(cityinfo, CityInfo.class);
        CityInfoDTO cityinfoDTO = new CityInfoDTO(c);
        FACADE.addCityInfo(cityinfoDTO);
        return cityinfoDTO;
    }
 
    @DELETE
    @Path("delete")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public CityInfoDTO deleteCityInfoByID(int id) {
        CityInfoDTO c = FACADE.getCityInfo(id);
        FACADE.removeCityInfo(id);
        return c;
    }
 
}