package server;

import java.sql.*;
import java.util.ArrayList;


public class FromDB {
    private static final String URL = "jdbc:mysql://localhost:3306/recipesdb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1002199Timka";
    public String name;
    public int recipeID;


    private static final String SELECT = "select recipe_ID,recipeName,recipeInstruction from recipelist where recipe_ID IN (select recipe_ID from ingred group by recipe_ID having count(1) = sum(ingredient in(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)))";
    private static final String SELECT_INGREDS = "select * from ingred where recipe_ID IN (?)";


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

        String[] ingreds = ingredient.split(";");
        ArrayList<String> ingredients = new ArrayList<String>();
        int j = ingreds.length;

        for (int i = 0; i < j; i++) {
            ingredients.add(i, ingreds[i]);
        }
        for (int i = j; i < 20; i++) {
            ingredients.add(i, "");
        }


        try {

            preparedStatement = connection.prepareStatement(SELECT);

            for (int i = 0; i < 20; i++) {
                preparedStatement.setString(i + 1, ingredients.get(i));
            }

            ResultSet recipe = preparedStatement.executeQuery();
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> recipeIDS = new ArrayList<String>();


            while (recipe.next()) {
                preparedStatement = connection.prepareStatement(SELECT_INGREDS);
                preparedStatement.setString(1, recipe.getString("recipe_ID"));
                ResultSet ingredsForID = preparedStatement.executeQuery();
                name = ";" + recipe.getString("recipeName") + ";" + recipe.getString("recipeInstruction") + ";";
                while (ingredsForID.next()) {
                    name += ingredsForID.getString("ingredient") + " " + ingredsForID.getString("quantity") + " ";
                }
                name += ";";
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

