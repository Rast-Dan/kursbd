package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car extends DBObject {
    Integer car_number;
    Integer box_number;
    Integer id_renter;
    Integer id_model;
    String rental_start_date;
    String automobile_number;
    String model_name;
    String renter_full_name;
    String renter_phone;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getRenter_full_name() {
        return renter_full_name;
    }

    public void setRenter_full_name(String renter_full_name) {
        this.renter_full_name = renter_full_name;
    }

    public String getRenter_phone() {
        return renter_phone;
    }

    public void setRenter_phone(String renter_phone) {
        this.renter_phone = renter_phone;
    }

    public String getAutomobile_number() {
        return automobile_number;
    }

    public void setAutomobile_number(String automobile_number) {
        this.automobile_number = automobile_number;
    }

    public Integer getCar_number() {
        return car_number;
    }

    public void setCar_number(Integer car_number) {
        this.car_number = car_number;
    }

    public Integer getBox_number() {
        return box_number;
    }

    public void setBox_number(Integer box_number) {
        this.box_number = box_number;
    }

    public Integer getId_renter() {
        return id_renter;
    }

    public void setId_renter(Integer id_renter) {
        this.id_renter = id_renter;
    }

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public String getRental_start_date() {
        return rental_start_date;
    }

    public void setRental_start_date(String rental_start_date) {
        this.rental_start_date = rental_start_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;

        if (!getCar_number().equals(car.getCar_number())) return false;
        if (!getBox_number().equals(car.getBox_number())) return false;
        if (!getId_renter().equals(car.getId_renter())) return false;
        if (!getId_model().equals(car.getId_model())) return false;
        return getRental_start_date().equals(car.getRental_start_date());
    }

    @Override
    public int hashCode() {
        int result = getCar_number().hashCode();
        result = 31 * result + getBox_number().hashCode();
        result = 31 * result + getId_renter().hashCode();
        result = 31 * result + getId_model().hashCode();
        result = 31 * result + getRental_start_date().hashCode();
        return result;
    }

    public static String getCreateString() {
        return "CREATE TABLE if not exists cars (" +
                "car_number INT NOT NULL, " +
                "box_number INT NOT NULL, " +
                "id_renter INT NOT NULL, " +
                "id_model INT NOT NULL, " +
                "rental_start_date TEXT NOT NULL, " +
                "automobile_number TEXT NOT NULL, " +
                "PRIMARY KEY(car_number), " +
                "CONSTRAINT fk_box " +
                "FOREIGN KEY(box_number) " +
                "REFERENCES boxes(box_number)," +
                "CONSTRAINT fk_renter " +
                "FOREIGN KEY(id_renter) " +
                "REFERENCES renters(id_renter)," +
                "CONSTRAINT fk_model " +
                "FOREIGN KEY(id_model) " +
                "REFERENCES models(id_model)" +
                ")";
    }

    public String insertString() {
        return String.format("INSERT INTO cars (car_number, box_number, id_renter, id_model, rental_start_date, automobile_number) " +
                        "VALUES (%d, %d, %d, %d, '%s', '%s')",
                getCar_number(), getBox_number(), getId_renter(), getId_model(), getRental_start_date(), getAutomobile_number());
    }

    public String updateString() {
        return String.format("UPDATE cars " +
                        "SET box_number = %d, id_renter = %d, id_model = %d, rental_start_date = '%s', automobile_number = '%s' " +
                        "WHERE car_number = %d",
                getBox_number(), getId_renter(), getId_model(), getRental_start_date(), getAutomobile_number(), getCar_number());
    }

    public void fillFromDB(ResultSet resultSet) {
        try {
            this.car_number = resultSet.getInt("car_number");
            this.box_number = resultSet.getInt("box_number");
            this.id_renter = resultSet.getInt("id_renter");
            this.id_model = resultSet.getInt("id_model");
            this.rental_start_date = resultSet.getString("rental_start_date");
            this.automobile_number = resultSet.getString("automobile_number");
            this.model_name = hasColumn(resultSet, "model_name") ? resultSet.getString("model_name") : null;
            this.renter_full_name = hasColumn(resultSet, "full_name") ? resultSet.getString("full_name") : null;
            this.renter_phone = hasColumn(resultSet, "phone") ? resultSet.getString("phone") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
