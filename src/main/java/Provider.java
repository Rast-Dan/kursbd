import DB.*;

import java.util.List;

public interface Provider {
    List<Renter> getAllRenters();

    Renter getRenterById(String renterId);

    Renter addRenter(Renter renter);

    Renter getRenterByBox(String boxId);

    List<Renter> getRentersByModel(String modelId);

    List<Model> getAllModels();

    Model getModelById(String modelId);

    Model addModel(Model model);

    Model getModelByBox(String boxId);

    List<Box> getAllBoxes();

    List<Box> getFreeBoxes();

    Box getBoxById(String boxId);

    Box addBox(Box box);

    void deleteBoxById(String boxId);

    void changeCost(CostUp costUp);

    Car addCar(Car car);

    void deleteCarById(String carId);
}
