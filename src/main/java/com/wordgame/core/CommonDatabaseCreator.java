package com.wordgame.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vladimir Bratchikov
 */
@Slf4j
public class CommonDatabaseCreator {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public void createDatabase() {
        String databaseUrl = getDatabaseUrl();
        String databaseName = getDatabaseName();
        try(Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            try(Statement statement = connection.createStatement()) {
                try(ResultSet resultSet =
                    statement.executeQuery("SELECT count(*) FROM pg_database "
                                         + "WHERE datistemplate = false AND datname = '" + databaseName + "'")) {
                    if (resultSet.next()) {
                        int result = resultSet.getInt(1);
                        if (result < 1) {
                            log.info("Database \'{}\' does not exist! Creating...", databaseName);
                            statement.executeUpdate("CREATE DATABASE " + databaseName);
                            log.info("Database \'{}\' successfully created", databaseName);
                        }
                        else {
                            log.info("Database \'{}\' already exists", databaseName);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Failed to create database \'{}\': {}", databaseName, e.getMessage());
        }
    }

    private String getDatabaseUrl() {
        return url.substring(0, url.lastIndexOf('/') + 1);
    }

    private String getDatabaseName() {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
