package org.lebedeva.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {
    protected Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GenericDao(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);

        DatabaseMetaData metaData = connection.getMetaData();
        if (metaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }
    }

    protected void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    protected void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    protected void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public abstract List<T> getAll() throws SQLException;

    public abstract void save(T data) throws SQLException;

    public abstract void update(long id, String... params) throws SQLException;

    public abstract void delete(long id) throws SQLException;

}
