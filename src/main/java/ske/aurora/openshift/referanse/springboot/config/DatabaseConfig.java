package ske.aurora.openshift.referanse.springboot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configure database if the aurora.db property is set.
 * This should be moved to a repo of its own.
 */
@Configuration
@EnableConfigurationProperties(DatabaseConfig.AuroraProperties.class)
@ConditionalOnProperty(prefix = "aurora", value = "db")
public class DatabaseConfig {

    @Autowired
    private AuroraProperties auroraProperties;

    @Bean
    @Profile("openshift")
    public DataSource dataSource() throws IOException {

        String envName = auroraProperties.db + "_DB_PROPERTIES";
        String databasePath = System.getenv(envName.toUpperCase());

        if (databasePath == null) {
            return null;
        }

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(databasePath)) {
            props.load(input);
        }

        return DataSourceBuilder.create()
            .url(props.getProperty("jdbc.url"))
            .username(props.getProperty("jdbc.username"))
            .password(props.getProperty("jdbc.password"))
            .build();
    }

    @ConfigurationProperties("aurora")
    public static class AuroraProperties {
        private String db;

        public String getDb() {
            return db;
        }

        public void setDb(String db) {
            this.db = db;
        }
    }

}