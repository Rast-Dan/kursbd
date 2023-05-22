package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model extends DBObject {
    Integer id_model;
    String name;

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model model)) return false;

        if (!getId_model().equals(model.getId_model())) return false;
        return getName().equals(model.getName());
    }

    @Override
    public int hashCode() {
        int result = getId_model().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    public static String getCreateString() {
        return "CREATE TABLE if not exists models (" +
                "id_model INT NOT NULL, " +
                "model_name TEXT NOT NULL, " +
                "PRIMARY KEY(id_model)" +
                ")";
    }

    public String insertString() {
        return String.format("INSERT INTO models (id_model, model_name) VALUES (%d, '%s')", getId_model(), getName());
    }

    public String updateString() {
        return String.format("UPDATE models " +
                        "SET model_name = '%s' " +
                        "WHERE id_model = %d",
                getName(), getId_model());
    }

    public void fillFromDB(ResultSet resultSet) {
        try {
            this.id_model = resultSet.getInt("id_model");
            this.name = resultSet.getString("model_name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
