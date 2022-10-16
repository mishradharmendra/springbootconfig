package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ReadDbPropertiesPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "databaseProperties";

    private String[] KEYS = {
            "con.key1",
            "con.key2"
    };

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> propertySource = new HashMap<>();

        try {

            // Build manually datasource to ServiceConfig
            DataSource ds = DataSourceBuilder
                    .create()
                    .username("postgres")
                    .password("admin")
                    .url("jdbc:postgresql://localhost:5432/postgres")
                    .driverClassName("org.postgresql.Driver")
                    .build();

            // Fetch all properties

            Connection connection = ds.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT config_value FROM config_params WHERE config_key = ?");

            for (int i = 0; i < KEYS.length; i++) {

                String key = KEYS[i];

                preparedStatement.setString(1, key);

                ResultSet rs = preparedStatement.executeQuery();

                // Populate all properties into the property source
                while (rs.next()) {
                    propertySource.put(key, rs.getString("config_value"));
                }

                rs.close();
                preparedStatement.clearParameters();

            }

            preparedStatement.close();
            connection.close();

            // Create a custom property source with the highest precedence and add it to Spring Environment
            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
