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
            throw new RuntimeException(e);
        }
    }

    private void execute(String query) {
        try {
            Statement statement = getStatement();
            statement.execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Renter> getAllRenters() {
        return getList(Renter.class, "SELECT * FROM renters");
    }

    public Renter getRenterById(String renterId) {
        return getList(Renter.class, String.format("SELECT * FROM renters WHERE id_renter = %s", renterId)).get(0);
    }

    public void addRenter(Renter renter) {
        addObject(renter);
    }

    public Renter getRenterByBox(String boxId) {
        return getList(Renter.class, String.format("SELECT renters.* FROM " +
                "renters JOIN cars ON renters.id_renter = cars.id_renter " +
                "JOIN boxes ON cars.box_number = boxes.box_number WHERE boxes.box_number = %s", boxId)).get(0);
    }

    public List<Renter> getRentersByModel(String modelId) {
        return getList(Renter.class, String.format("SELECT renters.* FROM " +
                "renters JOIN cars ON renters.id_renter = cars.id_renter " +
                "JOIN boxes ON cars.box_number = boxes.box_number " +
                "JOIN models ON boxes.id_model = models.id_model WHERE boxes.id_model = %s", modelId));
    }

    public List<Model> getAllModels() {
        return getList(Model.class, "SELECT * FROM models");
    }

    public Model getModelById(String modelId) {
        return getList(Model.class, String.format("SELECT * FROM models WHERE id_model = %s", modelId)).get(0);
    }

    public void addModel(Model model) {
        addObject(model);
    }

    public Model getModelByBox(String boxId) {
        return getList(Model.class, String.format("SELECT models.* FROM " +
                "models JOIN boxes ON boxes.id_model = models.id_model " +
                "WHERE box_number = %s", boxId)).get(0);
    }

    public List<Box> getAllBoxes() {
        return getList(Box.class, "SELECT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model");
    }

    public List<Box> getFreeBoxes() {
        return getList(Box.class, "SELECT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model " +
                "WHERE box_number NOT IN (SELECT box_number FROM cars)");
    }

    public Box getBoxById(String boxId) {
        return getList(Box.class, String.format("SELECT boxes.*, models.model_name " +
                "FROM boxes join models on boxes.id_model = models.id_model " +
                "WHERE box_number = %s", boxId)).get(0);
    }

    public void addBox(Box box) {
        if(!getAllBoxes().isEmpty())
            throw new MyException("Бокс уже существует");
        addObject(box);
    }

    public void deleteBoxById(String boxId) {
        execute(String.format("DELETE from boxes WHERE box_number = %s", boxId));
    }

    public void changeCost(CostUp costUp) {
        execute(String.format("UPDATE boxes SET daily_cost = ROUND(daily_cost * %s)", costUp.getCoef()));
    }

    public void addCar(Car car) {
        addObject(car);
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
            throw new RuntimeException(e);
        }

    }

}
