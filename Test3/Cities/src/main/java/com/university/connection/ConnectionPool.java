package com.university.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private final static String url = "jdbc:postgresql://localhost:5432/Cities";
    private final static String user = "postgres";
    private final static String pass = "658679";
    private static ConnectionPool instance = null;
    private static Properties props = null;

    private ConnectionPool() {
        props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, props);
            logger.info("Connection has been created successfully");
        } catch (SQLException e) {
            logger.warn("Connection could not be created: {}", e.getMessage());
        }
        return connection;
    }
}
