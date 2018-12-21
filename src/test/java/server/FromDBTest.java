package server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class FromDBTest {

    @Test
    public void getFromDB() {
        FromDB db = new FromDB();
        String ingredients = "Молоко;Яйцо куриное;Мука пшеничная;";
        int actual = db.GetFromDB(ingredients).size();
        int expected = 1;
        assertEquals(expected,actual);
    }

    @Test
    public void closeConnection() {
        FromDB db = new FromDB();
        String URL = "jdbc:mysql://localhost:3306/recipesdb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String USERNAME = "root";
        String PASSWORD = "1002199Timka";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.CloseConnection();
        boolean isClose = false;
        try {
            isClose = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean expected = false;

        assertEquals(isClose, expected);
    }
}