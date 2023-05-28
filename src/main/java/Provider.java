import DB.*;

import java.util.List;

public interface Provider {
    List<Renter> getAllRenters();

    Renter getRenterById(String renterId);

    Renter addRenter(Renter renter);
    Renter updateRenter(Renter renter);

    Renter getRenterByBox(String boxId);

    List<Renter> getRentersByModel(String modelId);

    List<Model> getAllModels();

    Model getModelById(String modelId);

    Model addModel(Model model);
    Model updateModel(Model model);

    Model getModelByBox(String boxId);

    List<Box> getAllBoxes();

    List<Box> getFreeBoxes();
    List<Box> getFreeBoxesWithModel(String modelId);

    Box getBoxById(String boxId);

    Box addBox(Box box);
    Box updateBox(Box box);

    void deleteBoxById(String boxId);

    void changeCost(CostUp costUp);

    Car addCar(Car car);
    Car updateCar(Car car);

    List<Car> getAllCars();
    Car getCarById(String carId);
    Car getCarByBox(String boxNumber);
    void deleteCarById(String carId);
}
