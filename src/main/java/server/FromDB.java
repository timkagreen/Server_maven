package server;

import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

public class FromDB {
    private static final String URL = "jdbc:mysql://localhost:3306/recipesdb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1002199Timka";
    public String name;

    private static final String INSERT_NEW = "SELECT * FROM recipelist WHERE recipeIngredients LIKE ?";

    Connection connection = null;

    public FromDB() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection.isClosed()) {
                System.out.println("Соединение с БД не установлено");
            } else
                System.out.println("Соединение с БД установлено!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> GetFromDB(String ingredient) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, "%" + ingredient + "%");
            ResultSet recipe = preparedStatement.executeQuery();
            ArrayList<String> names = new ArrayList<String>();

            while (recipe.next()) {
                name = recipe.getString("recipeName");
                names.add(name);
            }
            return names;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void CloseConnection() {
        try {
            connection.close();
            if (connection.isClosed()) {
                System.out.println("Соединение с БД закрыто");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

