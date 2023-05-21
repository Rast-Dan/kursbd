import DB.*;
import exceptions.MyException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("")
public class Api {
    private Provider provider = new DBProviderImpl();

    @GET
    @Path("/renters/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getAllRenters() {
        return provider.getAllRenters();
    }

    @GET
    @Path("/renters/{renterId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterById(@PathParam("renterId") String renterId) {
        return provider.getRenterById(renterId);
    }

    @POST
    @Path("/renters/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addRenter(Renter renter) {
        provider.addRenter(renter);
    }

    @GET
    @Path("/renterInBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterByBox(@PathParam("boxId") String boxId) {
        return provider.getRenterByBox(boxId);
    }

    @GET
    @Path("/renterByModel/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getRentersByModel(@PathParam("modelId") String modelId) {
        return provider.getRentersByModel(modelId);
    }

    @GET
    @Path("/models/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Model> getAllModels() {
        return provider.getAllModels();
    }

    @GET
    @Path("/models/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelById(@PathParam("modelId") String modelId) {
        return provider.getModelById(modelId);
    }

    @POST
    @Path("/models/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addModel(Model model) {
        provider.addModel(model);
    }

    @GET
    @Path("/modelForBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelByBox(@PathParam("boxId") String boxId) {
        return provider.getModelByBox(boxId);
    }

    @GET
    @Path("/boxes/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getAllBoxes() {
        return provider.getAllBoxes();
    }

    @GET
    @Path("/boxes/free")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getFreeBoxes() {
        return provider.getFreeBoxes();
    }

    @GET
    @Path("/boxes/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Box getBoxById(@PathParam("boxId") String boxId) {
        return provider.getBoxById(boxId);
    }

    @POST
    @Path("/boxes/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addBox(Box box) {
        provider.addBox(box);
    }

    @DELETE
    @Path("/boxes/delete/{boxId}")
    public void deleteBoxById(@PathParam("boxId") String boxId) {
        provider.deleteBoxById(boxId);
    }

    @POST
    @Path("/boxes/costUp")
    @Consumes({MediaType.APPLICATION_JSON})
    public void changeCost(CostUp costUp) {
        provider.changeCost(costUp);
    }

    @POST
    @Path("/cars/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addCar(Car car) {
        provider.addCar(car);
    }

    @DELETE
    @Path("/cars/delete/{carId}")
    public void deleteCarById(@PathParam("carId") String carId) {
        provider.deleteCarById(carId);
    }

}
