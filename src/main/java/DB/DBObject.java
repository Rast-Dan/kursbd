package DB;

import java.sql.ResultSet;

public abstract class DBObject {
    abstract public void fillFromDB(ResultSet resultSet);
    abstract public String insertString();
}
