import DB.*;
import exceptions.MyException;
import org.jboss.resteasy.spi.BadRequestException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBProviderImpl implements Provider {

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        return connection.createStatement();
    }

    private <Type extends DBObject> List<Type> getList(Class<Type> type, String request) {
        try {
            List<Type> result = new ArrayList<>();
            Statement statement = getStatement();
            ResultSet rs = statement.executeQuery(request);
            while(rs.next()) {
                Type obj = type.getDeclaredConstructor().newInstance();
                obj.fillFromDB(rs);
                result.add(obj);
            }
            return result;
        } catch (Exception e) {
            throw new MyException("Непредвиденная ошибка:\n" + e);
        }
    }

    private <Type extends DBObject> void addObject(Type value) {
        try {
            execute(value.insertString());
        } catch (Exception e) {
            throw new MyException("Непредвиденная ошибка:\n" + e);
        }
    }

    private <Type extends DBObject> void updateObject(Type value) {
        try {
            execute(value.updateString());
        } catch (Exception e) {
            throw new MyException("Непредвиденная ошибка:\n" + e);
        }
    }


    private void execute(String query) {
        try {
            Statement statement = getStatement();
            statement.execute(query);
        } catch (Exception e) {
            throw new MyException("Непредвиденная ошибка:\n" + e);
        }
    }

    public List<Renter> getAllRenters() {
        return getList(Renter.class, "SELECT * FROM renters");
    }

    public Renter getRenterById(String renterId) {
        List<Renter> renters = getList(Renter.class, String.format("SELECT * FROM renters WHERE id_renter = %s", renterId));
        if(renters.isEmpty())
            throw new MyException("Арендатора с таким идентификатором не существует");
        return renters.get(0);
    }
    String selectRentersQuery = "SELECT * FROM renters WHERE id_renter = %s";
    public Renter addRenter(Renter renter) {
        if(renter.getId_renter() == null) {
            Integer ident = 0;
            while(!getList(Renter.class, String.format(selectRentersQuery, ident)).isEmpty())
                ident = (int)(Math.random() * Integer.MAX_VALUE);
            renter.setId_renter(ident);
        }
        if(!getList(Renter.class, String.format(selectRentersQuery, renter.getId_renter())).isEmpty())
            throw new MyException("Арендатор с таким идентификатором уже существует");
        addObject(renter);
        return renter;
    }

    public Renter updateRenter(Renter renter) {
        if(getList(Renter.class, String.format(selectRentersQuery, renter.getId_renter())).isEmpty())
            throw new MyException("Арендатор с таким идентификатором отсутствует");
        updateObject(renter);
        return renter;
    }

    public Renter getRenterByBox(String boxId) {
        List<Renter> renters = getList(Renter.class, String.format("SELECT renters.* FROM " +
                "renters JOIN cars ON renters.id_renter = cars.id_renter " +
                "JOIN boxes ON cars.box_number = boxes.box_number WHERE boxes.box_number = %s", boxId));
        if(renters.isEmpty())
            throw new MyException("Арендатора с таким боксом не существует");
        return renters.get(0);
    }

    public List<Renter> getRentersByModel(String modelId) {
        return getList(Renter.class, String.format("SELECT DISTINCT renters.* FROM " +
                "renters JOIN cars ON renters.id_renter = cars.id_renter " +
                "JOIN boxes ON cars.box_number = boxes.box_number " +
                "JOIN models ON boxes.id_model = models.id_model WHERE boxes.id_model = %s", modelId));
    }

    public List<Model> getAllModels() {
        return getList(Model.class, "SELECT * FROM models");
    }

    public Model getModelById(String modelId) {
        List<Model> models = getList(Model.class, String.format("SELECT * FROM models WHERE id_model = %s", modelId));
        if(models.isEmpty())
            throw new MyException("Модели с таким идентификатором нет");
        return models.get(0);
    }
    String selectModelsQuery = "SELECT * FROM models WHERE id_model = %s";
    public Model addModel(Model model) {
        if(model.getId_model() == null) {
            Integer ident = 0;
            while(!getList(Model.class, String.format(selectModelsQuery, ident)).isEmpty())
                ident = (int)(Math.random() * Integer.MAX_VALUE);
            model.setId_model(ident);
        }
        if(!getList(Model.class, String.format(selectModelsQuery, model.getId_model())).isEmpty())
            throw new MyException("Модель с таким идентификатором уже существует");
        addObject(model);
        return model;
    }
    public Model updateModel(Model model) {
        if(getList(Model.class, String.format(selectModelsQuery, model.getId_model())).isEmpty())
            throw new MyException("Модель с таким идентификатором отсутствует");
        updateObject(model);
        return model;
    }

    public Model getModelByBox(String boxId) {
        List<Model> models = getList(Model.class, String.format("SELECT DISTINCT models.* FROM " +
                "models JOIN boxes ON boxes.id_model = models.id_model " +
                "WHERE box_number = %s", boxId));
        if(models.isEmpty())
            throw new MyException("Модель для данного бокса отсутствует");
        return models.get(0);
    }

    public List<Box> getAllBoxes() {
        return getList(Box.class, "SELECT DISTINCT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model");
    }

    public List<Box> getFreeBoxes() {
        return getList(Box.class, "SELECT DISTINCT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model " +
                "WHERE box_number NOT IN (SELECT box_number FROM cars)");
    }

    public List<Box> getFreeBoxesWithModel(String modelId) {
        return getList(Box.class, String.format("SELECT DISTINCT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model " +
                "WHERE boxes.id_model = %s AND box_number NOT IN (SELECT box_number FROM cars)", modelId));
    }


    public Box getBoxById(String boxId) {
        List<Box> boxes = getList(Box.class, String.format("SELECT DISTINCT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model " +
                "WHERE box_number = %s", boxId));
        if(boxes.isEmpty())
            throw new MyException("Бокса с таким идентификатором не существует");
        return boxes.get(0);
    }

    private static String selectBoxesQuery = "SELECT * FROM boxes WHERE box_number = %s";
    public Box addBox(Box box) {
        if(box.getBox_number() == null) {
            Integer ident = 0;
            while(!getList(Box.class, String.format(selectBoxesQuery, ident)).isEmpty())
                ident = (int)(Math.random() * Integer.MAX_VALUE);
            box.setBox_number(ident);
        }
        if(!getList(Box.class, String.format(selectBoxesQuery, box.getBox_number())).isEmpty())
            throw new MyException("Бокс с таким идентификатором уже существует");
        addObject(box);
        return box;
    }

    public Box updateBox(Box box) {
        if(getList(Box.class, String.format(selectBoxesQuery, box.getBox_number())).isEmpty())
            throw new MyException("Бокс с таким идентификатором отсутствует");
        updateObject(box);
        return box;
    }

    public void deleteBoxById(String boxId) {
        execute(String.format("DELETE from boxes WHERE box_number = %s", boxId));
    }

    public void changeCost(CostUp costUp) {
            execute(String.format("UPDATE boxes SET daily_cost = ROUND(daily_cost * %s)", costUp.getCoef()));
    }

    String selectCarsQuery = "SELECT * FROM cars WHERE car_number = %s";
    public  Car addCar(Car car) {
        if(car.getCar_number() == null) {
            Integer ident = 0;
            while(!getList(Car.class, String.format(selectCarsQuery, ident)).isEmpty())
                ident = (int)(Math.random() * Integer.MAX_VALUE);
            car.setCar_number(ident);
        }
        if(!getList(Car.class, String.format(selectCarsQuery, car.getCar_number())).isEmpty())
            throw new MyException("Машина с таким идентификатором уже существует");
        addObject(car);
        return car;
    }

    public Car updateCar(Car car) {
        if(getList(Car.class, String.format(selectCarsQuery, car.getCar_number())).isEmpty())
            throw new MyException("Машина с таким идентификатором отсутствует");
        updateObject(car);
        return car;
    }

    public List<Car> getAllCars() {
        return getList(Car.class, "SELECT DISTINCT cars.*, models.model_name, renters.full_name, renters.phone " +
                "FROM cars join models on cars.id_model = models.id_model join renters on cars.id_renter = renters.id_renter");
    }


    public Car getCarById(String carId) {
        List<Car> cars = getList(Car.class, String.format("SELECT DISTINCT cars.*, models.model_name, renters.full_name, renters.phone " +
                "FROM cars join models on cars.id_model = models.id_model join renters on cars.id_renter = renters.id_renter " +
                "WHERE car_number = %s", carId));
        if(cars.isEmpty())
            throw new MyException("Машины с таким идентификатором не существует");
        return cars.get(0);
    }

    public Car getCarByBox(String boxNumber) {
        List<Car> cars = getList(Car.class, String.format("SELECT DISTINCT cars.*, models.model_name, renters.full_name, renters.phone " +
                "FROM cars join models on cars.id_model = models.id_model join renters on cars.id_renter = renters.id_renter " +
                "WHERE box_number = %s", boxNumber));
        if(cars.isEmpty())
            throw new MyException("Бокс пуст");
        return cars.get(0);
    }

    public void deleteCarById(String carId) {
        execute(String.format("DELETE from cars WHERE car_number = %s", carId));
    }

    public DBProviderImpl() {
        try {
            Statement statement = getStatement();
            statement.execute(Model.getCreateString());
            statement.execute(Renter.getCreateString());
            statement.execute(Box.getCreateString());
            statement.execute(Car.getCreateString());
        } catch (SQLException e) {
            throw new MyException(e.getMessage());
        }

    }

}
