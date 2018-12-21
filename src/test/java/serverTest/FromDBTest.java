package serverTest;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class FromDBTest {

    @Test
    public void getFromDB_Count1() {
        FromDB db = new FromDB();
        String ingredients = "Молоко;Яйцо куриное;";
        int actual = db.GetFromDB(ingredients).size();
        int expected = 1;
        assertEquals(expected,actual);
    }

    @Test
    public void getFromDB_Count2() {
        FromDB db = new FromDB();
        String ingredients = "Молоко;Яйцо куриное;Пшеничная мука;";
        int actual = db.GetFromDB(ingredients).size();
        int expected = 17;
        assertEquals(expected,actual);
    }

    @Test
    public void getFromDB_Count3() {
        FromDB db = new FromDB();
        String ingredients = ";Молоко;Яйцо куриное;Пшеничная мука;Сливочное масло;";
        int actual = db.GetFromDB(ingredients).size();
        int expected = 28;
        assertEquals(expected,actual);
    }

    @Test
    public void getFromDB_String() {
        FromDB db = new FromDB();
        String ingredients = ";Молоко;Яйцо куриное;";
        String actual = db.GetFromDB(ingredients).toString();
        String expected = "[;Омлет из натуральных продуктов;1. Отбитые в миску или в кастрюлю яйца посолить, влить немного молока и взбить ложкой или вилкой. 2. Полученную массу вылить на горячую сковороду с маслом и жарить на сильном огне, слегка встряхивая сковороду, чтобы яичная масса прогревалась равномерно. Как только яичница начнет густеть, гибким ножом завернуть ее края с двух сторон к середине, придавая таким образом яичнице форму продолговатого пирожка; откинуть его швом вниз на тарелку или овальное блюдо, смазать кусочком сливочного масла и тотчас же подать на стол.;Яйцо куриное 3 штуки Молоко 1 столовая ложка Растительное масло 1 столовая ложка ;]";
        assertEquals(expected,actual);
    }

    @Test
    public void closeConnection() {
        FromDB db = new FromDB();
        Connection connection = db.getConnection();
        db.CloseConnection();
        boolean isClose = false;
        try {
            isClose = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean expected = true;

        assertEquals(isClose, expected);
    }

    @Test
    public void openConnection() {
        FromDB db = new FromDB();
        Connection connection = db.getConnection();

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