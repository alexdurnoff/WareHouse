package ru.durnov.warehouse.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {
    private SqliteDaoConnector connector;

    @Before
    public void setup() throws SQLException {
        this.connector = new SqliteDaoConnector();
    }

    @Test
    public void testConnection() throws SQLException {
        SqliteDaoConnector connector = new SqliteDaoConnector();
        Assert.assertNotNull(connector.getConnection());
        System.out.println(connector.getConnection().toString());
    }

    @Test
    public void deleteDevicesTable(){
        //Один раз тест был пройден. Больше не повторяем, так как таблицы devices уже нет)))
        //Еще один вывод - пароль, похоже, не нужен
        String request = "DROP TABLE IF EXISTS DEVICES_DEVICES";
            connector.executeUpdateRequest(request);
    }

    @Test
    public void testFirstRequestT0SimpleTables() throws SQLException {
        ResultSet resultSet = connector.getResultSet("select * from producttable");
        while (resultSet.next()){
            for (int i = 1; i < 3; i++){
                System.out.print(resultSet.getString(i) + "::");

            }
            System.out.println();
        }
    }
}
