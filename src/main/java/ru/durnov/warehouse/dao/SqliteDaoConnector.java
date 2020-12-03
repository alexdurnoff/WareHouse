package ru.durnov.warehouse.dao;

import java.sql.*;

public class SqliteDaoConnector {
    private final String driverStr = "driver=com.mysql.cj.jdbc.Driver";
    private Connection connection;
    private final String url = "jdbc:sqlite:db/db.sqlite3";

    public SqliteDaoConnector() throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public void executeUpdateRequest(String sqlRequest) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlRequest);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ResultSet getResultSet(String sqlRequest) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return null;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        return "Connector:" + "\n" + "url=" + this.url;

    }
}
