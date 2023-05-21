package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Box extends DBObject {
    Integer box_number;
    Integer id_model;
    Integer daily_cost;
    String model_name;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public Integer getBox_number() {
        return box_number;
    }

    public void setBox_number(Integer box_number) {
        this.box_number = box_number;
    }

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public Integer getDaily_cost() {
        return daily_cost;
    }

    public void setDaily_cost(Integer daily_cost) {
        this.daily_cost = daily_cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box box)) return false;

        if (!getBox_number().equals(box.getBox_number())) return false;
        if (!getId_model().equals(box.getId_model())) return false;
        return getDaily_cost().equals(box.getDaily_cost());
    }

    @Override
    public int hashCode() {
        int result = getBox_number().hashCode();
        result = 31 * result + getId_model().hashCode();
        result = 31 * result + getDaily_cost().hashCode();
        return result;
    }
    public static String getCreateString() {
        return "CREATE TABLE if not exists boxes (" +
                "box_number INT NOT NULL, " +
                "id_model INT NOT NULL, " +
                "daily_cost INT NOT NULL, " +
                "PRIMARY KEY(box_number), " +
                "CONSTRAINT fk_model " +
                "FOREIGN KEY(id_model) " +
                "REFERENCES models(id_model)" +
                ")";
    }

    public String insertString() {
        return String.format("INSERT INTO boxes (box_number, id_model, daily_cost) " +
                        "VALUES (%d, %d, %d)",
                getBox_number(), getId_model(), getDaily_cost());
    }

    public void fillFromDB(ResultSet resultSet) {
        try {
            this.box_number = resultSet.getInt("box_number");
            this.id_model = resultSet.getInt("id_model");
            this.daily_cost = resultSet.getInt("daily_cost");
            this.model_name = resultSet.getString("model_name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
