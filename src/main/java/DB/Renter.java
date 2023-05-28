package DB;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Renter extends DBObject {
    private Integer id_renter;
    private String full_name;
    private String phone;
    private String address;
    public Integer getId_renter() {
        return id_renter;
    }

    public void setId_renter(Integer id_renter) {
        this.id_renter = id_renter;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Renter renter)) return false;

        if (!getId_renter().equals(renter.getId_renter())) return false;
        if (!getFull_name().equals(renter.getFull_name())) return false;
        if (getPhone() != null ? !getPhone().equals(renter.getPhone()) : renter.getPhone() != null) return false;
        return getAddress() != null ? getAddress().equals(renter.getAddress()) : renter.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getId_renter().hashCode();
        result = 31 * result + getFull_name().hashCode();
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }

    public static String getCreateString() {
        return "CREATE TABLE if not exists renters (" +
                "id_renter INT NOT NULL, " +
                "full_name TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "address TEXT NOT NULL, " +
                "PRIMARY KEY(id_renter)" +
                ")";
    }
    public String insertString() {
        return String.format("INSERT INTO renters (id_renter, full_name, phone, address) " +
                "VALUES (%d, '%s', '%s', '%s')",
                getId_renter(), getFull_name(), getPhone(), getAddress());
    }

    public String updateString() {
        return String.format("UPDATE renters " +
                        "SET full_name = '%s', phone = '%s', address = '%s' " +
                        "WHERE id_renter = %d",
                getFull_name(), getPhone(), getAddress(), getId_renter());
    }
    public void fillFromDB(ResultSet resultSet) {
        try {
            this.id_renter = resultSet.getInt("id_renter");
            this.full_name = resultSet.getString("full_name");
            this.phone = resultSet.getString("phone");
            this.address = resultSet.getString("address");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
