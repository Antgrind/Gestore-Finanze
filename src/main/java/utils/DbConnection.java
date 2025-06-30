
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://dpg-d1gi0dngi27c73bpc7kg-a.frankfurt-postgres.render.com:5432/mafia_finanze";
    private static final String USER = "mafia_user";
    private static final String PASSWORD = "hMHAao8xeGuFxAN1HhGVEdWLCFeggyRT";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
