package server;

import java.sql.*;
import java.util.ArrayList;

public class FromDB {
    private static final String URL = "jdbc:mysql://localhost:3306/recipesdb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1002199Timka";
    public String name;

    private static final String SELECT = "select recipeName from recipelist where recipe_ID IN (select recipe_ID from ingred group by recipe_ID having count(1) = sum(ingredient in(?,?,?,?,?,?,?)))";

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
        String[] ingredients = ingredient.split(";");
        try {
            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, ingredients[0]);
            preparedStatement.setString(2, ingredients[1]);
            preparedStatement.setString(3, ingredients[2]);
            preparedStatement.setString(4, ingredients[3]);
            preparedStatement.setString(5, ingredients[4]);
            preparedStatement.setString(6, ingredients[5]);
            //preparedStatement.setString(7, ingredients[6]);
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

