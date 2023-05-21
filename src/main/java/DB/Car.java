package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car extends DBObject {
    Integer car_number;
    Integer box_number;
    Integer id_renter;
    Integer id_model;
    String rental_start_date;

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
        return String.format("INSERT INTO cars (car_number, box_number, id_renter, id_model, rental_start_date) " +
                        "VALUES (%d, %d, %d, %d, '%s')",
                getCar_number(), getBox_number(), getId_renter(), getId_model(), getRental_start_date());
    }

    public void fillFromDB(ResultSet resultSet) {
        try {
            this.car_number = resultSet.getInt("car_number");
            this.box_number = resultSet.getInt("box_number");
            this.id_renter = resultSet.getInt("id_renter");
            this.id_model = resultSet.getInt("id_model");
            this.rental_start_date = resultSet.getString("rental_start_date");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
